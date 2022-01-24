package com.iljin.apiServer.template.system.settings;

import com.iljin.apiServer.core.security.user.User;
import com.iljin.apiServer.core.security.user.UserRepository;
import com.iljin.apiServer.core.util.Util;
import com.iljin.apiServer.template.asset.AssetDto;
import com.iljin.apiServer.template.system.menu.UserMenu;
import com.iljin.apiServer.template.system.menu.UserMenuRepository;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SettingsServiceImpl implements SettingsService {
    private final Util util;
    private final UserMenuRepository userMenuRepository;
    private final UserDashboardRepository userDashboardRepository;
    private final UserRepository userRepository;
    private final UserDashboardRepositoryCustom userDashboardRepositoryCustom;

    @Override
    public List<AssetDto> getUserSettings(String loginId) {
        List<AssetDto> list = userDashboardRepository.getUserSettings(loginId)
                .stream()
                .map(s -> new AssetDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,(Integer) s[3]
                        ,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
                )).collect(Collectors.toList());

        return list;
    }

    @Override
    public ResponseEntity<String> saveUserSettings(List<SettingsDto> list) {
        User loginUser = util.getLoginUser();
        String compCd = loginUser.getCompCd();
        String loginId = loginUser.getLoginId();

        if (list.size() > 0) {
            userMenuRepository.deleteByCompCdAndUserId(compCd, loginId);
            for (SettingsDto settingsDto : list) {
                UserMenu userMenu = new UserMenu();
                userMenu.setCompCd(settingsDto.getCompCd());
                userMenu.setUserId(settingsDto.getLoginId());
                userMenu.setMenuNo(settingsDto.getMenuNo());
                userMenu.setMenuIconCd(settingsDto.getMenuIconCd());

                userMenuRepository.save(userMenu);
            }
        }
        return new ResponseEntity<>("저장되었습니다.", HttpStatus.OK);
    }

    /*
    @Override
    public List<AssetDto> getUserDefinedAssetList(AssetDto assetDto) {
        String loginId = assetDto.getLoginId();
        String assetId = assetDto.getAssetId();
        String assetName = assetDto.getAssetNm();
        String assetStatCd = assetDto.getAssetStatCd();
        String orderBy = assetDto.getOrderBy();


        List<AssetDto> list = userDashboardRepository.getUserDashboardByLoginIdOrAssetIdOrAssetNmOrAssetStatCd(loginId, assetId, assetName, assetStatCd, orderBy)
                .stream()
                .map(s -> new AssetDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,(Integer) s[3]
                        ,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[5]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[6]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[7]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[8]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[9]).orElse(""))
                )).collect(Collectors.toList());

        return list;
    }
     */

    @Override
    public List<AssetDto> getUserDefinedAssetList(AssetDto assetDto) {

        return userDashboardRepositoryCustom.getUserDefinedAssetList(assetDto);

    }

    @Override
    public ResponseEntity<String> saveUserAssetList(List<UserDashboardDto> list) {
        User loginUser = util.getLoginUser();
        String loginId = loginUser.getLoginId();;

        if (list.size() > 0) {
            userDashboardRepository.deleteByLoginId(loginId);
            for (UserDashboardDto userDashboardDto : list) {
                UserDashboard userDashboard = new UserDashboard();
                userDashboard.setLoginId(userDashboardDto.getLoginId());
                userDashboard.setAssetId(userDashboardDto.getAssetId());
                userDashboard.setAssetOrder(userDashboardDto.getAssetOrder());

                userDashboardRepository.save(userDashboard);
            }
        }
        return new ResponseEntity<>("저장되었습니다.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> saveColorThema(ColorThemaDto colorThemaDto) {
        User loginUser = util.getLoginUser();
        String loginId = loginUser.getLoginId();

        if(!StringUtils.isEmpty(colorThemaDto.getAttribute2())) {
            //attribute2저장
            userRepository.findByLoginId(loginId).ifPresent(user -> {
                user.setAttribute2(colorThemaDto.getAttribute2());
                userRepository.save(user);
            });
        }
        return new ResponseEntity<>("저장되었습니다.", HttpStatus.OK);
    }
}
