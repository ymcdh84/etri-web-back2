package com.iljin.apiServer.template.bigdata.tag;

import com.iljin.apiServer.core.util.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/tag")
public class BigdataAssetTagController {

    private final BigdataAssetTagService bigdataAssetTagService;
    private final AssetTagRepository assetTagRepository;

    @ExceptionHandler(BigdataAssetTagException.class)
    public ResponseEntity<Error> receiptNotFound(BigdataAssetTagException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 태그 조회
     * Feature : BigdataAssetTagDto 설비 태그 조회
     * @param assetTagDto has two search conditions.
     * */
    @PostMapping("/one")
    public ResponseEntity<List<BigdataAssetTagDto>> getAssetTag(@RequestBody BigdataAssetTagDto assetTagDto) {
        List<BigdataAssetTagDto> list = bigdataAssetTagService.getAssetTag(assetTagDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 사이트별 태그 리스트 조회
     * Feature : BigdataAssetTagDto 설비 태그 리스트 조회
     * */
    @PostMapping("/site")
    public ResponseEntity<List<AssetTag>> getAssetTagSiteList(@RequestBody BigdataAssetTagDto assetTagDto) {

        List<AssetTag> list = assetTagRepository.findBySiteId(assetTagDto.getSiteId());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Subject : 태그상세 정보
     * 저장
     * @param bigdataAssetTagDto is BigdataAssetTagDto
     * */
    @PutMapping("/save")
    public ResponseEntity<String> saveAssetTag(@RequestBody BigdataAssetTagDto bigdataAssetTagDto) {
        return bigdataAssetTagService.saveAssetTag(bigdataAssetTagDto);
        //return null;
    }
}