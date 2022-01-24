package com.iljin.apiServer.template.approval.rule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalRuleRepository extends JpaRepository<ApprovalRule, ApprovalRuleKey> {

    Optional<ApprovalRule> findTopByCompCdAndDocTypeCdAndDtlTypeCdAndCurCdOrderByRuleSeqDesc(String CompCd, String docTypeCd, String dtlTypeCd, String curCd);
}
