package com.iljin.apiServer.template.system.emp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EmployeeKey implements Serializable {
    String empNo;
    String compCd;

    @Builder
    public EmployeeKey(String empNo, String compCd) {
        this.empNo = empNo;
        this.compCd = compCd;
    }

    /* Default Constructor */
    public EmployeeKey() {

    }
}
