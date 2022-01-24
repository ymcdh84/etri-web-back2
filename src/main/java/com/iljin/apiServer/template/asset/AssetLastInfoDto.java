package com.iljin.apiServer.template.asset;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class AssetLastInfoDto implements Serializable {

    String lotId;
    String resId;
    String charDesc;
    String unit;
    String value1;
    String matWeight;

    public AssetLastInfoDto(String lotId, String resId, String charDesc, String unit, String value1, String matWeight){
        this.lotId = lotId;
        this.resId = resId;
        this.charDesc = charDesc;
        this.unit = unit;
        this.value1 = value1;
        this.matWeight = matWeight;
    }
}
