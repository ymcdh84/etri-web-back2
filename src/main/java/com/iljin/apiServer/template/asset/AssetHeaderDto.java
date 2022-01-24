package com.iljin.apiServer.template.asset;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class AssetHeaderDto implements Serializable {
    String assetId;
    String assetStatCd;
    String procStatCd;
    String procStatNm;

    /*
    설비리스트 조회
     */
    public AssetHeaderDto(String assetId, String assetStatCd, String procStatCd, String procStatNm){
        this.assetId = assetId;
        this.assetStatCd = assetStatCd;
        this.procStatCd = procStatCd;
        this.procStatNm = procStatNm;
    }
}
