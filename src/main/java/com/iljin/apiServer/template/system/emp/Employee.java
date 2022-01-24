package com.iljin.apiServer.template.system.emp;

import com.iljin.apiServer.core.security.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tb_mst_emp")
@IdClass(EmployeeKey.class)
public class Employee {
    // 사원번호
    @Id
    @Column(name = "emp_no", nullable = false)
    private String empNo;

    // 회사코드
    @Id
    @Column(name = "comp_cd", nullable = false)
    private String compCd;

    // 사원성명
    @Column(name = "emp_nm")
    private String empNm;

    // 회사명
    @Column(name = "comp_nm")
    private String compNm;

    // 부서코드
    @Column(name = "dept_cd")
    private String deptCd;

    // 부서명
    @Column(name = "dept_nm")
    private String deptNm;

    // 비용부서코드
    @Column(name = "cctr_cd")
    private String cctrCd;

    // 비용부서명
    @Column(name = "cctr_nm")
    private String cctrNm;

    // 사업장코드
    @Column(name = "bp_cd")
    private String bpCd;

    // 사업장명
    @Column(name = "bp_nm")
    private String bpNm;

    // 상위부서코드
    @Column(name = "upper_dept_cd")
    private String upperDeptCd;

    // 상위부서명
    @Column(name = "upper_dept_nm")
    private String upperDeptNm;

    // 사원구분코드
    @Column(name = "emp_dv_cd")
    private String empDvCd;

    // 사원구분명
    @Column(name = "emp_dv_nm")
    private String empDvNm;

    // 직군코드
    @Column(name = "job_group_cd")
    private String jobGroupCd;

    // 직군명
    @Column(name = "job_group_nm")
    private String jobGroupNm;

    // 직책코드
    @Column(name = "job_dut_cd")
    private String jobDutCd;

    // 직책명
    @Column(name = "job_dut_nm")
    private String jobDutNm;

    // 직급코드
    @Column(name = "job_grade_cd")
    private String jobGradeCd;

    // 직급명
    @Column(name = "job_grade_nm")
    private String jobGradeNm;

    // 직무코드
    @Column(name = "job_cd")
    private String jobCd;

    // 직무명
    @Column(name = "job_nm")
    private String jobNm;

    // 호칭코드
    @Column(name = "title_cd")
    private String titleCd;

    // 호칭명
    @Column(name = "title_nm")
    private String titleNm;

    // 재직상태코드(10:재직, 20:휴직, 30:퇴직)
    @Column(name = "serve_cd")
    private String serveCd;

    // 재직상태명
    @Column(name = "serve_nm")
    private String serveNm;

    // 채용구분코드
    @Column(name = "hire_cd")
    private String hireCd;

    // 채용구분명
    @Column(name = "hire_nm")
    private String hireNm;

    // 그룹입사일자
    @Column(name = "grp_join_dt")
    private String grpJoinDt;

    // 입사일자
    @Column(name = "join_dt")
    private String joinDt;

    // 퇴사일자
    @Column(name = "retire_dt")
    private String retireDt;

    // 이메일
    @Column(name = "email")
    private String email;

    // 사무실전화번호
    @Column(name = "off_tel_no")
    private String offTelNo;

    // 모바일전화번호
    @Column(name = "mob_tel_no")
    private String mobTelNo;

    // 거래처코드
    @Column(name = "vend_cd")
    private String vendCd;

    // IF ID
    @Column(name = "picode")
    private String picode;

    // IF 상태
    @Column(name = "pistat")
    private String pistat;

    // IF 일자
    @Column(name = "pidate")
    private String pidate;

    // IF 시간
    @Column(name = "pitime")
    private String pitime;

    // IF 유저
    @Column(name = "piuser")
    private String piuser;

    // 전송메시지
    @Column(name = "pimsg")
    private String pimsg;

    // 메시지ID
    @Column(name = "pimsgid")
    private String pimsgid;

    @OneToOne(mappedBy = "employee")
    private User user;

    @Builder
    public Employee(String compCd, String compNm, String empNo, String empNm, String deptCd, String deptNm, String bpCd, String bpNm, String upperDeptCd, String upperDeptNm, String jobDutCd, String jobDutNm, String jobGradeCd, String jobGradeNm, String serveCd, String serveNm, String email, String mobTelNo) {
        this.compCd = compCd;
        this.compNm = compNm;
        this.empNo = empNo;
        this.empNm = empNm;
        this.deptCd = deptCd;
        this.deptNm = deptNm;
        this.bpCd = bpCd;
        this.bpNm = bpNm;
        this.upperDeptCd = upperDeptCd;
        this.upperDeptNm = upperDeptNm;
        this.jobDutCd = jobDutCd;
        this.jobDutNm = jobDutNm;
        this.jobGradeCd = jobGradeCd;
        this.jobGradeNm = jobGradeNm;
        this.serveCd = serveCd;
        this.serveNm = serveNm;
        this.email = email;
        this.mobTelNo = mobTelNo;
    }

    public Employee updateEmployee(String empNm, String deptCd, String deptNm, String upperDeptCd, String upperDeptNm, String jobDutCd, String jobDutNm, String jobGradeCd, String jobGradeNm,
                                   String serveCd, String serveNm, String email, String mobTelNo) {
        this.empNm = empNm;
        this.deptCd = deptCd;
        this.deptNm = deptNm;
        this.upperDeptCd = upperDeptCd;
        this.upperDeptNm = upperDeptNm;
        this.jobDutCd = jobDutCd;
        this.jobDutNm = jobDutNm;
        this.jobGradeCd = jobGradeCd;
        this.jobGradeNm = jobGradeNm;
        this.serveCd = serveCd;
        this.serveNm = serveNm;
        this.email = email;
        this.mobTelNo = mobTelNo;

        return this;
    }
}
