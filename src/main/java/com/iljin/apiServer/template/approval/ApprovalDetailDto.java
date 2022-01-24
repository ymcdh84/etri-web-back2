package com.iljin.apiServer.template.approval;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ApprovalDetailDto implements Serializable {

    /* 문서번호 */
    String apprNo;

    /* 결재순번 */
    Short apprSeq;

    /* 결재유형 */
    String apprTypeCd;
    String apprType;

    /* 결재자 */
    String aprverId;
    String aprverUser;

    /* 실제결재자 */
    String aAprverId;
    String aAprverUser;

    /* 결재상태 */
    String apprFgCd;
    String apprStatus;

    /* 결재일자 */
    String apprDtm;

    /* 의견 */
    String apprDesc;

}
