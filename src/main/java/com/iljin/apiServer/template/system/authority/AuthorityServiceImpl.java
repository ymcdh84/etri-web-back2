package com.iljin.apiServer.template.system.authority;

import com.iljin.apiServer.core.security.role.*;
import com.iljin.apiServer.core.security.user.User;
import com.iljin.apiServer.core.security.user.UserRepository;
import com.iljin.apiServer.core.util.Util;
import com.iljin.apiServer.template.system.menu.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthorityServiceImpl implements AuthorityService {
    private final Util util;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final MenuAuthRepository menuAuthRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public List<AuthorityDto> getAuthority(String compCd) {
        List<AuthorityDto> list = new ArrayList<>();

        list = authorityRepository.getAuthoritiesByCompCd(compCd)
                .stream()
                .map(s -> new AuthorityDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
                ))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public ResponseEntity<String> saveAuthorities(List<AuthorityDto> authList) {
        String loginCompCd = util.getLoginCompCd();//화면에서 회사를 선택해야 함 - 여러 회사가 존재할 경우

        if(authList.size() > 0) {
            for(AuthorityDto authorityDto : authList) {
                String roleCd = authorityDto.getRoleCd();
                String compCd = authorityDto.getCompCd();
                RoleKey roleKey = new RoleKey(compCd, roleCd);

                Optional<Role> role = roleRepository.findById(roleKey);
                if(role.isPresent()) {
                    //update
                    role.ifPresent(c -> {
                        c.updateRole(authorityDto.getRoleNm(),
                                authorityDto.getRoleSelectCd(),
                                authorityDto.getRoleDc());

                        roleRepository.save(c);
                    });
                } else {
                    //insert
                    Role c = new Role(loginCompCd,
                            authorityDto.getRoleCd(),
                            authorityDto.getRoleNm(),
                            authorityDto.getRoleSelectCd(),
                            authorityDto.getRoleDc());

                    roleRepository.save(c);
                }
            }
        }

        return new ResponseEntity<>("저장 되었습니다.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteAuthority(String roleCd, String compCd) {
        RoleKey roleKey = new RoleKey(compCd, roleCd);

        roleRepository.deleteById(roleKey);
        return new ResponseEntity<>("삭제 되었습니다.", HttpStatus.OK);
    }

    @Override
    public List<MenuAuthDto> getMenuByAuthority(String roleCd, String compCd) {
        List<MenuAuthDto> list = new ArrayList<>();

        list = authorityRepository.getMenuByAuthority(compCd, roleCd)
                .stream()
                .map(s -> new MenuAuthDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,roleCd
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
                        ,compCd
                        ,(Integer) s[6]
                        ,(Integer) s[7]
                        ,String.valueOf(Optional.ofNullable(s[8]).orElse(""))
                ))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public ResponseEntity<String> saveMenuByAuthority(String roleCd, String compCd, List<MenuAuthDto> menuAuthList) {
        /* 권한코드 기준 전체 삭제 */
        menuAuthRepository.deleteByRoleCdAndCompCd(roleCd, compCd);

        for(MenuAuthDto menuAuthDto : menuAuthList) {
            MenuAuth menuAuth = new MenuAuth(menuAuthDto.getRoleCd(),
                    menuAuthDto.getMenuNo(),
                    menuAuthDto.getCompCd());

            menuAuthRepository.save(menuAuth);
        }

        return new ResponseEntity<>("저장되었습니다.", HttpStatus.OK);
    }

    @Override
    public List<AuthorityDto> getUserInfoByAuthority(String roleCd, String compCd) {
        List<AuthorityDto> list = new ArrayList<>();

        list = authorityRepository.getUserInfoByAuthority(compCd, roleCd)
                .stream()
                .map(s -> new AuthorityDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[5]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[6]).orElse(""))
                ))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public ResponseEntity<String> saveUserInfoByAuthority(String roleCd, String compCd, List<AuthorityDto> authList) {
        if(authList.size() > 0) {
            for(AuthorityDto authorityDto : authList) {
                if(authorityDto.getRoleChk().equals("1")) {
                    //권한부여 체크

                    // TODO 복수 권한 부여는 차후 필요시 구현
                    Optional<UserRole> modifiedRole = userRoleRepository.findRoleByUser_LoginId(authorityDto.getEmpNo());
                    if (!modifiedRole.isPresent()) {
                        modifiedRole = Optional.of(new UserRole());
                    }
                    modifiedRole.ifPresent(c -> {
                        c.updateRole(roleCd);
                        userRoleRepository.save(c);
                    });
                } else if(authorityDto.getRoleChk().equals("0")) {
                    //권한부여 체크 해제
                    Optional<UserRole> modifiedRole = userRoleRepository.findRoleByUser_LoginId(authorityDto.getEmpNo());
                    if (!modifiedRole.isPresent()) {
                        modifiedRole = Optional.of(new UserRole());
                    }

                    modifiedRole.ifPresent(c -> {
                        if(c.getRole().equals(roleCd)) {
                            c.updateRole("");
                            userRoleRepository.save(c);
                        }
                    });
                }
            }
        }

        return new ResponseEntity<>("저장되었습니다.", HttpStatus.OK);
    }
}
