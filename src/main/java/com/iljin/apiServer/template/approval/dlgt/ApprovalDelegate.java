package com.iljin.apiServer.template.approval.dlgt;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TB_APPR_DELEGATE")
@IdClass(ApprovalDelegateKey.class)
public class ApprovalDelegate {

    //위임자ID
    @Id
    @Column(name = "ADLG_ID", nullable = false)
    String adlgId;

    //수임자ID
    @Id
    @Column(name = "ACT_ID", nullable = false)
    String actId;

    //순번
    @Id
    @Column(name = "ADLG_SEQ", nullable = false)
    Short adlgSeq;

    //회사코드
    @Column(name = "COMP_CD")
    String compCd;

    //위임상태코드
    @Column(name = "ADLG_STAT_CD")
    String adlgStatCd;

    //ADLG_STR_DT
    @Column(name = "ADLG_STR_DT")
    String adlgStrDt;

    //위임종료일자
    @Column(name = "ADLG_END_DT")
    String adlgEndDt;

    //위임사유내용
    @Column(name = "ADLG_RSN")
    String adlgRsn;

    //위임실행일시
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ADLG_EXE_DTM")
    LocalDateTime adlgExeDtm;

    //위임해제일시
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ADLG_RVC_DTM")
    LocalDateTime adlgRvcDtm;

    //등록자ID
    @Column(name = "REG_ID")
    String regId;

    //등록일시
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "REG_DTM", nullable = false)
    LocalDateTime regDtm;

    //수정자ID
    @Column(name = "CHG_ID")
    String chgId;

    //수정일시
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CHG_DTM", nullable = false)
    LocalDateTime chgDtm;
}
