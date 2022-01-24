package com.iljin.apiServer.template.system.menu;

import com.iljin.apiServer.core.security.role.UserRole;
import com.iljin.apiServer.template.system.authority.MenuAuth;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "A_MENU")
@IdClass(MenuKey.class)
public class Menu {
    //회사코드
    @Id
    @Column(name = "COMP_CD", nullable = false)
    String compCd;

    //메뉴순번
    @Id
    @Column(name = "MENU_NO", nullable = false)
    String menuNo;

    //메뉴명
    @Column(name = "MENU_NM")
    String menuNm;

    //프로그램명
    @Column(name = "PROGRAM_FILE_NM")
    String programFileNm;

    //상위메뉴명
    @Column(name = "UPPER_MENU_NO")
    String upperMenuNo;

    //메뉴순서
    @Column(name = "MENU_ORDR")
    Integer menuOrder;

    //메뉴설명
    @Column(name = "MENU_DC")
    String menuDc;

    //연결이미지경로
    @Column(name = "RELATE_IMAGE_PATH")
    String relateImagePath;

    //연결이미지명
    @Column(name = "RELATE_IMAGE_NM")
    String relateImageNm;

    @Builder
    public Menu(String compCd, String menuNo, String menuNm, String programFileNm, String upperMenuNo, Integer menuOrder,
                String menuDc, String relateImagePath, String relateImageNm) {
        this.compCd = compCd;
        this.menuNo = menuNo;
        this.menuNm = menuNm;
        this.programFileNm = programFileNm;
        this.upperMenuNo = upperMenuNo;
        this.menuOrder = menuOrder;
        this.menuDc = menuDc;
        this.relateImagePath = relateImagePath;
        this.relateImageNm = relateImageNm;
    }

    public Menu updateMenu(String menuNo, String upperMenuNo, String menuNm, String programFileNm, Integer menuOrder, String relateImageNm, String relateImagePath, String menuDc) {
        this.menuNo = menuNo;
        this.upperMenuNo = upperMenuNo;
        this.menuNm = menuNm;
        this.programFileNm = programFileNm;
        this.menuOrder = menuOrder;
        this.relateImageNm = relateImageNm;
        this.relateImagePath = relateImagePath;
        this.menuDc = menuDc;

        return this;
    }
}
