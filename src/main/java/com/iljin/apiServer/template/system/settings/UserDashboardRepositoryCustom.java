package com.iljin.apiServer.template.system.settings;

import com.iljin.apiServer.template.asset.AssetDto;

import java.util.Collection;
import java.util.List;

public interface UserDashboardRepositoryCustom {
    List<AssetDto> getUserDefinedAssetList(AssetDto assetDto);
}
