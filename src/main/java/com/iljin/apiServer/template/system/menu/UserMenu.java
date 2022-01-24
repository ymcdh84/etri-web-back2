package com.iljin.apiServer.template.system.menu;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "A_USER_MENU")
@IdClass(UserMenuKey.class)
public class UserMenu {
    //회사코드
    @Id
    @Column(name = "COMP_CD", nullable = false)
    String compCd;

    //사원번호
    @Id
    @Column(name = "USER_ID", nullable = false)
    String userId;

    //메뉴순번
    @Id
    @Column(name = "MENU_NO", nullable = false)
    String menuNo;

    //메뉴순서
    @Column(name = "MENU_ORDR")
    String menuOrdr;

    //메뉴아이콘코드
    @Column(name = "MENU_ICON_CD")
    String menuIconCd;
}
