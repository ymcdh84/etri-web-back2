package com.iljin.apiServer.template.system.menu;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProgramMngService {
    List<MenuDto> getMenuList(MenuDto menuDto);

    @Modifying
    @Transactional
    ResponseEntity<String> saveMenuList(List<MenuDto> list);

    @Modifying
    @Transactional
    ResponseEntity<String> deleteMenu(String compCd, String menuNo);
}
