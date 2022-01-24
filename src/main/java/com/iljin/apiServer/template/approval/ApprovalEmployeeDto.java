package com.iljin.apiServer.template.approval;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class ApprovalEmployeeDto implements Serializable {

    /* Level Code */
    String lvlCd;

    /* CCTR Code */
    String cctrCd;

    /* CCTR Name */
    String cctrNm;

    /* Upper CCTR Code */
    String upperCd;

    /* Employee No */
    String empNo;

    /* Employee Name */
    String empNm;

    /* Employee Position Code */
    String jobCd;

    /* Employee Position Name */
    String jobNm;

    /* Employee Rank Code */
    String dutCd;

    /* Employee Rank Name */
    String dutNm;

    /* depth on tree */
    String depthNm;

    public ApprovalEmployeeDto(String lvlCd, String cctrCd, String cctrNm, String upperCd, String depthNm) {
        this.lvlCd = lvlCd;
        this.cctrCd = cctrCd;
        this.cctrNm = cctrNm;
        this.upperCd = upperCd;
        this.depthNm = depthNm;
    }

}
