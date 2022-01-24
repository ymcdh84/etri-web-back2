package com.iljin.apiServer.template.asset;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AssetRepositoryCustomImpl implements AssetRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AssetDetailDto> getAssetDetailInfo(String assetId) {
        StringBuilder sb = new StringBuilder();
        sb.append("" +
                "select year" +
                "      ,month" +
                "      ,day" +
                "      ,hour" +
                "      ,min" +
                "      ,lot_no" +
                "      ,main_heater_sv" +
                "      ,main_heater_pv" +
                "      ,main_heater_out" +
                "      ,bottom_heater_sv" +
                "      ,bottom_heater_pv" +
                "      ,bottom_heater_out" +
                "      ,ROUND(temp, 2) as temp" +
                "      ,ROUND(weight, 2) as weight"+
                " From ");
        sb.append(assetId);
        sb.append(
                " where lot_no = (select lot_no from ");
        sb.append(assetId);
        sb.append(
                " where 1=1" +
                " and crt_dtm = (select max(crt_dtm) from ");
        sb.append(assetId);
        sb.append(" limit 1) limit 1) ");

        Query query = entityManager.createNativeQuery(sb.toString());

        return new JpaResultMapper().list(query, AssetDetailDto.class);
    }
}
