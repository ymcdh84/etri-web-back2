package com.iljin.apiServer.template.bigdata.lndata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearningLotNoRepository extends JpaRepository<LearningLotNo, LearningLotNoKey> {

    Optional<LearningLotNo> findById(LearningLotNoKey learningDataKey);

    void deleteBySiteIdAndAssetId(String siteId, String assetId);
}
