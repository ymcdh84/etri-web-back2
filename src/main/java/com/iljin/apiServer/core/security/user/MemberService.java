package com.iljin.apiServer.core.security.user;

import com.iljin.apiServer.core.security.role.UserRole;
import com.iljin.apiServer.core.security.role.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLoginId(username);

        if(!user.isPresent()) {
            throw new UsernameNotFoundException(username + "is not found.");
        }

        Member member = new Member();
        member.setUsername(user.get().getLoginId());
        member.setPassword(user.get().getLoginPw());
        member.setAuthorities(getAuthorities(username));
        member.setEnabled(true);
        member.setAccountNonExpired(true);
        member.setAccountNonLocked(true);
        member.setCredentialsNonExpired(true);

        return member;
    }

    public Collection<GrantedAuthority> getAuthorities(String username) {
        List<UserRole> authList = userRoleRepository.findRolesByUser_LoginId(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole authority : authList) {
            authorities.add(new SimpleGrantedAuthority(authority.getRole()));
        }

        return authorities;
    }

}
