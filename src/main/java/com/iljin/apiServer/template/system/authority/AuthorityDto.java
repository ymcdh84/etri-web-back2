package com.iljin.apiServer.template.system.authority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityDto {
    //권한코드
    String roleCd;
    //회사코드
    String compCd;
    //권한명
    String roleNm;
    //조회권한
    String roleSelectCd;
    String roleSelectNm;
    //권한설명
    String roleDc;

    public AuthorityDto() {

    }

    public AuthorityDto(String roleCd, String compCd, String roleNm, String roleSelectCd, String roleSelectNm) {
        this.roleCd = roleCd;
        this.compCd = compCd;
        this.roleNm = roleNm;
        this.roleSelectCd = roleSelectCd;
        this.roleSelectNm = roleSelectNm;
    }

    String roleChk;
    String crtRole;
    Integer id;
    String empNo;
    String empNm;
    String jobDutNm;
    String deptNm;
    public AuthorityDto(String roleChk, String crtRole, String empNo, String empNm, String jobDutNm, String deptNm, String compCd) {
        this.roleChk = roleChk;
        this.crtRole = crtRole;
        this.empNo = empNo;
        this.empNm = empNm;
        this.jobDutNm = jobDutNm;
        this.deptNm = deptNm;
        this.compCd = compCd;
    }
}
