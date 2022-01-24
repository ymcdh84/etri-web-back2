package com.iljin.apiServer.template.system.menu;

import java.util.List;

public interface MenuService {
    List<MenuDto> getMenuListByLoginId(String loginId);
}
