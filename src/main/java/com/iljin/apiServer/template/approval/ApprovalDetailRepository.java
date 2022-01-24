package com.iljin.apiServer.template.approval;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalDetailRepository extends JpaRepository<ApprovalDetail, ApprovalDetailKey> {

    // Optional<ApprovalDetail> findOne(ApprovalDetailKey approvalDetailKey);
    Optional<ApprovalDetail> findByApprNoAndApprSeq(String apprNo, Short apprSeq);

    Optional<ApprovalDetail> findTopByApprNoOrderByApprSeqDesc(String approNo);

    void deleteByApprNo(String apprNo);
}
