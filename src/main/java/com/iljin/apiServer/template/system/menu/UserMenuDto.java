package com.iljin.apiServer.template.system.menu;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserMenuDto {

    //회사코드
    String compCd;
    //사원번호
    String userId;
    //메뉴순번
    String menuNo;
    //메뉴명
    String menuNm;
    //프로그램파일명
    String programFileNm;
    //메뉴순서
    String menuOrdr;
    //메뉴아이콘코드
    String menuIconCd;

    public UserMenuDto(String menuNo, String menuNm, String programFileNm, String menuIconCd) {
        this.menuNo = menuNo;
        this.menuNm = menuNm;
        this.programFileNm = programFileNm;
        this.menuIconCd = menuIconCd;
    }
}
