package com.iljin.apiServer.core.security;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@Getter
public class AuthToken {
    private String userName;
    private String loginId;
    private String loginCompCd;
    private String loginCompNm;
    private String loginDeptCd;
    private String loginDeptNm;
    private String loginJobDutCd;
    private String loginJobDutNm;
    private String loginJobGradeCd;
    private String loginJobGradeNm;
    private String token;
    private String attribute2;
    private Collection authorities;

    @Builder
    public AuthToken(String userName,
                     String loginId,
                     String loginCompCd,
                     String loginCompNm,
                     String loginDeptCd,
                     String loginDeptNm,
                     String loginJobDutCd,
                     String loginJobDutNm,
                     String loginJobGradeCd,
                     String loginJobGradeNm,
                     String token,
                     String attribute2,
                     Collection authorities) {
        this.userName = userName;
        this.loginId = loginId;
        this.loginCompCd = loginCompCd;
        this.loginCompNm = loginCompNm;
        this.loginDeptCd = loginDeptCd;
        this.loginDeptNm = loginDeptNm;
        this.loginJobDutCd = loginJobDutCd;
        this.loginJobDutNm = loginJobDutNm;
        this.loginJobGradeCd = loginJobGradeCd;
        this.loginJobGradeNm = loginJobGradeNm;
        this.token = token;
        this.attribute2 = attribute2;
        this.authorities = authorities;
    }
}
