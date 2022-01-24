package com.iljin.apiServer.template.approval;

import com.iljin.apiServer.template.approval.dlgt.ApprovalDelegateDto;
import com.iljin.apiServer.template.approval.rule.ApprovalRule;
import com.iljin.apiServer.template.approval.rule.ApprovalRuleDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ApprovalService {

    /**
    * 결재할 문서
    * */
    List<ApprovalHeaderDto> getApprTodoList(ApprovalHeaderDto approvalHeaderDto);

    /**
    * 결재한 문서
    * */
    List<ApprovalHeaderDto> getApprDoneList(ApprovalHeaderDto approvalHeaderDto);

    /**
    * 상신한 문서
    * */
    List<ApprovalHeaderDto> getApprReqList(ApprovalHeaderDto approvalHeaderDto);

    /**
    * 결재 상세
    * */
    Map<String, Object> getApproval(String docNo);

    /**
    * 상신 취소
    * */
    @Modifying
    @Transactional
    ResponseEntity<String> cancelApproval(String docNo);

    /**
    * 결재 처리
    * */
    @Modifying
    @Transactional
    ResponseEntity<String> doApproval(ApprovalDetailDto approvalDetailDto);

    /**
    * 결재 상신
    * */
    @Modifying
    @Transactional
    ResponseEntity<ApprovalHeader> requestApproval(ApprovalHeader approvalHeader);

    /**
     * 결재선 지정
     * */
    List<ApprovalEmployeeDto> getApprovalEmpList();

    /**
     * 결재위임관리 - 리스트 조회
     * */
    List<ApprovalDelegateDto> getApprovalDlgtList(ApprovalDelegateDto approvalDelegateDto);

    /**
     * 결재위임관리 - 저장(신규/갱신)
     * */
    @Modifying
    @Transactional
    ResponseEntity<String> saveApprovalDlgt(ApprovalDelegateDto approvalDelegateDto);

    /**
     * 결재위임관리 - 지정해제
     * */
    @Modifying
    @Transactional
    ResponseEntity<String> cancelApprovalDlgt(ApprovalDelegateDto approvalDelegateDto);

    /**
     * 전결규정관리 - 조회
     * */
    List<ApprovalRuleDto> getApprRuleList(ApprovalRuleDto approvalRuleDto);

    /**
     * 전결규정관리 - 행삭제
     * */
    ResponseEntity<String> deleteApprovalRule(List<ApprovalRuleDto> approvalRuleDto);

    /**
     * 전결규정관리 - 신규/수정
     * */
    ResponseEntity<String> saveApprovalRules(List<ApprovalRule> approvalRules);

    List<ApprovalHeaderDto> getApprIngList(ApprovalHeaderDto approvalHeaderDto);

    List<ApprovalHeaderDto> getApprAprList(ApprovalHeaderDto approvalHeaderDto);

    List<ApprovalHeaderDto> getApprRejList(ApprovalHeaderDto approvalHeaderDto);

    List<ApprovalEmployeeDto> getApprovalDeptTreeList();

    List<ApprovalRuleDto> getApprRuleLines(String docTypeCd, String dtlTypeCd, String curCd, String maxAmt);

    ResponseEntity<String> getDelegatingCheck(String adlgId, String actId);
}
