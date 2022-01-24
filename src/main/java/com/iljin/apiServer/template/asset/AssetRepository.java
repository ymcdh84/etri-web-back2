package com.iljin.apiServer.template.asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<AssetHeader,AssetHeaderKey> {

    @Query(value = "" +
            "SELECT ba.asset_id "+
            "      ,ba.asset_stat_cd "+
            "      ,ba.growState "+
            "      ,pr.detail_nm "+
            "FROM (SELECT asset_id"+
            "             ,asset_stat_cd"+
            "             ,CASE" +
            "                   WHEN `SEQ_PROC_STAT_CD`='15' AND `GROW_PROC_STAT_CD`='1' THEN '16'" +
            "                   ELSE `SEQ_PROC_STAT_CD`" +
            "              END AS growState " +
            "      FROM RND.TB_BIGDATA_ASSET WHERE `ASSET_ID`=:assetId) ba "+
            "LEFT JOIN (SELECT * FROM RND.TB_CODE_DT WHERE `GROUP_CD` = 'PROC_STAT_CD') pr ON ba.growState = pr.detail_cd", nativeQuery=true)
    List<Object[]> getAssetHeader(@Param("assetId") String assetId);


    @Query(value = "" +
            " select `LOT_ID` " +
            "       ,`RES_ID` " +
            "       ,`CHAR_DESC` " +
            "       ,`UNIT` " +
            "       ,`VALUE_1` " +
            "       ,`MAT_WEIGHT` " +
            " FROM INTERFACE.vbouleedcyield " +
            " WHERE 1=1 " +
            " AND `RES_ID`= :assetId " +
            " AND `LOT_ID` = (SELECT MAX(`LOT_ID`) FROM INTERFACE.vbouleedcyield WHERE `RES_ID`= :assetId) " +
            " ORDER BY `CHAR_DESC`", nativeQuery=true)
    List<Object[]> getAssetLastInfo(@Param("assetId") String assetId);

}
