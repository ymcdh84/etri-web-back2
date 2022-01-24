package com.iljin.apiServer.template.bigdata.connect;

import com.iljin.apiServer.core.util.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/connect")
public class BigdataConncectController {

    private final BigdataConnectService bigdataConnectService;

    @ExceptionHandler(BigdataConnectException.class)
    public ResponseEntity<Error> receiptNotFound(BigdataConnectException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 연결정보 관리
     * Feature : BigdataConnect 리스트 조회
     * @param connectDto has two search conditions.
     *                siteNm : 사이트 명
     * */
    @PostMapping("/list")
    public ResponseEntity<List<BigdataConnectDto>> getConncetList(@RequestBody BigdataConnectDto connectDto) {
        List<BigdataConnectDto> list = bigdataConnectService.getConnectList(connectDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 연결정보 관리
     * 저장
     * @param conncetList is BigdataConnectDto List.(Data on grid)
     * */
    @PutMapping("/save")
    public ResponseEntity<String> saveConnectList(@RequestBody List<BigdataConnectDto> conncetList) {
        return bigdataConnectService.saveConnect(conncetList);
    }

    /**
     * Subject : 연결 정보 행삭제
     *
     * @param
     */
    @DeleteMapping("/")
    public ResponseEntity<String> deleteConnect(@RequestParam String siteId, @RequestParam String connectId) {
        return bigdataConnectService.deleteConnect(siteId, connectId);
    }

    /**
     * Subject : 토픽관리 관리
     * Feature : BigdataConnect 사이트 연결 리스트 조회
     * @param connectDto has two search conditions.
     *                siteNm : 사이트 명
     * */
    @PostMapping("/site")
    public ResponseEntity<List<BigdataConnectDto>> getSiteConncetList(@RequestBody BigdataConnectDto connectDto) {
        List<BigdataConnectDto> list = bigdataConnectService.getSiteConnectList(connectDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}