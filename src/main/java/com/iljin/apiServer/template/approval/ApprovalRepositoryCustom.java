package com.iljin.apiServer.template.approval;

import com.iljin.apiServer.template.approval.dlgt.ApprovalDelegateDto;
import com.iljin.apiServer.template.approval.rule.ApprovalRuleDto;

import java.util.List;

public interface ApprovalRepositoryCustom {

    List<ApprovalHeaderDto> getApprTodoList(ApprovalHeaderDto approvalHeaderDto);

    List<ApprovalHeaderDto> getApprDoneList(ApprovalHeaderDto approvalHeaderDto);

    List<ApprovalHeaderDto> getApprReqList(ApprovalHeaderDto approvalHeaderDto);

    List<ApprovalHeaderDto> getApprHeader(String docNo);

    List<ApprovalDetailDto> getApprDetail(String docNo);

    List<ApprovalEmployeeDto> getApprovalEmpList();

    List<ApprovalDelegateDto> getApprovalDlgtList(ApprovalDelegateDto approvalDelegateDto);

    List<ApprovalRuleDto> getApprRuleList(ApprovalRuleDto approvalRuleDto);

    List<ApprovalHeaderDto> getApprIngList(ApprovalHeaderDto approvalHeaderDto);

    List<ApprovalHeaderDto> getApprAprList(ApprovalHeaderDto approvalHeaderDto);

    List<ApprovalHeaderDto> getApprRejList(ApprovalHeaderDto approvalHeaderDto);

    List<ApprovalEmployeeDto> getApprovalDeptTreeList();

    List<ApprovalRuleDto> getApprRuleLines(String loginId, ApprovalRuleDto approvalRuleDto);
}
