package com.iljin.apiServer.template.bigdata.topic;

import com.iljin.apiServer.core.util.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/topic")
public class BigdataTopicController {

    private final BigdataTopicService bigdataTopicService;

    @ExceptionHandler(BigdataTopicException.class)
    public ResponseEntity<Error> receiptNotFound(BigdataTopicException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 카프카 토픽 관리
     * Feature : BigdataConnect 리스트 조회
     * @param topicDto has two search conditions.
     *                siteNm : 사이트 명
     * */
    @PostMapping("/list")
    public ResponseEntity<List<BigdataTopicDto>> getTopicList(@RequestBody BigdataTopicDto topicDto) {
        List<BigdataTopicDto> list = bigdataTopicService.getTopicList(topicDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 카프카 토픽 관리
     * 저장
     * @param topicList is BigdataConnectDto List.(Data on grid)
     * */
    @PutMapping("/save")
    public ResponseEntity<String> saveTopicList(@RequestBody List<BigdataTopicDto> topicList) {
        return bigdataTopicService.saveTopic(topicList);
    }

    /**
     * Subject : 카프카 토픽 행삭제
     *
     * @param
     */
    @DeleteMapping("/")
    public ResponseEntity<String> deleteTopic(@RequestParam String siteId, @RequestParam String connectId, @RequestParam String topicId) {
        return bigdataTopicService.deleteTopic(siteId, connectId, topicId);
    }

    /**
     * Subject : 카프카 토픽 관리
     * 토픽 작동 (시작/중지)
     * @param topicDto has two search conditions.
     * */
    @PutMapping("/operate")
    public ResponseEntity<String> doOperateTopic(@RequestBody BigdataTopicDto topicDto) {
        return bigdataTopicService.doOperateTopic(topicDto);
    }
}