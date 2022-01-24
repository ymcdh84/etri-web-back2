package com.iljin.apiServer.template.system.code;

import com.iljin.apiServer.core.util.Error;
import com.iljin.apiServer.core.util.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/code")
public class CodeController {

    private final CodeService codeService;

    @ExceptionHandler(CodeException.class)
    public ResponseEntity<Error> receiptNotFound(CodeException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 공통콤보 조회
     */
    @GetMapping("/combo")
    public ResponseEntity<List<Pair>> getComboBox(@ModelAttribute CodeDto codeDto) {
        List<Pair> codes = codeService.getComboBox(codeDto);
        return new ResponseEntity<>(codes, HttpStatus.OK);
    }

    /**
     * Subject : 코드상세 조회
     */
    @GetMapping("/detail")
    public ResponseEntity<List<Map>> getCodeDetails(@ModelAttribute CodeDto codeDto) {
        List<Map> codes = codeService.getByCodeDto(codeDto);
        return new ResponseEntity<>(codes, HttpStatus.OK);
    }

    /**
     * Subject : 전체 코드 조회
     */
    @GetMapping("/")
    public ResponseEntity<List<Map>> getCodeAll() {
        List<Map> codes = codeService.getCodeAll();
        return new ResponseEntity<>(codes, HttpStatus.OK);
    }

    /**
     * Subject : 공통코드관리
     * Feature : GroupCode 리스트 조회
     * @param codeDto has two search conditions.
     *                groupCd/groupNm : 그룹코드/명
     *                useYn : 사용유무
     *
     * */
    @PostMapping("/list")
    public ResponseEntity<List<CodeDto>> getGroupCodeList(@RequestBody CodeDto codeDto) {
        List<CodeDto> list = codeService.getGroupCodeList(codeDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /**
     * Subject : 공통코드관리
     * Feature : DetailCode 조회 - 그룹코드DIV 셀 클릭 event
     * @param codeDto is 그룹코드DIV에서 클릭한 라인의 그룹코드
     * */
    @PostMapping("/detail")
    public ResponseEntity<List<CodeDto>> getGroupCodeDetailList(@RequestBody CodeDto codeDto) {
        List<CodeDto> list = codeService.getGroupCodeDetailList(codeDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /**
     * Subject : 공통코드관리
     * Feature : 현재화면 저장
     * @param codeHeaderDetails
     * */
    @PutMapping("/save")
    public ResponseEntity<String> saveCodeLists(@RequestBody CodeHeaderDetails codeHeaderDetails) {
        return codeService.saveCodeLists(codeHeaderDetails);
    }
    /**
     * Subject : 공통코드관리
     * Feature : 그룹코드 영역 행삭제
     * @param groupCd
     * */
    @DeleteMapping("/delete/{groupCd}")
    public ResponseEntity<String> deleteCode(@PathVariable String groupCd) {
        return codeService.deleteCode(groupCd);
    }

}

@Getter
@Setter
class CodeHeaderDetails {
    List<CodeDto> codeHeader;
    List<CodeDto> codeDetail;
}
