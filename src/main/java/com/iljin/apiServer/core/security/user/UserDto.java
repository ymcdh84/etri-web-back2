package com.iljin.apiServer.core.security.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserDto implements Serializable {
    Long id;
    String loginCompCd;
    String loginCompNm;
    String loginDeptCd;
    String loginDeptNm;
    String loginJobCd;
    String loginJobNm;
    String loginDutCd;
    String loginDutNm;
    String loginId;
    String loginPw;
    String userName;
    String attribute1;
    String attribute2;
    String attribute3;
    String attribute4;
    String attribute5;
    boolean enableFlag;
    List<String> roles;
    String role;

    /*
     * added on 26.08.2019
     * Login history
     * */
    //log_id
    Long logId;
    String connectId;
    String connectIp;
    //W:web, M:Mobile
    String connectMthd;
    //error msg
    String connectError;
    String connectUrl;
    LocalDateTime creationDate;

    /* 27.08.2019 */
    String compCd;
    String deptCd;

    /*
     * for OAuth 2.0
     * @ 14.08.2020
     * */
    String token;

    public UserDto(Long id,
                   String loginId,
                   String loginPw,
                   String userName,
                   boolean enableFlag,
                   String role,
                   List<String> roles) {
        this.id = id;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.userName = userName;
        this.enableFlag = enableFlag;
        this.roles = roles;
        this.role = role;
    }

    public UserDto() {
    }
}
