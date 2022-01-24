package com.iljin.apiServer.template.approval.dlgt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalDelegateRepository extends JpaRepository<ApprovalDelegate, ApprovalDelegateKey> {

    Optional<ApprovalDelegate> findByAdlgIdAndActId(String adlgId, String actId);

    Optional<ApprovalDelegate> findTopByAdlgIdAndActIdOrderByAdlgSeqDesc(String adlgId, String actId);

    Optional<ApprovalDelegate> findByAdlgIdAndActIdAndCompCdAndAdlgStatCdAndAdlgStrDtLessThanEqualAndAdlgEndDtGreaterThanEqual(String adlgId, String actId, String compCd, String adlgStatCd, String strDt, String endDt);

    List<ApprovalDelegate> findByAdlgStatCdAndAdlgEndDtLessThan(String adlgStatCd, String adlgEndDt);
}
