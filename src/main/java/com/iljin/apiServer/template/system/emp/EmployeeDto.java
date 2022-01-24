package com.iljin.apiServer.template.system.emp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeeDto {
    String compCd;
    String compNm;

    String empNo;
    String empNm;

    String password;

    String deptCd;
    String deptNm;
    String upperDeptCd;
    String upperDeptNm;

    String jobDutCd;
    String jobDutNm;
    String jobGradeCd;
    String jobGradeNm;

    String role;
    String roleNm;

    String serveCd;
    String serveNm;

    boolean enableFlag;

    String email;
    String mobTelNo;

    LocalDateTime creationDate;
    LocalDateTime modifiedDate;

    public EmployeeDto(String compCd, String compNm, String empNo, String empNm, String deptCd, String deptNm, String upperDeptCd, String upperDeptNm, String jobDutCd, String jobDutNm, String jobGradeCd, String jobGradeNm, String role, String roleNm, String serveCd, String serveNm, boolean enableFlag, String email, String mobTelNo) {
        this.compCd = compCd;
        this.compNm = compNm;
        this.empNo = empNo;
        this.empNm = empNm;
        this.deptCd = deptCd;
        this.deptNm = deptNm;
        this.upperDeptCd = upperDeptCd;
        this.upperDeptNm = upperDeptNm;
        this.jobDutCd = jobDutCd;
        this.jobDutNm = jobDutNm;
        this.jobGradeCd = jobGradeCd;
        this.jobGradeNm = jobGradeNm;
        this.role = role;
        this.roleNm = roleNm;
        this.serveCd = serveCd;
        this.serveNm = serveNm;
        this.enableFlag = enableFlag;
        this.email = email;
        this.mobTelNo = mobTelNo;
    }

    public EmployeeDto(String compCd, String compNm, String empNo, String empNm
            , String deptCd, String deptNm, String upperDeptCd, String upperDeptNm
            , String jobDutCd, String jobDutNm, String jobGradeCd, String jobGradeNm
            , String role, String roleNm
            , String serveCd, String serveNm, boolean enableFlag
            , String email, String mobTelNo
            , LocalDateTime creationDate, LocalDateTime modifiedDate) {
        this.compCd = compCd;
        this.compNm = compNm;
        this.empNo = empNo;
        this.empNm = empNm;
        this.deptCd = deptCd;
        this.deptNm = deptNm;
        this.upperDeptCd = upperDeptCd;
        this.upperDeptNm = upperDeptNm;
        this.jobDutCd = jobDutCd;
        this.jobDutNm = jobDutNm;
        this.jobGradeCd = jobGradeCd;
        this.jobGradeNm = jobGradeNm;
        this.role = role;
        this.roleNm = roleNm;
        this.serveCd = serveCd;
        this.serveNm = serveNm;
        this.enableFlag = enableFlag;
        this.email = email;
        this.mobTelNo = mobTelNo;
        this.creationDate = creationDate;
        this.modifiedDate = modifiedDate;
    }

    public EmployeeDto() {

    }

    public EmployeeDto(String deptCd, String deptNm, String upperDeptCd, String upperDeptNm) {
        this.deptCd = deptCd;
        this.deptNm = deptNm;
        this.upperDeptCd = upperDeptCd;
        this.upperDeptNm = upperDeptNm;
    }
}
