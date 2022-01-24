package com.iljin.apiServer.template.system.menu;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MenuDto implements Serializable {
    //회사코드
    String compCd;
    //메뉴 순번
    String menuNo;
    //메뉴명
    String menuNm = "";
    //프로그램명
    String programFileNm;
    //상위메뉴명
    String upperMenuNo;
    //메뉴순서
    Integer menuOrder;
    //메뉴설명
    String menuDc;
    //연결이미지경로
    String relateImagePath;
    //연결이미지명
    String relateImageNm;

    /*
    * getAllMenuList
    *  */
    public MenuDto(Integer menuOrder, String menuNo, String menuNm, String upperMenuNo, String programFileNm) {
        this.menuOrder = menuOrder;
        this.menuNo = menuNo;
        this.menuNm = menuNm;
        this.upperMenuNo = upperMenuNo;
        this.programFileNm = programFileNm;
    }

    /*
    * 프로그램 관리
    * */
    public MenuDto(String compCd, String menuNo, String upperMenuNo, String menuNm, String programFileNm, Integer menuOrder, String relateImageNm, String relateImagePath, String menuDc) {
        this.compCd = compCd;
        this.menuNo = menuNo;
        this.upperMenuNo = upperMenuNo;
        this.menuNm = menuNm;
        this.programFileNm = programFileNm;
        this.menuOrder = menuOrder;
        this.relateImageNm = relateImageNm;
        this.relateImagePath = relateImagePath;
        this.menuDc = menuDc;
    }

    /*
     * getMenuListByAuthority
     *  */
    public MenuDto(String menuNo, String menuNm, String programFileNm, String upperMenuNo, Integer menuOrder) {
        this.menuNo = menuNo;
        this.menuNm = menuNm;
        this.programFileNm = programFileNm;
        this.upperMenuNo = upperMenuNo;
        this.menuOrder = menuOrder;
    }

    public MenuDto() {

    }
}
