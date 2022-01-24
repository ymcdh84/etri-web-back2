package com.iljin.apiServer.template.system.settings;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class SettingsDto {
    BigInteger cnt;

    // 등록여부
    String regYn;
    // 메뉴 번호
    String menuNo;
    // 메뉴명
    String menuName;
    // 메뉴 아이콘 코드
    String menuIconCd;

    // 회사코드
    String compCd;
    // 사용자 아이디
    String loginId;

    public SettingsDto() {}

    public SettingsDto(String regYn, String menuNo, String menuName, String menuIconCd) {
        this.regYn = regYn;
        this.menuNo = menuNo;
        this.menuName = menuName;
        this.menuIconCd = menuIconCd;
    }
}
