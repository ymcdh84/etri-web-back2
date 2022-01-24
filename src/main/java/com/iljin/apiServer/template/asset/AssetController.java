package com.iljin.apiServer.template.asset;

import com.iljin.apiServer.template.asset.chartdata.PredictChart;
import com.iljin.apiServer.template.asset.chartdata.RealTimeChart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asset")
public class AssetController {
    private final AssetService assetService;

    /**
     * ED-DI-001 Equipment Details (Equipment Details Information)
     * Desc. 장비정보(장비상태)
     * @param assetId is 장비아이디디
     **/

    @GetMapping("/{assetId}")
    public ResponseEntity<List<AssetHeaderDto>> getAssetHeader(@PathVariable(required = true) String assetId) {
        List<AssetHeaderDto> list = assetService.getAssetHeader(assetId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /**
     * ED-DI-002 Equipment Details (Equipment Details Information)
     * Desc. 장비상세정보
     * @param assetId is 장비아이디
     **/
    @GetMapping("/detail/{assetId}")
    public ResponseEntity<List<AssetDetailDto>>getAssetDetailInfo(@PathVariable(required = true) String assetId){
        List<AssetDetailDto> list = assetService.getAssetDetailInfo(assetId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /**
     * ED-DI-002 Equipment Details (Equipment Details Information)
     * Desc. 장비상세정보(lastInfo)
     * @param assetId is 장비아이디
     **/
    @GetMapping("/lastInfo/{assetId}")
    public ResponseEntity<List<AssetLastInfoDto>>getAssetLastInfo(@PathVariable(required = true) String assetId){
        List<AssetLastInfoDto> list = assetService.getAssetLastInfo(assetId);
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    /**
     * ED-MC-001 Equipment Details (AI Module chart)
     * Desc. 장비상세정보
     * @param assetId is 장비아이디
     * @param stageDivCd is 장비흐름 stage로 구분
     //* @param predictDivCd is P(예측치),H(상한치),L(하한치)
     **/
    @GetMapping("/predicted")
    public List<PredictChart>getPredictData(@RequestParam String assetId, @RequestParam String stageDivCd, @RequestParam String predictDivCd){
        return assetService.getPredictData(assetId,stageDivCd,predictDivCd);
    }

    @GetMapping("/predicted1")
    public ResponseEntity<Map<String, Object>>getPredictData1(@RequestParam String assetId, @RequestParam String stageDivCd, @RequestParam String predictDivCd ){
        Map<String, Object> map = assetService.getPredictData1(assetId,stageDivCd,predictDivCd);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * ED-MC-001 Equipment Details (AI Module chart)
     * Desc. 장비상세정보 (실시간 데이터)
     * @param assetId is 장비아이디
     * @param stageDivCd is 장비흐름 stage로 구분
     **/
    @GetMapping("/realTime")
    public List<RealTimeChart>getRealTimeData(@RequestParam String assetId, @RequestParam String stageDivCd ){
        return assetService.getRealTimeData(assetId,stageDivCd);
    }

    @GetMapping("/realTime1")
    public ResponseEntity<Map<String, Object>>getRealTimeData1(@RequestParam String assetId, @RequestParam String stageDivCd, @RequestParam String lotNo ){
        Map<String, Object> map = assetService.getRealTimeData1(assetId,stageDivCd,lotNo);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * ED-MC-001 Equipment Details (AI Module chart)
     * Desc. stage 단계 체크
     * @param assetId is 장비아이디
     **/
    @GetMapping("/stageLevel")
    public Map<String, Object> getStageLevel(@RequestParam String assetId){
        return assetService.getStageLevel(assetId);
    }

    /**
     * ED-MC-001 Equipment Details (PCA chart)
     * Desc. 주성분분석(PCA) Chart Data
     * @param assetId is 장비아이디
     * @param stageDivCd is 장비흐름 stage로 구분
     **/
    @GetMapping("/pcaData")
    public ResponseEntity<Map<String, Object>>getPcaData(@RequestParam String assetId, @RequestParam String stageDivCd){
        Map<String, Object> map = assetService.getPcaData(assetId,stageDivCd);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * ED-MC-001 Equipment Details (PCA chart)
     * Desc. 주성분분석(PCA) Chart Data - spe
     * @param assetId is 장비아이디
     * @param stageDivCd is 장비흐름 stage로 구분
     **/
    @GetMapping("/speData")
    public ResponseEntity<Map<String, Object>>getSpeData(@RequestParam String assetId, @RequestParam String detectionIndexCd, @RequestParam String stageDivCd){
        Map<String, Object> map = assetService.getSpeData(assetId, detectionIndexCd, stageDivCd);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * ED-MC-001 Equipment Details (PCA chart)
     * Desc. 주성분분석(PCA) Chart Data - speReal
     * @param assetId is 장비아이디
     * @param stageDivCd is 장비흐름 stage로 구분
     * @param detectionIndexCd is SPE/T2로 구분
     * @param lotNo is 마지막 lotMo
     **/
    @GetMapping("/speRealData")
    public ResponseEntity<Map<String, Object>>getSpeRealData(@RequestParam String assetId, @RequestParam String detectionIndexCd, @RequestParam String stageDivCd, @RequestParam String lotNo){
        Map<String, Object> map = assetService.getSpeRealData(assetId, detectionIndexCd, stageDivCd, lotNo);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * ED-MC-001 Equipment Details (PCA chart)
     * Desc. (실시간) 기여도 Chart Data - HeatMap
     * @param assetId is 장비아이디
     * @param stageDivCd is 장비흐름 stage로 구분
     * @param detectionIndexCd is SPE/T2로 구분
     * @param lotNo is 마지막 lotMo
     **/
    @GetMapping("/heatMapData")
    public ResponseEntity<Map<String, Object>>getHeatMapData(@RequestParam String assetId, @RequestParam String detectionIndexCd, @RequestParam String stageDivCd, @RequestParam String lotNo){
        Map<String, Object> map = assetService.getHeatMapData(assetId, detectionIndexCd, stageDivCd, lotNo);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * ED-MC-001 Equipment Details (PCA chart -GrahpPop)
     * Desc. (오프라인) 모델 학습 탐지지수의 문턱값 설정 Chart Data - histogram
     * @param assetId is 장비아이디
     * @param stageDivCd is 장비흐름 stage로 구분
     * @param detectionIndexCd is SPE/T2로 구분
     **/
    @GetMapping("/histogramData")
    public ResponseEntity<Map<String, Object>>getHistogramData(@RequestParam String assetId, @RequestParam String detectionIndexCd, @RequestParam String stageDivCd){
        Map<String, Object> map = assetService.getHistogramData(assetId, detectionIndexCd, stageDivCd);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
