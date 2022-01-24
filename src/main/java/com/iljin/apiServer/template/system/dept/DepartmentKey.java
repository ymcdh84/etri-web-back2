package com.iljin.apiServer.template.system.dept;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DepartmentKey implements Serializable {
    String compCd;
    String deptNo;

    @Builder
    public DepartmentKey(String compCd, String deptNo) {
        this.compCd = compCd;
        this.deptNo = deptNo;
    }

    /* Default Constructor */
    public DepartmentKey() {}
}
