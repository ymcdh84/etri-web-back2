package com.iljin.apiServer.template.bigdata.tag;

import com.iljin.apiServer.core.files.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetTagRepository extends JpaRepository<AssetTag, BigdataAssetTagKey> {

    Optional<AssetTag> findById(BigdataAssetTagKey bigdataAssetTagKey);

    List<AssetTag> findBySiteIdAndAssetIdAndMappingKey(String siteId, String assetId, String mappingKey);

    List<AssetTag> findBySiteId(String siteId);

    List<AssetTag> findBySiteIdAndAssetId(String siteId, String assetId);

    void deleteBySiteIdAndAssetId(String siteId, String assetId);
}
