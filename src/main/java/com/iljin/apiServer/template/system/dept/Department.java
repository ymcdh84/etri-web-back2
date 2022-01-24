package com.iljin.apiServer.template.system.dept;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "TB_MST_DEPT")
@IdClass(DepartmentKey.class)
public class Department {
    // 회사코드
    @Id
    @Column(name = "COMP_CD", nullable = false)
    private String compCd;

    @Id
    @Column(name = "DEPT_NO", nullable = false)
    private String deptNo;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @Column(name = "ADMR_DEPT_NO")
    private String admrDeptNo;

    @Column(name = "LOCATION")
    private String location;

    @Builder
    public Department(String compCd, String deptNo, String deptName, String admrDeptNo, String location) {
        this.compCd = compCd;
        this.deptNo = deptNo;
        this.deptName = deptName;
        this.admrDeptNo = admrDeptNo;
        this.location = location;
    }
}
