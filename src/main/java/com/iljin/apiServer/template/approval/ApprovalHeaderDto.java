package com.iljin.apiServer.template.approval;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ApprovalHeaderDto implements Serializable {

    /**/
    String loginId;

    /* 문서유형 */
    String docTypeCd;
    String docType;

    /* 문서상태 */
    String docStatCd;
    String docStatNm;

    /* 제목 */
    String docTitleNm;

    /* 결재유형 */
    String apprType;

    /* 결재구분 */
    String apprStatus;

    /* 문서번호 */
    String apprNo;
    /* 문서관리번호 */
    String docMngNo;

    /* 기안자 - empNo, empName, empRank */
    String draftUserId;
    String draftUserName;
    String draftUserJob;

    /* 기안일자 */
    String draftDtm;

    /* 추가조회 */
    String searchDtmFr;
    String searchDtmTo;
    String delegateChk;// 위임여부

    /* for Mobile */
    BigDecimal totAmt;// TB_SLIP_HD.TOT_AMT
    String hdSgtxt;// 전표 적요
    String slipApprDtm;// 전표 결재 기안일시
    BigDecimal reqTotAmt;// TB_BUD_HD.REQ_TOT_AMT
    String reqRsn;// 신청 사유
    String budApprDtm;// 예산신청 결재 기안일시

    /* Constructor */
    /*
     * approvalRepositoryCustom resultMapping on qlrm getApprTodoList() /
     * getApprDoneList() / getApprReqList
     */
    public ApprovalHeaderDto(String docTypeCd, String docType, String docStatCd, String docStatNm, String docTitleNm,
            String apprType, String apprStatus, String apprNo, String docMngNo, String draftUserId,
            String draftUserName, String draftUserJob, String draftDtm, BigDecimal totAmt, String hdSgtxt,
            String slipApprDtm, BigDecimal reqTotAmt, String reqRsn, String budApprDtm) {
        this.docTypeCd = docTypeCd;
        this.docType = docType;
        this.docStatCd = docStatCd;
        this.docStatNm = docStatNm;
        this.docTitleNm = docTitleNm;
        this.apprType = apprType;
        this.apprStatus = apprStatus;
        this.apprNo = apprNo;
        this.docMngNo = docMngNo;
        this.draftUserId = draftUserId;
        this.draftUserName = draftUserName;
        this.draftUserJob = draftUserJob;
        this.draftDtm = draftDtm;
        this.totAmt = totAmt;
        this.hdSgtxt = hdSgtxt;
        this.slipApprDtm = slipApprDtm;
        this.reqTotAmt = reqTotAmt;
        this.reqRsn = reqRsn;
        this.budApprDtm = budApprDtm;
    }

    /*
     * approvalRepositoryCustom.getApprHeader() resultMapping on qlrm
     */
    public ApprovalHeaderDto(String docTypeCd, String docType, String docStatCd, String docStatNm, String docTitleNm,
            String apprType, String apprStatus, String apprNo, String docMngNo, String draftUserId,
            String draftUserName, String draftUserJob, String draftDtm) {
        this.docTypeCd = docTypeCd;
        this.docType = docType;
        this.docStatCd = docStatCd;
        this.docStatNm = docStatNm;
        this.docTitleNm = docTitleNm;
        this.apprType = apprType;
        this.apprStatus = apprStatus;
        this.apprNo = apprNo;
        this.docMngNo = docMngNo;
        this.draftUserId = draftUserId;
        this.draftUserName = draftUserName;
        this.draftUserJob = draftUserJob;
        this.draftDtm = draftDtm;
    }

}
