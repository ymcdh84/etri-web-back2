package com.iljin.apiServer.template.approval.rule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TB_APPR_RULE")
@IdClass(ApprovalRuleKey.class)
public class ApprovalRule {

    // 회사코드
    @Id
    @Column(name = "COMP_CD", nullable = false)
    String compCd;

    // 문서유형코드
    @Id
    @Column(name = "DOC_TYPE_CD", nullable = false)
    String docTypeCd;

    // 상세유형코드
    @Id
    @Column(name = "DTL_TYPE_CD", nullable = false)
    String dtlTypeCd;

    // 통화코드
    @Id
    @Column(name = "CUR_CD", nullable = false)
    String curCd;

    // 순번
    @Id
    @Column(name = "RULE_SEQ", nullable = false)
    Short ruleSeq;

    // 사용여부
    @Column(name = "USE_YN")
    String useYn;

    // 상한금액
    @Column(name = "MAX_AMT")
    BigDecimal maxAmt;

    // 결재유형코드
    @Column(name = "APPR_TYPE_CD")
    String apprTypeCd;

    // 고정결재여부
    @Column(name = "FIX_YN")
    String fixYn;

    // 결재자분류코드
    @Column(name = "APRVER_CLASS_CD")
    String aprverClassCd;

    // 결재자분류값
    @Column(name = "APRVER_CLASS_VAL")
    String aprverClassVal;

    // 비고
    @Column(name = "REMARK")
    String remark;

    // 등록자ID
    @Column(name = "REG_ID", nullable = false)
    String regId;

    // 등록일시
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "REGI_DTM", nullable = false)
    LocalDateTime regiDtm;

    // 변경자ID
    @Column(name = "CHG_ID", nullable = false)
    String chgId;

    // 변경일시
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CHG_DTM", nullable = false)
    LocalDateTime chgDtm;
}
