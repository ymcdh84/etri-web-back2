package com.iljin.apiServer.template.system.dept;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDto {
    String compCd;
    String deptCd;
    String deptNm;
    String upperDeptCd;
    String upperDeptNm;

    public DepartmentDto(String deptCd, String deptNm, String upperDeptCd, String upperDeptNm) {
        this.deptCd = deptCd;
        this.deptNm = deptNm;
        this.upperDeptCd = upperDeptCd;
        this.upperDeptNm = upperDeptNm;
    }
}
