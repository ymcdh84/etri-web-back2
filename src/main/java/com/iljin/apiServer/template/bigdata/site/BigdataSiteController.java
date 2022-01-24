package com.iljin.apiServer.template.bigdata.site;

import com.iljin.apiServer.core.util.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/site")
public class BigdataSiteController {

    private final BigdataSiteService bigdataSiteService;

    @ExceptionHandler(BigdataSiteException.class)
    public ResponseEntity<Error> receiptNotFound(BigdataSiteException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 사이트 관리
     * Feature : BigdataSite 리스트 조회
     * @param siteDto has two search conditions.
     *                siteNm : 사이트 명
     * */
    @PostMapping("/list")
    public ResponseEntity<List<BigdataSiteDto>> getSiteList(@RequestBody BigdataSiteDto siteDto) {
        List<BigdataSiteDto> list = bigdataSiteService.getSiteList(siteDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 사이트 관리
     * 저장
     * @param siteList is BigdataSiteDto List.(Data on grid)
     * */
    @PutMapping("/save")
    public ResponseEntity<String> saveSiteList(@RequestBody List<BigdataSiteDto> siteList) {
        return bigdataSiteService.saveSite(siteList);
        //return null;
    }

    /**
     * Subject : 사이트 삭제
     *
     * @param
     */
    @DeleteMapping("/")
    public ResponseEntity<String> deleteSite(@RequestParam String siteId) {
        return bigdataSiteService.deleteSite(siteId);
    }
}
