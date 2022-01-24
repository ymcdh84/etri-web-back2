package com.iljin.apiServer.template.bigdata.lndata;

import com.iljin.apiServer.core.util.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/lndata")
public class LearningDataController {

    private final LearningDataService learningDataService;


    @ExceptionHandler(LearningDataException.class)
    public ResponseEntity<Error> receiptNotFound(LearningDataException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 학습데이터 관리
     * Feature : LearningLotNoDto 학습데이터 Lot 정보 리스트 조회
     * @param larningLotNoDto has two search conditions.
     *   siteId : 사이트 ID
     *   assetId : 자산 ID
     * */
    @PostMapping("/list")
    public ResponseEntity<List<LearningLotNoDto>> getLearningLotData(@RequestBody LearningLotNoDto larningLotNoDto) {

        List<LearningLotNoDto> list = learningDataService.getLearningLotInfoList(larningLotNoDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @PutMapping("/save")
    public ResponseEntity<String> saveLearningLotData(@RequestBody List<LearningLotNoDto> dataList) {
        return learningDataService.saveLearningLotData(dataList);
    }

    /**
     * pca 차트 데이터 생성 배치 실행
     * @param learningLotNoDto
     * @return
     */
    @PutMapping("/batch/pca")
    public void savePcaBatchData(@RequestBody LearningLotNoDto learningLotNoDto) throws IOException {
        learningDataService.execLearningBatchPython("SITE_0001", "pca");
    }

    /**
     * predict 차트 데이터 생성 배치 실행
     * @param learningLotNoDto
     * @return
     */
    @PutMapping("/batch/predict")
    public void savePredictBatchData(@RequestBody LearningLotNoDto learningLotNoDto) throws IOException  {
        learningDataService.execLearningBatchPython("SITE_0001","predict");
    }
}
