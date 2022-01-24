package com.iljin.apiServer.template.system.settings;

import com.iljin.apiServer.template.asset.AssetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDashboardRepository extends JpaRepository<UserDashboard, UserDashboardKey> {
    @Query(value="" +
            "SELECT ba.asset_id " +
            " 	    ,ba.asset_nm " +
            " 	    ,ba.asset_stat_cd " +
            " 	    ,mo.asset_order " +
            "       ,CASE WHEN mo.asset_id = ba.asset_id THEN '1' ELSE '0' END AS regYn" +
            "       ,ah.alarm_id" +
            "       ,ah.alarm_desc" +
            "       ,DATE_FORMAT(ah.alarm_date_time, '%Y-%m-%d %T') AS alarmDate" +
            "       ,ba2.growState" +
            "       ,ba2.detail_nm"+
            "  FROM (SELECT * FROM tb_bigdata_asset WHERE up_asset_id = '' or up_asset_id is null) ba" +
            "       LEFT JOIN (SELECT * FROM tb_user_dashboard WHERE login_id = :loginId) mo ON mo.asset_id = ba.asset_id" +
            "       LEFT JOIN (SELECT * FROM RND.TB_BIGDATA_ALARM_HISTORY ah" +
            "                  WHERE `ALARM_ID` = (SELECT MAX(`ALARM_ID`) FROM RND.TB_BIGDATA_ALARM_HISTORY WHERE `ASSET_ID` = ah.`ASSET_ID`)) ah" +
            "       ON ah.`ASSET_ID` = mo.`ASSET_ID`" +
            "       LEFT JOIN (SELECT ba1.asset_id, ba1.growState, pr.detail_nm" +
            "                  FROM (SELECT asset_id, CASE WHEN seq_proc_stat_cd = '15' AND grow_proc_stat_cd = '1' THEN '16' ELSE seq_proc_stat_cd END AS growState" +
            "                        FROM TB_BIGDATA_ASSET) ba1" +
            "                  LEFT JOIN (SELECT * FROM RND.TB_CODE_DT WHERE group_cd = 'PROC_STAT_CD') pr ON ba1.growState = pr.detail_cd ) ba2"+
            "       ON ba2.asset_id = mo.asset_id"+
            " WHERE 1=1 " +
            " 	AND (ba.asset_id LIKE CONCAT('%', :assetId, '%') " +
            " 	OR ba.asset_nm LIKE CONCAT('%', :assetNm, '%') " +
            "   )"+
            " 	AND ba.asset_stat_cd LIKE CONCAT('%', :assetStatCd, '%') " +
            " ORDER BY "+
            "   (case when :orderBy='asset_order' then asset_order END) ASC, " +
            "   (case when :orderBy='asset_stat_cd' then asset_stat_cd END)ASC, asset_id ASC, "+
            "   (case when :orderBy='asset_nm' then asset_nm END) ASC"
            , nativeQuery=true)
    List<Object[]> getUserDashboardByLoginIdOrAssetIdOrAssetNmOrAssetStatCd(
            @Param("loginId") String loginId,
            @Param("assetId") String assetId,
            @Param("assetNm") String assetNm,
            @Param("assetStatCd") String assetStatCd,
            @Param("orderBy") String orderBy);

    void deleteByLoginId(String loginId);

    @Query(value = "" +
            "SELECT ba.asset_id"+
            "       ,ba.asset_nm"+
            "       ,ba.asset_stat_cd" +
            "       ,ud.asset_order " +
            "       ,CASE WHEN ud.asset_id = ba.asset_id THEN '1' ELSE '0' END AS regYn" +
            "   FROM (SELECT * FROM tb_bigdata_asset WHERE up_asset_id = '' or up_asset_id is null) ba" +
            "        LEFT JOIN (SELECT * FROM tb_user_dashboard WHERE login_id = :loginId ) ud ON ud.asset_id = ba.asset_id" +
            "   ORDER BY ba.asset_nm" , nativeQuery=true)
    List<Object[]> getUserSettings(@Param("loginId") String loginId);
}
