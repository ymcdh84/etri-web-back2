package com.iljin.apiServer.template.bigdata.asset;

import com.iljin.apiServer.core.util.Error;

import com.iljin.apiServer.template.bigdata.mongodb.DocumentDto;
import com.iljin.apiServer.template.bigdata.mongodb.MongoDbService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/asset")
public class BigdataAssetController {

    private final BigdataAssetService bigdataAssetService;

    private final MongoDbService mongoDbService;

    @ExceptionHandler(BigdataAssetException.class)
    public ResponseEntity<Error> receiptNotFound(BigdataAssetException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 자산정보
     * Feature : BigdataAsset 자산 리스트 조회
     * @param assetDto has two search conditions.
     * */
    @PostMapping("/list")
    public ResponseEntity<List<BigdataAssetDto>> getAssetList(@RequestBody BigdataAssetDto assetDto) {
        List<BigdataAssetDto> list = bigdataAssetService.getAssetWithImage(assetDto);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 태그 현황 정보
     * Feature : BigdataAsset 설비 태그 리스트 조회
     * @param assetDto has two search conditions.
     * */
    @PostMapping("/tag/list")
    public ResponseEntity<List<BigdataAssetDto>> getAssetTagList(@RequestBody BigdataAssetDto assetDto) {
        List<BigdataAssetDto> list = bigdataAssetService.getAssetTagList(assetDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 알람 이력 조회
     * Feature : BigdataAsset 알람 이력 조회
     * @param assetDto has two search conditions.
     * */
    @PostMapping("/alarm/list")
    public ResponseEntity<List<BigdataAssetDto>> getAlarmList(@RequestBody BigdataAssetDto assetDto) {
        List<BigdataAssetDto> list = bigdataAssetService.getAlarmList(assetDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 알람 이력 조회
     * Feature : BigdataAsset 알람 이력 조회
     * @param assetDto has two search conditions.
     * */
    @PostMapping("/alarm/list/monitoring")
    public ResponseEntity<List<BigdataAssetDto>> getAlarmMonitoringList(@RequestBody BigdataAssetDto assetDto) {
        List<BigdataAssetDto> list = bigdataAssetService.getAlarmMonitoringList(assetDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 알람 확인 처리
     * 저장
     * @param dataList is BigdataAssetDto List.(Data on grid)
     * */
    @PutMapping("/alarm/confirm")
    public ResponseEntity<String> saveAlarmConfirm(@RequestBody List<BigdataAssetDto> dataList) {
        return bigdataAssetService.saveAlarmConfirm(dataList);
    }

    /**
     * Subject : 알람 삭제
     *
     * @param
     */
    @DeleteMapping("/alarm")
    public ResponseEntity<String> deleteAlarm(@RequestParam String alarmId) {
        return bigdataAssetService.deleteAlarm(alarmId);
    }

    /**
     * Subject : 자산관리
     * Feature : BigdataAsset 설비 태그 트리 리스트 조회
     * @param assetDto has two search conditions.
     * */
    @PostMapping("/tree/list")
    public ResponseEntity<List<BigdataAssetDto>> getAssetTreeList(@RequestBody BigdataAssetDto assetDto) {
        List<BigdataAssetDto> list = bigdataAssetService.getAssetTreeList(assetDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 자산 상세 정보
     * 저장
     * @param bigdataAssetDto is BigdataAssetDto
     * */
    @PutMapping("/save/new")
    public ResponseEntity<String> saveAssetNew(@RequestBody BigdataAssetDto bigdataAssetDto) {
        return bigdataAssetService.saveAssetNew(bigdataAssetDto);
    }

    /**
     * Subject : 자산 상세 정보
     * 저장
     * @param bigdataAssetDto is BigdataAssetDto
     * */
    @PutMapping("/save")
    public ResponseEntity<String> saveAsset(@RequestBody BigdataAssetDto bigdataAssetDto) {
        return bigdataAssetService.saveAsset(bigdataAssetDto);
    }

    /**
     * Subject : 자산 삭제
     *
     * @param
     */
    @DeleteMapping("/{siteId}/{assetId}")
    public ResponseEntity<String> deleteAsset(@PathVariable String siteId, @PathVariable String assetId) {
        return bigdataAssetService.deleteAsset(siteId, assetId);
    }

    /**
     *  '자산관리' 태그 맵핑전 데이터 체크
     * */
    @PutMapping("/chk/tag")
    public void getAssetTagMappingStatus(@RequestBody BigdataAssetDto bigdataAssetDto) {
        bigdataAssetService.getAssetTagMappingStatus(bigdataAssetDto);
    }

    /**
     *  '자산관리' 트리뷰에서 태그가 맵핑될 자산 id
     * */
    @PutMapping("/reg/tag")
    public ResponseEntity<String> saveRegAssetTag(@RequestBody BigdataAssetDto bigdataAssetDto) {
        return bigdataAssetService.saveRegAssetTag(bigdataAssetDto);
    }

    /**
     *  '자산관리' 자산 모델링
     * */
    @PutMapping("/modeling")
    public void saveAssetModeling(@RequestBody BigdataAssetDto bigdataAssetDto) {
        bigdataAssetService.callSpAssetModeling(bigdataAssetDto.getSiteId(), bigdataAssetDto.getAssetId());
    }

    /**
     *  '자산관리' 컬렉션 필드 리스트 조회
     * */
    @PutMapping("/mongodb")
    public List<DocumentDto> getMongoDbCollectionInfo(@RequestBody BigdataAssetDto bigdataAssetDto) {
        return mongoDbService.getMongoDbCollectionInfo(bigdataAssetDto);
    }

    /**
     *  '자산관리' 컬렉션 리스트 조회
     * */
    @GetMapping("/mongodb/collections")
    public ResponseEntity<List<DocumentDto>> getMongoDbCollections() {
        List<DocumentDto> list = mongoDbService.getMongoDbCollections();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    /**
     *  '자산관리' 자산 태그 복사
     * */
    @PutMapping("/copy/tag/{siteId}/{assetId}/{copyAssetId}")
    public ResponseEntity<String> saveCopyAssetTag(@PathVariable String siteId,
                                                      @PathVariable String assetId,
                                                      @PathVariable String copyAssetId) {
        return bigdataAssetService.saveCopyAssetTag(siteId, assetId, copyAssetId);
    }
}