package com.iljin.apiServer.template.system.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserMenuServiceImpl implements UserMenuService {
    private final UserMenuRepository userMenuRepository;

    @Override
    public List<UserMenuDto> getCustomQuickMenuList(String loginId) {
        List<UserMenuDto> quickMenus = new ArrayList<>();

        //1. Logged in User's quick menus & set Dto
        quickMenus = userMenuRepository.getCustomQuickMenuList(loginId)
                .stream()
                .map(s -> new UserMenuDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                ))
                .collect(Collectors.toList());

        return quickMenus;
    }
}
