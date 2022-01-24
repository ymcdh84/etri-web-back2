package com.iljin.apiServer.template.system.authority;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "A_MENU_ROLE")
@IdClass(MenuAuthKey.class)
public class MenuAuth {
    //권한코드
    @Id
    @Column(name = "role_cd")
    String roleCd;

    //메뉴순번
    @Id
    @Column(name = "menu_no")
    String menuNo;

    //회사코드
    @Id
    @Column(name = "comp_cd")
    String compCd;

    @Builder
    public MenuAuth(String roleCd, String menuNo, String compCd) {
        this.roleCd = roleCd;
        this.menuNo = menuNo;
        this.compCd = compCd;
    }
}
