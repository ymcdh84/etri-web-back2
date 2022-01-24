package com.iljin.apiServer.template.bigdata.asset;

import com.iljin.apiServer.template.bigdata.connect.BigdataConnect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BigdataAssetRepository extends JpaRepository<BigdataAsset, BigdataAssetKey> {
    List<BigdataConnect> findBySiteId(String siteId);
}
