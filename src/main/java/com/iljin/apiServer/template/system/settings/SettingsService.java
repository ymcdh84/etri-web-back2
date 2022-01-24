package com.iljin.apiServer.template.system.settings;

import com.iljin.apiServer.template.asset.AssetDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SettingsService {
    List<AssetDto> getUserSettings(String loginId);

    @Modifying
    @Transactional
    ResponseEntity<String> saveUserSettings(List<SettingsDto> settingsDtos);

    List<AssetDto> getUserDefinedAssetList(AssetDto assetDto);

    @Modifying
    @Transactional
    ResponseEntity<String> saveUserAssetList(List<UserDashboardDto> userDashboardDto);

    @Modifying
    @Transactional
    ResponseEntity<String> saveColorThema(ColorThemaDto colorThemaDto);
}
