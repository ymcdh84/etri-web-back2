package com.iljin.apiServer.template.system.menu;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class MenuKey implements Serializable {
    /**
     *
     */
    String compCd;
    String menuNo;

    @Builder
    public MenuKey(String compCd, String menuNo) {
        this.compCd = compCd;
        this.menuNo = menuNo;
    }
}
