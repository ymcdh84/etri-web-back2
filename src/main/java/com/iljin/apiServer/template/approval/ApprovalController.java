package com.iljin.apiServer.template.approval;
import com.iljin.apiServer.template.approval.dlgt.ApprovalDelegateDto;
import com.iljin.apiServer.template.approval.rule.ApprovalRule;
import com.iljin.apiServer.template.approval.rule.ApprovalRuleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.iljin.apiServer.core.util.Error;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/appr")
public class ApprovalController {

    private final ApprovalService approvalService;

    @ExceptionHandler(ApprovalException.class)
    public ResponseEntity<Error> approvalNotFound(ApprovalException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * subject : 결재할 문서 조회
     * @param approvalHeaderDto has search conditions
     *                          delegateChk : 위임체크
     *                          docTypeCd : 문서유형
     *                          searchDtmFr : 기간
     *                          searchDtmTo
     *                          docTitleNm : 문서명
     *                          draftUserId : 기안자
     * */
    @PostMapping("/todo/list")
    public ResponseEntity<List<ApprovalHeaderDto>> getApprTodoList(@RequestBody ApprovalHeaderDto approvalHeaderDto) {
        List<ApprovalHeaderDto> apprTodos = approvalService.getApprTodoList(approvalHeaderDto);
        return new ResponseEntity<>(apprTodos, HttpStatus.OK);
    }
    /**
     * subject : 결재한 문서
     * @param approvalHeaderDto has search conditions
     *                          docTypeCd : 문서유형
     *                          searchDtmFr : 기간
     *                          searchDtmTo
     *                          docTitleNm : 문서명
     *                          draftUserId : 기안자
     *                          docStatCd : 문서상태
     * */
    @PostMapping("/done/list")
    public ResponseEntity<List<ApprovalHeaderDto>> getApprDoneList(@RequestBody ApprovalHeaderDto approvalHeaderDto) {
        List<ApprovalHeaderDto> apprDones = approvalService.getApprDoneList(approvalHeaderDto);
        return new ResponseEntity<>(apprDones, HttpStatus.OK);
    }
    /**
     * subject : 상신한 문서 조회
     * @param approvalHeaderDto has search conditions
     *                          docTypeCd : 문서유형
     *                          searchDtmFr : 기간
     *                          searchDtmTo
     *                          docTitleNm : 문서명
     *                          draftUserId : 기안자
     *                          docStatCd : 문서상태
     * */
    @PostMapping("/req/list")
    public ResponseEntity<List<ApprovalHeaderDto>> getApprReqList(@RequestBody ApprovalHeaderDto approvalHeaderDto) {
        List<ApprovalHeaderDto> apprReqs = approvalService.getApprReqList(approvalHeaderDto);
        return new ResponseEntity<>(apprReqs, HttpStatus.OK);
    }
    /**
     * subject : 결재진행 중인 문서
     * Desc. 로그인한 사용자가 상신한, 결재한 전자결재 건.
     * @param approvalHeaderDto
     * */
    @PostMapping("/ing/list")
    public ResponseEntity<List<ApprovalHeaderDto>> getApprIngList(@RequestBody ApprovalHeaderDto approvalHeaderDto) {
        List<ApprovalHeaderDto> apprIngs = approvalService.getApprIngList(approvalHeaderDto);

        return new ResponseEntity<>(apprIngs, HttpStatus.OK);
    }
    /**
     * subject : 결재완료 문서
     * Desc. 로그인한 사용자가 연관된 문서 중 결재완료된 전자결재 건.
     * @param approvalHeaderDto
     * */
    @PostMapping("/apr/list")
    public ResponseEntity<List<ApprovalHeaderDto>> getApprAprList(@RequestBody ApprovalHeaderDto approvalHeaderDto) {
        List<ApprovalHeaderDto> apprAprs = approvalService.getApprAprList(approvalHeaderDto);

        return new ResponseEntity<>(apprAprs, HttpStatus.OK);
    }
    /**
     * subject : 반려 문서
     * Desc. 로그인한 사용자가 연관된 문서 중 반려된 전자결재 건.
     * @param approvalHeaderDto
     * */
    @PostMapping("/rej/list")
    public ResponseEntity<List<ApprovalHeaderDto>> getApprRejList(@RequestBody ApprovalHeaderDto approvalHeaderDto) {
        List<ApprovalHeaderDto> apprRejs = approvalService.getApprRejList(approvalHeaderDto);

        return new ResponseEntity<>(apprRejs, HttpStatus.OK);
    }


    /**
     * subject : 결재 상세
     * 결재 상세 내용
     * @param docNo is "전자결재 문서번호"
     * */
    @GetMapping("/detail/{docNo}")
    public ResponseEntity<Map<String, Object>> getApproval(@PathVariable String docNo) {

        Map<String, Object> apprDetails = approvalService.getApproval(docNo);
        if(apprDetails.size() > 0) {
            return new ResponseEntity<>(apprDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * subject : 상신취소
     * @param docNo is "전자결재 문서번호"
     * */
    @GetMapping("/detail/cancel/{docNo}")
    public ResponseEntity<String> cancelApproval(@PathVariable String docNo) {

        return approvalService.cancelApproval(docNo);
    }

    /**
     * subject : 결재처리 팝업
     * update : 승인/반려, 의견
     * */
    @PostMapping("/detail/doApproval")
    public ResponseEntity<String> doApproval(@RequestBody ApprovalDetailDto approvalDetailDto) {

        return approvalService.doApproval(approvalDetailDto);
    }

    /**
     * subject : 결재 상신
     * Insert : TB_APPR_HD, TB_APPR_DT
     * @param approvalHeader
     * Desc.
     * docTypeCd(문서유형) / docMngNo(문서관리번호) / docTitleNm{제목(조합:문서분류;법인카드전표 / 총 금액 / 적요)}
     * 결재선 - approvalDetails (JSON Array)
     * apprSeq(순번) / apprTypeCd(결재유형코드) / aprverId(결재자 사번)
     * */
    @PutMapping("/detail/req")
    public ResponseEntity<ApprovalHeader> requestApproval(@RequestBody ApprovalHeader approvalHeader) {

        return approvalService.requestApproval(approvalHeader);
    }

    /**
     * subject : 결재선 조회
     * 부서 트리 구조
     * Desc. 부서 클릭 시 부서별 임직원 리스트 조회 - "/emp/dept/{deptCd}"
     * */
    @GetMapping("/detail/approvalLine")
    public ResponseEntity<List<ApprovalEmployeeDto>> getApprDeptTreeList() {
        /*List<ApprovalEmployeeDto> list = approvalService.getApprovalEmpList();*/
        List<ApprovalEmployeeDto> list = approvalService.getApprovalDeptTreeList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * subject : 결재위임관리
     * feature : 조회
     * */
    @PostMapping("/delegating/list")
    public ResponseEntity<List<ApprovalDelegateDto>> getApprDelegatingList(@RequestBody ApprovalDelegateDto approvalDelegateDto) {
        List<ApprovalDelegateDto> approvalDelegateDtos = approvalService.getApprovalDlgtList(approvalDelegateDto);
        return new ResponseEntity<>(approvalDelegateDtos, HttpStatus.OK);
    }

    /**
     * subject : 결재위임관리
     * feature : 저장(신규/갱신)
     * */
    @PostMapping("/delegating/save")
    public ResponseEntity<String> saveDelegating(@RequestBody ApprovalDelegateDto approvalDelegateDto) {

        return approvalService.saveApprovalDlgt(approvalDelegateDto);
    }

    /**
     * subject : 결재위임관리
     * feature : 지정해제
     * */
    @PostMapping("/delegating/cancel")
    public ResponseEntity<String> cancelDelegating(@RequestBody ApprovalDelegateDto approvalDelegateDto) {

        return approvalService.cancelApprovalDlgt(approvalDelegateDto);
    }

    /**
     * subject : 결재위임관리
     * feature : 위임여부 확인
     * @param adlgId is 위임자ID(결재선의 결재자 사번)
     * @param actId is 수임자ID(로그인한 사용자 사번)
     * */
    @GetMapping("/delegatingCheck")
    public ResponseEntity<String> getDelegatingCheck(@RequestParam String adlgId, @RequestParam String actId) {
        return approvalService.getDelegatingCheck(adlgId, actId);
    }

    /**
     * subject : 전결규정관리
     * feature : 리스트 조회
     * */
    @PostMapping("/rule/list")
    public ResponseEntity<List<ApprovalRuleDto>> getApprRuleList(@RequestBody ApprovalRuleDto approvalRuleDto) {

        List<ApprovalRuleDto> list = approvalService.getApprRuleList(approvalRuleDto);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    /**
     * subject : 전결규정관리
     * feature : 신규/수정
     * */
    @PostMapping("/rule")
    public ResponseEntity<String> saveApprovalRules(@RequestBody List<ApprovalRule> approvalRules) {

        return approvalService.saveApprovalRules(approvalRules);
    }
    /**
     * subject : 전결규정관리
     * feature : 행삭제
     * @param approvalRuleDto has 5 field for ApprovalRuleKey.
     *                        1. compCd
     *                        2. docTypeCd(BDGT/SLIP)
     *                        3. dtlTypeCd(상세유형)
     *                        4. curCd(통화코드)
     *                        5. ruleSeq(규정순번)
     * */
    @PostMapping("/deleteRules")
    public ResponseEntity<String> deleteApprovalRule(@RequestBody List<ApprovalRuleDto> approvalRuleDto) {

        return approvalService.deleteApprovalRule(approvalRuleDto);
    }
    /**
     * subject : 전결규정관리
     * feature : 적용시 저장된 전결 규정 가져오기
     * @param docTypeCd : 문서 유형. SLIP/BDGT
     * @param dtlTypeCd : 문서 상세유형
     * @param curCd : 통화유형
     * @param maxAmt : 상한금액
     * */
    @GetMapping("/rule")
    public ResponseEntity<List<ApprovalRuleDto>> getApprRuleLines(@RequestParam("docTypeCd") String docTypeCd,
                                                                  @RequestParam("dtlTypeCd") String dtlTypeCd,
                                                                  @RequestParam("curCd") String curCd,
                                                                  @RequestParam("maxAmt") String maxAmt) {
        List<ApprovalRuleDto> list = approvalService.getApprRuleLines(docTypeCd, dtlTypeCd, curCd, maxAmt);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
