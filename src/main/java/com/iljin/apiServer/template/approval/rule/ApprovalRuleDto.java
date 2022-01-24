package com.iljin.apiServer.template.approval.rule;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ApprovalRuleDto implements Serializable {

    /* 회사코드 */
    String compCd;

    /* 문서유형코드 */
    String docTypeCd;
    String docTypeNm;

    /* 상세유형코드 */
    String dtlTypeCd;
    String dtlTypeNm;

    /* 통화코드 */
    String curCd;
    String curNm;

    /* 순번 */
    Short ruleSeq;

    /* 사용여부 */
    String useYn;

    /* 상한금액 */
    BigDecimal maxAmt;

    /* 결재유형코드 */
    String apprTypeCd;
    String apprTypeNm;

    /* 고정결재여부 */
    String fixYn;

    /* 결재자분류코드 */
    String aprverClassCd;
    String aprverClassNm;

    /* 결재자분류값 */
    String aprverClassVal;

    /* 비고 */
    String remark;

    String empNo;
    String empNm;
    String jobNm;
    String deptNm;

    /*
     * getApprRuleList
     */

    public ApprovalRuleDto(String compCd, String docTypeCd, String docTypeNm, String dtlTypeCd, String dtlTypeNm,
            String curCd, String curNm, Short ruleSeq, String useYn, BigDecimal maxAmt, String apprTypeCd,
            String apprTypeNm, String fixYn, String aprverClassCd, String aprverClassNm, String aprverClassVal,
            String remark) {
        this.compCd = compCd;
        this.docTypeCd = docTypeCd;
        this.docTypeNm = docTypeNm;
        this.dtlTypeCd = dtlTypeCd;
        this.dtlTypeNm = dtlTypeNm;
        this.curCd = curCd;
        this.curNm = curNm;
        this.ruleSeq = ruleSeq;
        this.useYn = useYn;
        this.maxAmt = maxAmt;
        this.apprTypeCd = apprTypeCd;
        this.apprTypeNm = apprTypeNm;
        this.fixYn = fixYn;
        this.aprverClassCd = aprverClassCd;
        this.aprverClassNm = aprverClassNm;
        this.aprverClassVal = aprverClassVal;
        this.remark = remark;
    }

    /*
     * EA-05-09 전결규정관리 - 적용 getApprRuleLines
     */
    // empNo
    String aprverId;
    // empNm
    String aprverUser;
    // deptNm
    String cctrNm;
    // apprTypeNm
    String apprType;

    public ApprovalRuleDto(Short ruleSeq, BigDecimal maxAmt, String apprTypeCd, String apprType, String fixYn,
            String aprverClassCd, String aprverClassVal, String aprverId, String aprverUser, String jobNm,
            String cctrNm) {
        this.ruleSeq = ruleSeq;
        this.maxAmt = maxAmt;
        this.apprTypeCd = apprTypeCd;
        this.apprType = apprType;
        this.fixYn = fixYn;
        this.aprverClassCd = aprverClassCd;
        this.aprverClassVal = aprverClassVal;
        this.aprverId = aprverId;
        this.aprverUser = aprverUser;
        this.jobNm = jobNm;
        this.cctrNm = cctrNm;
    }
}
