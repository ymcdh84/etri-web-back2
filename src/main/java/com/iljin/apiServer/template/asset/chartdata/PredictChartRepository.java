package com.iljin.apiServer.template.asset.chartdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PredictChartRepository extends JpaRepository<PredictChart, String > {
    List<PredictChart> findByAssetIdContainsAndStageDivCdContainsAndPredictDivCdContains(String assetId, String stageDivCd, String predictDivCd);

    @Query(value = "" +
            "SELECT nvl(factor_1, 0.0) factor_1" +
            "      ,nvl(factor_2, 0.0) factor_2" +
            "      ,nvl(factor_3, 0.0) factor_3" +
            "      ,nvl(factor_4, 0.0) factor_4" +
            "      ,nvl(factor_5, 0.0) factor_5" +
            "      ,nvl(factor_6, 0.0) factor_6" +
            "      ,nvl(factor_7, 0.0) factor_7" +
            "      ,nvl(factor_8, 0.0) factor_8" +
            "      ,nvl(factor_9, 0.0) factor_9" +
            "  FROM TB_PREDICT_CHART_DATA" +
            " WHERE 1=1 " +
            "   AND asset_id = :assetId" +
            "   AND predict_div_cd = :predictDivCd" +
            "   AND stage_div_cd = :stageDivCd", nativeQuery=true)
    ArrayList<ArrayList> getPredictData1(@Param("assetId") String assetId, @Param("stageDivCd") String stageDivCd,@Param("predictDivCd") String predictDivCd);
}
