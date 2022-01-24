package com.iljin.apiServer.template.asset;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class AssetDto implements Serializable {
    String siteId;
    String assetId;
    String assetNm;
    String assetGroup;
    String assetDesc;
    String upAssetId;
    String assetStatCd;
    String loginId;
    Integer assetOrder;
    String orderBy;
    String regYn;
    String alarmId;
    String alarmDesc;
    String alarmDate;
    String growState;
    String growStateNm;

    /*
    설비리스트 조회
     */
    public AssetDto(String assetId, String assetNm, String assetStatCd, Integer assetOrder,String regYn, String alarmId, String alarmDesc, String alarmDate, String growState, String growStateNm){
        this.assetId = assetId;
        this.assetNm = assetNm;
        this.assetStatCd = assetStatCd;
        this.assetOrder = assetOrder;
        this.regYn = regYn;
        this.alarmId = alarmId;
        this.alarmDesc = alarmDesc;
        this.alarmDate = alarmDate;
        this.growState = growState;
        this.growStateNm = growStateNm;
    }

    /*
   설비리스트 저장
    */
    public AssetDto(String assetId, String assetNm, String assetStatCd, Integer assetOrder,String regYn){
        this.assetId = assetId;
        this.assetNm = assetNm;
        this.assetStatCd = assetStatCd;
        this.assetOrder = assetOrder;
        this.regYn = regYn;
    }

}
