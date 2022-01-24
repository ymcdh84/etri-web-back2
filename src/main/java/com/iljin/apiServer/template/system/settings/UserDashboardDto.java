package com.iljin.apiServer.template.system.settings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDashboardDto {
    String siteId;
    String assetId;
    String assetNm;
    String assetGroup;
    String assetDesc;
    String upAssetId;
    String assetStatCd;
    String loginId;
    Integer assetOrder;
    /*
    public UserDashboardDto(String loginId, String assetId, Integer assetOrder) {
        this.loginId = loginId;
        this.assetId = assetId;
        this.assetOrder = assetOrder;
    }
    */
}
