package com.iljin.apiServer.template.system.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.iljin.apiServer.core.util.Error;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/program")
public class ProgramMngController {

    private final ProgramMngService programMngService;

    @ExceptionHandler(ProgramMngException.class)
    public ResponseEntity<Error> ProgramMngNotFound(ProgramMngException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 프로그램 관리 목록조회
     * 
     * @param menuDto has menu name value.
     */
    @PostMapping("/list")
    public ResponseEntity<List<MenuDto>> getMenuList(@RequestBody MenuDto menuDto) {
        List<MenuDto> list = programMngService.getMenuList(menuDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 프로그램 관리 저장
     * 
     * @param list is grid Data.
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveMenuList(@RequestBody List<MenuDto> list) {
        return programMngService.saveMenuList(list);
    }

    /**
     * Subject : 프로그램 관리 행삭제
     * 
     * @param
     */
    @DeleteMapping("/")
    public ResponseEntity<String> deleteMenu(@RequestParam String compCd, @RequestParam String menuNo) {
        return programMngService.deleteMenu(compCd, menuNo);
    }
}
