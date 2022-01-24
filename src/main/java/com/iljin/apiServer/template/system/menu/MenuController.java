package com.iljin.apiServer.template.system.menu;

import com.iljin.apiServer.core.util.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;
    private final UserMenuService userMenuService;

    @ExceptionHandler(MenuException.class)
    public ResponseEntity<Error> MenuNotFound(MenuException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : Menu List
     * @Param String loginId : logged in User employee No.
     * */
    @GetMapping("/list/{loginId}")
    public ResponseEntity<List<MenuDto>> getMenuListByLoginId(@PathVariable String loginId) {
        List<MenuDto> list = menuService.getMenuListByLoginId(loginId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : Menu List
     * @Param String loginId : logged in User employee No.
     * */
    @GetMapping("/quick-menu/{loginId}")
    public ResponseEntity<List<UserMenuDto>> getCustomQuickMenuList(@PathVariable String loginId) {
        List<UserMenuDto> list = userMenuService.getCustomQuickMenuList(loginId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
