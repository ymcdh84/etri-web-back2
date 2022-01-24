package com.iljin.apiServer.template.bigdata.monitoring;

import com.iljin.apiServer.core.util.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/monitoring")
public class BigdataMonitoringController {

    private final BigdataMonitoringService bigdataMonitoringService;

    @ExceptionHandler(BigdataMonitoringException.class)
    public ResponseEntity<Error> receiptNotFound(BigdataMonitoringException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 고장구간 개별 기여도 분석 결과
     * Feature : BigdataSite 리스트 조회
     * @param moDto has six search conditions.
     * siteId : 사이트 아이디
     * assetId : 자산 아이디
     * detectionIndexCd : 탐지지수 구분
     * stageDivCd : 스테이지 구분
     * stSeq : 시작 seq
     * endSeq : 종료 seq
     * */
    @PostMapping("/cont/test")
    public ResponseEntity<List<List<Double>>> getContTestList(@RequestBody BigdataMonitoringDto moDto) {

        List<List<Double>> list = bigdataMonitoringService.getContTestList(moDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 고장구간 기여도 분석 결과
     * Feature : BigdataSite 리스트 조회
     * @param moDto has six search conditions.
     * siteId : 사이트 아이디
     * assetId : 자산 아이디
     * detectionIndexCd : 탐지지수 구분
     * stageDivCd : 스테이지 구분
     * stSeq : 시작 seq
     * endSeq : 종료 seq
     * */
    @PostMapping("/cont")
    public ResponseEntity<List<List<Double>>> getContMeanList(@RequestBody BigdataMonitoringDto moDto) {

        List<List<Double>> list = bigdataMonitoringService.getContMeanList(moDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
