package com.iljin.apiServer.template.approval;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalHeaderRepository extends JpaRepository<ApprovalHeader, String> {

    Optional<ApprovalHeader> findByApprNo(String apprNo);

    @Query(value = "SELECT FN_CMN_GET_MNG_NO('AP')", nativeQuery = true)
    String getApprNo();

    Optional<ApprovalHeader> findByDocMngNo(String docMngNo);
}
