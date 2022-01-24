package com.iljin.apiServer.template.asset.chartdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PCAChartRepository extends JpaRepository<PCAChart, PCAChartKey > {
    //List<PCAChart> findByAssetIdContainsAndStageDivCdContains(String assetId,String stageDivCd);
    @Query(value = "" +
            "SELECT var_no, eigenvalue, cpv, th_cpv " +
            "  FROM tb_pca_cpv_data" +
            " WHERE 1=1 " +
            "   AND asset_id = :assetId" +
            "   AND stage_div_cd = :stageDivCd", nativeQuery=true)
    ArrayList<ArrayList> findByAssetIdContainsAndStageDivCdContains(String assetId,String stageDivCd);

    @Query(value = "" +
            "SELECT val, th_val " +
            "FROM tb_frequency_chart_data " +
            "WHERE 1=1 " +
            "AND asset_id = :assetId " +
            "AND detection_index_cd = :detectionIndexCd " +
            "AND stage_div_cd = :stageDivCd ", nativeQuery=true)
    ArrayList<ArrayList> getHistogramData(String assetId, String detectionIndexCd, String stageDivCd);
}

