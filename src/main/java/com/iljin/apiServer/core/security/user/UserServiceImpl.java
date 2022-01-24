package com.iljin.apiServer.core.security.user;

import com.iljin.apiServer.core.security.role.Role;
import com.iljin.apiServer.core.security.AuthToken;
import com.iljin.apiServer.core.security.loginHistory.LoginHistory;
import com.iljin.apiServer.core.security.loginHistory.LoginHistoryRepository;
import com.iljin.apiServer.core.security.role.RoleRepository;
import com.iljin.apiServer.core.security.role.UserRole;
import com.iljin.apiServer.core.security.role.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final LoginHistoryRepository loginHistoryRepository;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDto(
                        user.id,
                        user.loginId,
                        "******",
                        user.userName,
                        user.enableFlag,
                        "",
                        user.getRoles().stream().map(
                                roles -> roles.getRole().toString()
                        ).collect(toList())
                )).collect(toList());
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    @Override
    public List<User> getSearchUser(String loginId) {
        return userRepository.findAllByLoginIdContains(loginId);
    }

    @Override
    public ResponseEntity<Object> addUser(UserDto userDto) {
        try {
            userDto.setLoginPw(passwordEncoder.encode(userDto.getLoginPw()));
            User newUser = new User();
            newUser.loginId = userDto.loginId;
            newUser.loginPw = userDto.loginPw;
            newUser.userName = userDto.userName;
            newUser.enableFlag = userDto.enableFlag;
            newUser.compCd = userDto.compCd;
            newUser.deptCd = userDto.deptCd;
            userRepository.save(newUser);

            userRepository.findByLoginId(userDto.loginId).ifPresent(c -> {
                List<UserRole> newRoles = new ArrayList<>();
                UserRole newRole = new UserRole(
                        c.getId(),
                        c.getCompCd(),
                        userDto.role,
                        c);
                userRoleRepository.save(newRole);

                newRoles.add(newRole);
                c.updateUserRoles(newRoles);
                userRepository.save(c);
            });

            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            throw new UserCreateException();
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(String loginId) {
        userRepository.deleteByLoginId(loginId);
        return new ResponseEntity<>("사용자가 삭제되었습니다", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> updateUser(UserDto userDto) {
        String loginId = userDto.getLoginId();
        Optional<User> userData = userRepository.findByLoginId(loginId);

        if (userData.isPresent()) {
            User modifiedUser = userData.get();

            // 비밀번호가 가려진 거 아닌 경우에만 변경.
            if (!userDto.loginPw.equals("******")) {
                modifiedUser.loginPw = passwordEncoder.encode(userDto.getLoginPw());
            }

            modifiedUser.userName = userDto.userName;
            modifiedUser.enableFlag = userDto.enableFlag;

            if(!userDto.getRole().isEmpty()) {
                List<UserRole> modifiedRoles = new ArrayList<>();

                // TODO 복수 권한 부여는 차후 필요시 구현 예정
                Optional<UserRole> modifiedRole = userRoleRepository.findRoleByUser_LoginId(userDto.loginId);
                if (!modifiedRole.isPresent()) {
                    modifiedRole = Optional.of(new UserRole());
                }
                modifiedRole.ifPresent(c -> {
                    c.updateRole(userDto.role);
                    userRoleRepository.save(c);

                    modifiedRoles.add(c);
                    modifiedUser.updateUserRoles(modifiedRoles);
                });
            }

            userRepository.save(modifiedUser);

            return new ResponseEntity<>(userRepository.save(modifiedUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<AuthToken> login(UserDto userDto, HttpSession session, HttpServletRequest request) {
        try {
            String loginId = userDto.loginId;
            String loginPw = userDto.loginPw;
            String loginToken = userDto.token;

            Optional<User> user = userRepository.findByLoginId(loginId);

            Optional<AuthToken> result =
                    user.map(obj -> {
                        // 1. username, password를 조합하여 UsernamePasswordAuthenticationToken 생성
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginId, loginPw);
                        if(loginToken != null) {
                            // Create Granted Authority Rules
                            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                            token = new UsernamePasswordAuthenticationToken(loginId, null, grantedAuthorities);
                        } else {
                            // Form login
                            // 2. Form 로그인 검증을 위해 UsernamePasswordAuthenticationToken 을 authenticationManager 의 인스턴스로 전달
                            authenticationManager.authenticate(token);// 3. 인증에 성공하면 Authentication 인스턴스 리턴
                        }

                        /*
                         * added on 26.08.2019
                         * Login history
                         * */
                        String clientIp = this.getClientIp(request);

                        /* login success */
                        LoginHistory loginHistory = new LoginHistory(
                                loginId,
                                clientIp,
                                userDto.getConnectMthd(),
                                "",
                                request.getRequestURI(),
                                LocalDateTime.now()
                        );
                        loginHistoryRepository.save(loginHistory);

                        if (!StringUtils.isEmpty(userDto.getAttribute1())) {
                            /* from mobile */
                            userRepository.findByLoginId(loginId).ifPresent(c -> {
                                c.updateAttribute1(userDto.getAttribute1());

                                // save Mobile token (String value)
                                userRepository.save(c);
                            });
                        }
                        return getAuthToken(session, loginId, obj, token);
                    });

            return result.map(authToken -> new ResponseEntity<>(authToken, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(new AuthToken(
                            null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null), HttpStatus.UNAUTHORIZED));
        } catch (AuthenticationException e) {
            LoginHistory loginHistory = new LoginHistory(
                    userDto.loginId,
                    this.getClientIp(request),
                    userDto.getConnectMthd(),
                    e.getMessage(),
                    request.getRequestURI(),
                    LocalDateTime.now()
            );
            loginHistoryRepository.save(loginHistory);

            return new ResponseEntity<>(new AuthToken(
                    null
                    , null
                    , null
                    , null
                    , null
                    , null
                    , null
                    , null
                    , null
                    , null
                    , null
                    , null
                    , null), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<AuthToken> ssoLogin(String loginId, HttpSession session) {
        Optional<User> user = userRepository.findByLoginId(loginId);

        List<UserRole> roles = userRoleRepository.findRolesByUser_LoginId(loginId);

//        List<String> r = roles.stream().map(Role::getRole).map(RoleType::toString).collect(Collectors.toList());
        List<String> r = roles.stream().map(x -> x.getRole()).collect(Collectors.toList());
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(r.get(0));

        Optional<AuthToken> result =
                user.map(obj -> {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginId, null, grantedAuthorities);
                    return getAuthToken(session, loginId, obj, token/*, token*/);
                });

        URI uri = null;
        try {
            uri = new URI("http://localhost:8080");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        headers.set("Set-cookie", "sessionid=" + session.getId());

        return result.map(authToken -> new ResponseEntity<>(authToken, headers, HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(new AuthToken(
                        null
                        , null
                        , null
                        , null
                        , null
                        , null
                        , null
                        , null
                        , null
                        , null
                        , null
                        , null
                        ,null), headers, HttpStatus.UNAUTHORIZED));
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @NotNull
    private AuthToken getAuthToken(final HttpSession session, final String loginId, final User obj, final UsernamePasswordAuthenticationToken token/*, final Authentication authentication*/) {
        // 4. Authentication 인스턴스를 SecurityContextHolder의 SecurityContext에 설정
        SecurityContextHolder.getContext().setAuthentication(token);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        Optional<User> user = userRepository.findByLoginId(loginId);

        List<Role> authList = roleRepository.findByRoleCdIn(user.get().roles.stream().map(UserRole::getRole).collect(Collectors.toList()));

        return new AuthToken(obj.userName,
                loginId,
                user.get().getCompCd(),
                user.get().getEmployee().getCompNm(),
                user.get().getEmployee().getDeptCd(),
                user.get().getEmployee().getDeptNm(),
                user.get().getEmployee().getJobDutCd(),
                user.get().getEmployee().getJobDutNm(),
                user.get().getEmployee().getJobGradeCd(),
                user.get().getEmployee().getJobGradeNm(),
                session.getId(),
                user.get().getAttribute2(),
                authList);
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
