package com.iljin.apiServer.template.system.menu;

import com.iljin.apiServer.core.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Override
    public List<MenuDto> getMenuListByLoginId(String loginId) {
        List<MenuDto> menuList = new ArrayList<>();
        AtomicReference<String> userRole = new AtomicReference<>("");

        //1. 사용자 권한 확인
        userRepository.findByLoginId(loginId)
                .ifPresent(c -> {
                    userRole.set(c.getRoles().get(0).getRole());
                });

        //2. 권한별 메인 메뉴 리스트 조회 및 set MenuDto
        menuList = menuRepository.getMenuListByAuthority(String.valueOf(userRole))
                .stream()
                .map(s -> new MenuDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                        ,(Integer) s[4]))
                .collect(Collectors.toList());

        return menuList;
    }
}
