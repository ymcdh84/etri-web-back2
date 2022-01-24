package com.iljin.apiServer.template.system.menu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserMenuKey implements Serializable {

    /**
     *
     */
    String compCd;
    String userId;
    String menuNo;
}
