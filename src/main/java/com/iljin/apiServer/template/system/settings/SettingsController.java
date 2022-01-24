package com.iljin.apiServer.template.system.settings;

import com.iljin.apiServer.core.util.Error;
import com.iljin.apiServer.template.asset.AssetDto;
import com.iljin.apiServer.template.system.authority.AuthorityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/settings")
public class SettingsController {
    private final SettingsService settingsService;

    @ExceptionHandler(AuthorityException.class)
    public ResponseEntity<Error> SettingsNotFound(SettingsException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * SE-ES-001 AssetListSettings(custom MainPage menu)
     * Desc. 장비설정 조회
     * @param loginId is 현재 로그인 사용자 아이디
     * */

    @GetMapping("/{loginId}")
    public ResponseEntity<List<AssetDto>> getUserSettings(@PathVariable String loginId) {
        List<AssetDto> list = settingsService.getUserSettings(loginId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /**
     * EA-08-03 Settings(custom MainPage menu)
     * Desc. 저장
     * @param dashboardDto is jsonArray.
     * Desc. param needs compCd, loginId, menuNo, menuIconCd for each row.
     * */
    @PutMapping("/")
    public ResponseEntity<String> saveUserSettings(@RequestBody List<SettingsDto> dashboardDto) {
        return settingsService.saveUserSettings(dashboardDto);
    }
    /**
     * MA-MP-001 MainPage
     * Desc.메인화면(설비리스트 조회)
    * */
    @PostMapping("/asset-list/search")
    public ResponseEntity<List<AssetDto>> getAssetList(@RequestBody AssetDto assetDto) {
        List<AssetDto> list = settingsService.getUserDefinedAssetList(assetDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /**
     * MA-MP-001 MainPage
     * Desc. 설비리스트 저장
     * */
    @PutMapping("/asset-list/save")
    public ResponseEntity<String> saveAssetList(@RequestBody List<UserDashboardDto> userDashboardDto) {
        return settingsService.saveUserAssetList(userDashboardDto);
    }

    /**
     * MA-MP-001 MainPage
     * Desc. 컬러테마 저장
     * */
    @PutMapping("/thema")
    public ResponseEntity<String> saveColorThema(@RequestBody ColorThemaDto colorThemaDto) {
        return settingsService.saveColorThema(colorThemaDto);
    }

}
