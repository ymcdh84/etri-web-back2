package com.iljin.apiServer.template.approval;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tb_appr_hd")
public class ApprovalHeader {

    // 결재번호
    @Id
    @Column(name = "APPR_NO", nullable = false)
    String apprNo;

    @OneToMany(mappedBy = "approvalHeader", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<ApprovalDetail> approvalDetails = new ArrayList<ApprovalDetail>();

    // 회사코드
    @Column(name = "COMP_CD")
    String compCd;

    // 문서유형코드
    @Column(name = "DOC_TYPE_CD")
    String docTypeCd;

    // 문서관리번호
    @Column(name = "DOC_MNG_NO")
    String docMngNo;

    // 문서제목
    @Column(name = "DOC_TITLE_NM")
    String docTitleNm;

    // 문서상태코드
    @Column(name = "DOC_STAT_CD")
    String docStatCd;

    // 기안자ID
    @Column(name = "DRAFT_ID")
    String draftId;

    // 기안일시
    @Column(name = "DRAFT_DTM")
    LocalDateTime draftDtm;

    // 최종순번
    @Column(name = "FNL_SEQ")
    Short fnlSeq;

    // 비고
    @Column(name = "REMARK")
    String remark;

    // 문서고유번호
    @Column(name = "DOC_IN_VAL")
    String docInVal;

    // 등록자ID
    @Column(name = "REG_ID")
    String regId;

    // 등록일시
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "REG_DTM", nullable = false)
    LocalDateTime regDtm;

    // 수정자ID
    @Column(name = "CHG_ID")
    String chgId;

    // 수정일시
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CHG_DTM", nullable = false)
    LocalDateTime chgDtm;
}
