package com.iljin.apiServer.template.system.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iljin.apiServer.core.util.Error;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthorityController {

    private final AuthorityService authorityService;

    @ExceptionHandler(AuthorityException.class)
    public ResponseEntity<Error> RoleManageNotFound(AuthorityException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 권한관리
     * 목록 조회
     * @param compCd is 회사코드.
     * */
    @GetMapping("/")
    public ResponseEntity<List<AuthorityDto>> getAuthorities(@RequestParam String compCd) {
        List<AuthorityDto> list = authorityService.getAuthority(compCd);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /**
     * Subject : 권한관리
     * 저장
     * @param authList is AuthorizationDto List.(Data on grid)
     * */
    @PutMapping("/")
    public ResponseEntity<String> saveAuthorities(@RequestBody List<AuthorityDto> authList) {
        return authorityService.saveAuthorities(authList);
    }

    /**
     * Subject : 권한관리
     * 행삭제
     * @param
     * */
    @DeleteMapping("/")
    public ResponseEntity<String> deleteAuthority(@RequestParam String roleCd, @RequestParam String compCd) {
        return authorityService.deleteAuthority(roleCd, compCd);
    }

    /**
     * Subject : 권한관리 - 권한별 메뉴
     * 목록 조회
     * @param roleCd is '권한관리' 그리드에서 클릭한 라인의 권한코드
     * @param compCd is '권한관리' 그리드에서 클릭한 라인의 회사코드
     * */
    @GetMapping("/menu")
    public ResponseEntity<List<MenuAuthDto>> getMenuByAuthority(@RequestParam String roleCd, @RequestParam String compCd) {
        List<MenuAuthDto> list = authorityService.getMenuByAuthority(roleCd, compCd);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /**
     * Subject : 권한관리 - 권한별 메뉴
     * 저장
     * @param roleCd is '권한관리' 그리드에서 클릭한 라인의 권한코드
     * @param compCd is '권한관리' 그리드에서 클릭한 라인의 회사코드
     * @param menuAuthList is checked data on grid.
     * */
    @PutMapping("/menu/{roleCd}/{compCd}")
    public ResponseEntity<String> saveMenuByAuthority(@PathVariable String roleCd,
                                                          @PathVariable String compCd,
                                                          @RequestBody List<MenuAuthDto> menuAuthList) {
        return authorityService.saveMenuByAuthority(roleCd, compCd, menuAuthList);
    }

    /**
     * Subject : 권한관리 - 권한별 사용자
     * 목록 조회
     * @param roleCd
     * @param compCd
     * */
    @GetMapping("/user")
    public ResponseEntity<List<AuthorityDto>> getUserInfoByAuthority(@RequestParam String roleCd, @RequestParam String compCd) {
        List<AuthorityDto> list = authorityService.getUserInfoByAuthority(roleCd, compCd);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /**
     * Subject : 권한관리 - 권한별 사용자
     * 저장
     * @param
     * */
    @PutMapping("/user/{roleCd}/{compCd}")
    public ResponseEntity<String> saveUserInfoByAuthority(@PathVariable String roleCd,
                                                              @PathVariable String compCd,
                                                              @RequestBody List<AuthorityDto> authList) {
        return authorityService.saveUserInfoByAuthority(roleCd, compCd, authList);
    }

}
