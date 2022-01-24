package com.iljin.apiServer.template.system.menu;

import java.util.List;

public interface UserMenuService {
    List<UserMenuDto> getCustomQuickMenuList(String loginId);
}
