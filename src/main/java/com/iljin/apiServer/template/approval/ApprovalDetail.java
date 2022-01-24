package com.iljin.apiServer.template.approval;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tb_appr_dt")
@IdClass(ApprovalDetailKey.class)
public class ApprovalDetail {

    //결재번호
    @Id
    @Column(name = "APPR_NO", nullable = false)
    String apprNo;

    //결재순번
    @Id
    @Column(name = "APPR_SEQ", nullable = false)
    Short apprSeq;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "APPR_NO", insertable = false, updatable = false)
    @JsonIgnore
    ApprovalHeader approvalHeader;

    //회사코드
    @Column(name = "COMP_CD")
    String compCd;

    //결재유형코드
    @Column(name = "APPR_TYPE_CD")
    String apprTypeCd;

    //결재구분코드
    @Column(name = "APPR_FG_CD")
    String apprFgCd;

    //결재자사번
    @Column(name = "APRVER_ID")
    String aprverId;

    //결재자성명
    @Column(name = "APRVER_NM")
    String aprverNm;

    //결재자부서명
    @Column(name = "APRVER_DEPT_NM")
    String aprverDeptNm;

    //결재자직급명
    @Column(name = "APRVER_JOB_NM")
    String aprverJobNm;

    //고정결재여부
    @Column(name = "FIX_YN")
    String fixYn;

    //실결재자ID
    @Column(name = "A_APRVER_ID")
    String aAprverId;

    //실결재자성명
    @Column(name = "A_APRVER_NM")
    String aAprverNm;

    //실결재자부서명
    @Column(name = "A_APRVER_DEPT_NM")
    String aAprverDeptNm;

    //실결재자직급명
    @Column(name = "A_APRVER_JOB_NM")
    String aAprverJobNm;

    //결재일시
    @Column(name = "APPR_DTM")
    LocalDateTime apprDtm;

    //결재의견
    @Column(name = "APPR_DESC")
    String apprDesc;

    //비고
    @Column(name = "REMARK")
    String remark;

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
