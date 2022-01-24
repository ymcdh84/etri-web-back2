package com.iljin.apiServer.template.approval.dlgt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApprovalDelegateDto implements Serializable {

    /* 순번 */
    Short adlgSeq;

    /* 위임자ID */
    String adlgId;
    String adlgNm;

    /* 수임자ID */
    String actId;
    String actNm;

    /* 회사코드 */
    String compCd;

    /* 위임상태코드 */
    String adlgStatCd;
    String adlgStatNm;

    /* 위임시작일자 */
    String adlgStrDt;

    /* 위임종료일자 */
    String adlgEndDt;

    /* 위임사유내용 */
    @JsonIgnore
    String adlgRsn;

    /* 위임실행일시 */
    @JsonIgnore
    Timestamp adlgExeDtm;

    /* 위임해제일시 */
    @JsonIgnore
    Timestamp adlgRvcDtm;
}
