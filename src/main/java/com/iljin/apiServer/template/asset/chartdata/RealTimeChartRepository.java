package com.iljin.apiServer.template.asset.chartdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface RealTimeChartRepository extends JpaRepository<RealTimeChart, RealTimeChartKey > {
    @Query(value = "" +
            "SELECT *" +
            "  FROM tb_real_time_chart_data" +
            " WHERE 1=1 " +
            "   AND lot_no = (SELECT lot_no FROM tb_real_time_chart_data " +
            "                 WHERE create_date_time = (SELECT MAX(create_date_time) FROM tb_real_time_chart_data WHERE asset_id = :assetId LIMIT 1) LIMIT 1) " +
            "   AND asset_id = :assetId" +
            "   AND stage_div_cd = :stageDivCd", nativeQuery=true)
    List<RealTimeChart> getRealTimeData(@Param("assetId") String assetId, @Param("stageDivCd") String stageDivCd);

    @Query(value = "" +
            "SELECT stage_div_cd" +
            "       ,lot_no" +
            "  FROM tb_real_time_chart_data" +
            " WHERE 1=1 " +
            "   AND create_date_time = (SELECT MAX(create_date_time) FROM tb_real_time_chart_data WHERE asset_id = :assetId limit 1)" +
            "   AND asset_id = :assetId LIMIT 1", nativeQuery=true)
    Map<String, Object> getStageLevel(@Param("assetId") String assetId);


    @Query(value = "" +
            "SELECT factor_1" +
            "      ,factor_2" +
            "      ,factor_3" +
            "      ,factor_4" +
            "      ,factor_5" +
            "      ,factor_6" +
            "      ,factor_7" +
            "      ,nvl(factor_8, 0.0) factor_8" +
            "      ,nvl(factor_9, 0.0) factor_9" +
            "  FROM tb_real_time_chart_data" +
            " WHERE 1=1 " +
            "   AND lot_no = :lotNo " +
            "   AND asset_id = :assetId " +
            "   AND stage_div_cd = :stageDivCd", nativeQuery=true)
    ArrayList<ArrayList> getRealTimeData1(@Param("assetId") String assetId, @Param("stageDivCd") String stageDivCd, @Param("lotNo") String lotNo);

}
