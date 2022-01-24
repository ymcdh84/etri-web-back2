package com.iljin.apiServer.core.util;

import com.iljin.apiServer.core.security.user.Member;
import com.iljin.apiServer.core.security.user.User;
import com.iljin.apiServer.core.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Util {
    final UserRepository userRepository;

    @Autowired
    public Util(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getLoginId() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal();

        String loginId = "";
        if (principal instanceof Member) {
            loginId = ((Member) principal).getUsername();
        }

        return loginId;
    }

    public String getLoginUserId() {
        Optional<User> user = userRepository.findByLoginId(getLoginId());
        return user.map(User::getLoginId).orElse("");
    }

    public String getLoginCompCd() {
        String loginId = Optional.ofNullable(getLoginId()).orElse("");
        Optional<User> user = userRepository.findByLoginId(loginId);
        return user.map(User::getCompCd).orElse("");
    }

    public User getLoginUser() {
        Optional<User> user = userRepository.findByLoginId(getLoginId());

        return user.orElse(null);
    }
}
