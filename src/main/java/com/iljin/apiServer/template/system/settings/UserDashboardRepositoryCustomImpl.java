package com.iljin.apiServer.template.system.settings;

import com.iljin.apiServer.template.asset.AssetDetailDto;
import com.iljin.apiServer.template.asset.AssetDto;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDashboardRepositoryCustomImpl implements UserDashboardRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AssetDto> getUserDefinedAssetList(AssetDto assetDto) {

        String loginId = assetDto.getLoginId();
        String assetId = assetDto.getAssetId();
        String assetNm = assetDto.getAssetNm();
        String assetStatCd = assetDto.getAssetStatCd();
        String orderBy = assetDto.getOrderBy();

        StringBuilder sb = new StringBuilder();
        sb.append("" +
                "SELECT ba.asset_id " +
                " 	    ,ba.asset_nm " +
                " 	    ,ba.asset_stat_cd " +
                " 	    ,mo.asset_order " +
                "       ,CASE WHEN mo.asset_id = ba.asset_id THEN '1' ELSE '0' END AS regYn" +
                "       ,ah.alarm_id" +
                "       ,ah.alarm_desc" +
                "       ,DATE_FORMAT(ah.alarm_date_time, '%Y-%m-%d %T') AS alarmDate" +
                "       ,ba2.growState" +
                "       ,ba2.detail_nm AS growStateNm "+
                "  FROM (SELECT * FROM tb_bigdata_asset WHERE up_asset_id = '' or up_asset_id is null) ba" +
                "       LEFT JOIN (SELECT * FROM tb_user_dashboard WHERE login_id = :loginId) mo ON mo.asset_id = ba.asset_id" +
                "       LEFT JOIN (SELECT * FROM RND.TB_BIGDATA_ALARM_HISTORY ah" +
                "                  WHERE `ALARM_ID` = (SELECT MAX(`ALARM_ID`) FROM RND.TB_BIGDATA_ALARM_HISTORY WHERE `ASSET_ID` = ah.`ASSET_ID`)) ah" +
                "       ON ah.`ASSET_ID` = mo.`ASSET_ID`" +
                "       LEFT JOIN (SELECT ba1.asset_id, ba1.growState, pr.detail_nm" +
                "                  FROM (SELECT asset_id, CASE WHEN seq_proc_stat_cd = '15' AND grow_proc_stat_cd = '1' THEN '16' ELSE seq_proc_stat_cd END AS growState" +
                "                        FROM TB_BIGDATA_ASSET) ba1" +
                "                  LEFT JOIN (SELECT * FROM RND.TB_CODE_DT WHERE group_cd = 'PROC_STAT_CD') pr ON ba1.growState = pr.detail_cd ) ba2"+
                "       ON ba2.asset_id = mo.asset_id");
        sb.append(" WHERE 1=1 ");
        if(!StringUtils.isEmpty(assetId) || !StringUtils.isEmpty(assetNm)) {//장비아이디,장비명
            sb.append(" 	AND (ba.asset_id LIKE CONCAT('%', :assetId, '%') " +
                      " 	OR ba.asset_nm LIKE CONCAT('%', :assetNm, '%') " +
                      "   )");
        }
        if(!StringUtils.isEmpty(assetStatCd)) {//장비상태
            sb.append(" 	AND ba.asset_stat_cd LIKE CONCAT('%', :assetStatCd, '%') ");
        }
        if(!StringUtils.isEmpty(orderBy)) {//장비상태
            sb.append(" ORDER BY " +
                    "   (case when :orderBy='asset_order' then asset_order END) ASC, " +
                    "   (case when :orderBy='asset_stat_cd' then asset_stat_cd END)ASC, asset_id ASC, " +
                    "   (case when :orderBy='asset_nm' then asset_nm END) ASC");
        }

        Query query = entityManager.createNativeQuery(sb.toString());

        query.setParameter("loginId", loginId);
        if(!StringUtils.isEmpty(assetId)) {
            query.setParameter("assetId", assetId);
        }
        if(!StringUtils.isEmpty(assetNm)) {
            query.setParameter("assetNm", assetNm);
        }
        if(!StringUtils.isEmpty(assetStatCd)) {
            query.setParameter("assetStatCd", assetStatCd);
        }
        if(!StringUtils.isEmpty(orderBy)) {
            query.setParameter("orderBy", orderBy);
        }

        return new JpaResultMapper().list(query, AssetDto.class);
    }
}
