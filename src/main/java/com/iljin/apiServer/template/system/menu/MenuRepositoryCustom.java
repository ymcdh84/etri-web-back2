package com.iljin.apiServer.template.system.menu;

import java.util.List;

public interface MenuRepositoryCustom {
    List<MenuDto> getMenuList(MenuDto menuDto);
}
