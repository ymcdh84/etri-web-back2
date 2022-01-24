package com.iljin.apiServer.template.asset.chartdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SPEChartRepository extends JpaRepository<SPEChart, SPEChartKey > {
    /*
    @Query(value = "" +
            "SELECT nvl(spe_test, '') AS spe_test "+
            "       ,nvl(the_spe, '') AS the_spe "+
            "FROM"+
            "       (SELECT * FROM tb_pca_spe_data" +
            "        WHERE 1=1 " +
            "        AND asset_id = :assetId" +
            "        AND stage_div_cd = :stageDivCd) th "+
            "LEFT JOIN (SELECT * FROM RND.tb_pca_spe_real_data "+
            "           WHERE 1=1" +
            "           AND lot_no = (SELECT MAX(lot_no) FROM rnd.tb_pca_spe_real_data WHERE asset_id = :assetId)" +
            "           AND stage_div_cd = :stageDivCd "+
            "           AND asset_id = :assetId) spe "+
            "ON (th.site_id = spe.site_id AND th.asset_id = spe.asset_id AND th.stage_div_cd = spe.stage_div_cd AND th.seq = spe.seq)", nativeQuery=true)
    ArrayList<ArrayList> getSpeData(@Param("assetId") String assetId, @Param("stageDivCd") String stageDivCd);

     */

    @Query(value = "" +
            "SELECT val "+
            "FROM tb_pca_spe_data " +
            "WHERE 1=1 " +
            "AND asset_id = :assetId "  +
            "AND detection_index_cd = :detectionIndexCd "  +
            "AND stage_div_cd = :stageDivCd ", nativeQuery=true)
    ArrayList<ArrayList> getSpeData(@Param("assetId") String assetId, @Param("detectionIndexCd") String detectionIndexCd, @Param("stageDivCd") String stageDivCd);


    @Query(value = "" +
            "SELECT spe_test " +
            "FROM RND.tb_pca_spe_real_data "+
            "WHERE 1=1 " +
            "AND lot_no = :lotNo " +
            "AND detection_index_cd = :detectionIndexCd " +
            "AND stage_div_cd = :stageDivCd "+
            "AND asset_id = :assetId ", nativeQuery=true)
    ArrayList<ArrayList> getSpeRealData(@Param("assetId") String assetId, @Param("detectionIndexCd") String detectionIndexCd, @Param("stageDivCd") String stageDivCd, @Param("lotNo") String lotNo);


    @Query(value = "" +
            "SELECT FACTOR_1 " +
            "      ,FACTOR_2 " +
            "      ,FACTOR_3 " +
            "      ,FACTOR_4 " +
            "      ,FACTOR_5 " +
            "FROM RND.tb_heat_map_real_data "+
            "WHERE 1=1 " +
            "AND detection_index_cd = :detectionIndexCd " +
            "AND stage_div_cd = :stageDivCd "+
            "AND asset_id = :assetId " +
            "AND lot_no = :lotNo"
            , nativeQuery=true)
    ArrayList<ArrayList> getHeatMapData(@Param("assetId") String assetId, @Param("detectionIndexCd") String detectionIndexCd, @Param("stageDivCd") String stageDivCd, @Param("lotNo") String lotNo);
}
