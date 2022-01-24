package com.iljin.apiServer.template.asset;

import java.util.List;

public interface AssetRepositoryCustom {
    List<AssetDetailDto> getAssetDetailInfo(String assetId);
}
