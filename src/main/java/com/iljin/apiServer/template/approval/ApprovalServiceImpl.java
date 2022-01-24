package com.iljin.apiServer.template.approval;

import com.iljin.apiServer.core.mPush.MobilePushService;
import com.iljin.apiServer.core.security.user.User;
import com.iljin.apiServer.core.util.Util;
import com.iljin.apiServer.template.approval.dlgt.ApprovalDelegate;
import com.iljin.apiServer.template.approval.dlgt.ApprovalDelegateDto;
import com.iljin.apiServer.template.approval.dlgt.ApprovalDelegateKey;
import com.iljin.apiServer.template.approval.dlgt.ApprovalDelegateRepository;
import com.iljin.apiServer.template.approval.rule.ApprovalRule;
import com.iljin.apiServer.template.approval.rule.ApprovalRuleDto;
import com.iljin.apiServer.template.approval.rule.ApprovalRuleKey;
import com.iljin.apiServer.template.approval.rule.ApprovalRuleRepository;
import com.iljin.apiServer.template.system.emp.Employee;
import com.iljin.apiServer.template.system.emp.EmployeeKey;
import com.iljin.apiServer.template.system.emp.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

    private final Util util;
    private final EmployeeRepository employeeRepository;
    private final MobilePushService pushService;

    private final ApprovalHeaderRepository approvalHeaderRepository;
    private final ApprovalDetailRepository approvalDetailRepository;
    private final ApprovalRepositoryCustom approvalRepositoryCustom;
    private final ApprovalDelegateRepository approvalDelegateRepository;
    private final ApprovalRuleRepository approvalRuleRepository;

    private String getApprNo() {
        return approvalHeaderRepository.getApprNo();
    }

    @Override
    public List<ApprovalHeaderDto> getApprTodoList(ApprovalHeaderDto approvalHeaderDto) {
        String methodName = new Object() {}
        .getClass()
                .getEnclosingMethod()
                .getName();
        log.info("Service.Method : " + methodName);
        String loginId = util.getLoginUserId();
        approvalHeaderDto.setLoginId(loginId);
        //approvalHeaderDto.setLoginId("340363");

        return approvalRepositoryCustom.getApprTodoList(approvalHeaderDto);
    }

    @Override
    public List<ApprovalHeaderDto> getApprDoneList(ApprovalHeaderDto approvalHeaderDto) {
        String loginId = util.getLoginUserId();
        approvalHeaderDto.setLoginId(loginId);
        //approvalHeaderDto.setLoginId("340363");
        return approvalRepositoryCustom.getApprDoneList(approvalHeaderDto);
    }

    @Override
    public List<ApprovalHeaderDto> getApprReqList(ApprovalHeaderDto approvalHeaderDto) {
        String loginId = util.getLoginUserId();
        approvalHeaderDto.setLoginId(loginId);
        //approvalHeaderDto.setLoginId("340363");
        return approvalRepositoryCustom.getApprReqList(approvalHeaderDto);
    }

    @Override
    public Map<String, Object> getApproval(String docNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String docMngNo = "";// 문서관리번호
        String docModulType = "";// 문서 모듈 타입(EA/BD/AP/GR)

        List<ApprovalHeaderDto> apprHeader = approvalRepositoryCustom.getApprHeader(docNo);
        if (!apprHeader.isEmpty()) {
            List<ApprovalDetailDto> apprDetails = approvalRepositoryCustom.getApprDetail(docNo);

            map.put("apprHeader", apprHeader);
            map.put("apprDetails", apprDetails);
        }

        docMngNo = apprHeader.get(0).getDocMngNo();
        docModulType = docMngNo.substring(0, 2);

        //TODO 전자결재에 가져올 정보 조회 및 응답 데이터 구성 ====================================================
/*
        if (docModulType.equals("EA")) {
            // 전표
            Optional<SlipHeaderDto> slipHeader = slipRepositoryCustom.getSlipHeader(docMngNo);
            slipHeader.ifPresent(header -> {
                // slipTypeCd : E1 (법인카드 추가조회)
                if (SlipType.CARD.getCode().equals(header.getSlipTypeCd())) {
                    CardSlipHeaderDto cardHeader = new CardSlipHeaderDto();
                    try {
                        PropertyUtils.copyProperties(cardHeader, header);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cardUseListRepositoryCustom.getCardUseList(cardHeader.getUseDtlsNo()).ifPresent(card -> {
                        cardHeader.setCrdPssrId(card.getCrdPssrId());
                        cardHeader.setCrdPssrNm(card.getCrdPssrNm());
                        cardHeader.setCrdPssrTitle(card.getCrdPssrTitle());
                        cardHeader.setCrdFgCd(card.getCrdFgCd());
                        cardHeader.setCrdFgNm(card.getCrdFgNm());
                        cardHeader.setApprDate(card.getApprDate());
                        cardHeader.setApprTime(card.getApprTime());
                        cardHeader.setMerchAddr(card.getMerchAddr());
                        cardHeader.setEtc3(card.getEtc3());
                        cardHeader.setMccName(card.getMccName());
                        cardHeader.setProcPeriod(card.getProcPeriod());
                        cardHeader.setTaxTypeCd(card.getTaxTypeCd());
                        cardHeader.setTaxTypeNm(card.getTaxTypeNm());
                        cardHeader.setTips(card.getTips());
                    });
                    map.put("slipHeader", cardHeader);
                } else {
                    map.put("slipHeader", header);
                }
                List<SlipDetailDto> slipDetails = slipRepositoryCustom.getSlipDetails(header.getEaSlipNo());
                map.put("slipDetails", slipDetails);
            });
        } else if (docModulType.equals("GR")) {
            // 그룹결재
            Optional<SlipGroupDto> slipGroupHeader = slipGroupRepositoryCustom.getSlipGroupHeader(docMngNo);
            if (slipGroupHeader.isPresent()) {
                SlipHistoryDto slipHistoryDto = new SlipHistoryDto();
                slipHistoryDto.setGrSlipNo(docMngNo);
                List<SlipHistoryDto> slipGroupDetails = slipManagementService.getSlipHistories(slipHistoryDto);

                map.put("slipGroupHeader", slipGroupHeader);
                map.put("slipGroupDetails", slipGroupDetails);
            }
        } else if (docModulType.equals("BD")) {
            // 예산
            Optional<BudgetHeaderDto> budgetHeader = budgetRepositoryCustom.getBudgetHeader(docMngNo);
            if (budgetHeader.isPresent()) {
                List<BudgetDetailDto> budgetDetails = budgetRepositoryCustom.getBudgetDetails(docMngNo);

                map.put("budgetHeader", budgetHeader);
                map.put("budgetDetails", budgetDetails);
            }
        }*/

        return map;
    }

    @Override
    public ResponseEntity<String> cancelApproval(String docNo) {
        User loginUser = util.getLoginUser();
        String loginId = loginUser.getLoginId();

        String msg = "";
        String docMngNo = "";// 문서관리번호

        Optional<ApprovalHeader> header = approvalHeaderRepository.findById(docNo);
        if (header.isPresent()) {
            docMngNo = header.get().getDocMngNo();

            // 결재요청 상태(REQ)
            if (header.get().getDocStatCd().equals(ApprovalState.REQUEST_APPROVAL.getCode())) {

                //TODO : 해당 전자결재에서 처리하는 문서의 상신 취소시 업무로직 구현
                /*
                // 해당 전자결재가 전표인 경우
                if (header.get().getDocTypeCd().equals(ApprovalType.TYPE_SLIP.getCode())) {
                    // docNmgNo가 GR로 시작하는 하는 경우 *그룹상신
                    if ("GR".equals(docMngNo.substring(0, 2))) {

                        // tb_slip_hd 데이터 원복
                        List<SlipHeader> slipHeaders = slipHeaderRepository.findByGrSlipNo(docMngNo);

                        for (SlipHeader slipHeader : slipHeaders) {

                            slipHeaderRepository.findByEaSlipNo(slipHeader.getEaSlipNo()).ifPresent(c -> {

                                c.setSlipStatCd("10");
                                c.setGrSlipNo("");
                                c.setChgId(loginId);
                                c.setChgDtm(LocalDateTime.now());

                                slipHeaderRepository.save(c);

                                *//* 예산통제 - 복원 *//*
                                Map<String, String> spResult = budgetRepositoryCustom.runBudgetControlProcedure("CNL",
                                        slipHeader.getEaSlipNo());
                                String oRtnCd = spResult.get("oRtnCd");
                                String oRtnMsg = spResult.get("oRtnMsg");

                                if (!oRtnCd.equals("S")) {
                                    throw new RuntimeException(oRtnMsg);
                                }

                            });
                        }

                        // tb_slip_gr 데이터 삭제
                        slipGroupRepository.deleteByGrSlipNo(docMngNo);

                        msg = "상신 취소되었습니다.";

                    } else {

                        // SLIP
                        slipHeaderRepository.findByEaSlipNo(docMngNo).ifPresent(c -> {
                            c.setSlipStatCd("10");
                            c.setChgId(loginId);
                            c.setChgDtm(LocalDateTime.now());

                            slipHeaderRepository.save(c);
                        });

                        *//* 예산통제 - 복원 *//*
                        Map<String, String> spResult = budgetRepositoryCustom.runBudgetControlProcedure("CNL",
                                docMngNo);
                        String oRtnCd = spResult.get("oRtnCd");
                        String oRtnMsg = spResult.get("oRtnMsg");
                        if (!oRtnCd.equals("S")) {
                            throw new RuntimeException(oRtnMsg);
                        }

                        msg = "상신 취소되었습니다.";
                    }

                } else {

                    // BUDGET
                    budgetHeaderRepository.findById(docMngNo).ifPresent(c -> {
                        c.setBudStatCd("10");
                        c.setChgId(loginId);
                        c.setChgDtm(LocalDateTime.now());

                        budgetHeaderRepository.save(c);
                    });

                    *//* 예산통제 - 복원 *//*
                    Map<String, String> spResult = budgetRepositoryCustom.runBudgetControlProcedure("CNL", docMngNo);
                    String oRtnCd = spResult.get("oRtnCd");
                    String oRtnMsg = spResult.get("oRtnMsg");
                    if (!oRtnCd.equals("S")) {
                        throw new RuntimeException(oRtnMsg);
                    }

                    msg = "상신 취소되었습니다.";
                }
*/
            } else {
                msg = "상신취소는 결재요청 상태에서만 가능합니다.";
            }

            approvalDetailRepository.deleteByApprNo(docNo);
            approvalHeaderRepository.deleteById(docNo);
        } else {
            msg = "[" + docNo + "] 에 대한 전자결재 정보가 없습니다.";
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> doApproval(ApprovalDetailDto approvalDetailDto) {

        User loginUser = util.getLoginUser();
        String loginId = loginUser.getLoginId();
        String compCd = loginUser.getCompCd();

        EmployeeKey empKey = new EmployeeKey(loginId, compCd);
        Optional<Employee> emp = employeeRepository.findById(empKey);

        String currentApproNo = approvalDetailDto.getApprNo();
        Short currentApprSeq = approvalDetailDto.getApprSeq();
        /*
         * update table table : TB_APPR_DT
         */
        approvalDetailRepository.findByApprNoAndApprSeq(currentApproNo, currentApprSeq).ifPresent(c -> {
            c.setApprFgCd(approvalDetailDto.getApprFgCd());
            c.setApprDesc(approvalDetailDto.getApprDesc());
            c.setApprDtm(LocalDateTime.now());
            c.setChgId(loginId);
            c.setChgDtm(LocalDateTime.now());
            approvalDetailRepository.save(c);
        });

        /* max Approval Seq */
        Optional<ApprovalDetail> approvalDetail = approvalDetailRepository
                .findTopByApprNoOrderByApprSeqDesc(currentApproNo);
        Short apprMaxSeq = approvalDetail.get().getApprSeq();

        /*
         * update Header Tables
         */
        approvalHeaderRepository.findById(currentApproNo).ifPresent(approvalHeader -> {
            String docType = approvalHeader.getDocTypeCd(); // 결재문서 유형(전표/예산:SLIP/BDGT)
            String docMngno = approvalHeader.getDocMngNo(); // 문서관리번호
            String apprType = docMngno.substring(0, 2); // 그룹결재 구분을 위한 변수

            if (approvalDetailDto.getApprFgCd().equals("A")) { // 승인
                if (currentApprSeq == apprMaxSeq) {
                    /*
                     * TB_APPR_HD.DOC_STAT_CD = 'APR' 최종결재
                     */
                    // ApprovalHeader
                    approvalHeader.setDocStatCd(ApprovalState.COMPLETED_APPROVAL.getCode());
                    approvalHeader.setFnlSeq(currentApprSeq);
                    approvalHeader.setChgId(loginId);
                    approvalHeaderRepository.save(approvalHeader);

                    // ApprovalDetail
                    approvalDetailRepository.findByApprNoAndApprSeq(currentApproNo, currentApprSeq).ifPresent(c -> {
                        c.setApprDesc(approvalDetailDto.getApprDesc());
                        c.setAAprverId(loginId);
                        c.setAAprverDeptNm(emp.get().getDeptNm());
                        c.setAAprverJobNm(emp.get().getJobNm());
                        c.setAAprverNm(emp.get().getEmpNm());
                        c.setChgId(loginId);
                        c.setChgDtm(LocalDateTime.now());
                        approvalDetailRepository.save(c);
                    });



                    //TODO : 해당 전자결재 최종 승인 단계시 처리할 문서의 업무로직 구현
/*

                    */
/*
                     * 결재문서에 걸려있는 문서(전표/예산)
                     *//*

                    if (docType.equals(ApprovalType.TYPE_SLIP.getCode())) {
                        // 결재문서가 전표의 경우 전표 테이블 업데이트
                        if (apprType.equals("GR")) {
                            // 전표 그룹결재 처리
                            slipGroupRepository.findById(docMngno).ifPresent(slipGroup -> {
                                slipGroup.setGrApprStatCd(GroupApprStatus.COMPLETED_APPROVAL.getCode());
                                slipGroup.setChgId(loginId);
                                slipGroup.setChgDtm(LocalDateTime.now());
                                slipGroupRepository.save(slipGroup);

                                List<SlipHeader> slipHeaders = slipHeaderRepository.findByGrSlipNo(docMngno);
                                for (SlipHeader slipHeader : slipHeaders) {
                                    slipHeaderRepository.findByEaSlipNo(slipHeader.getEaSlipNo()).ifPresent(c -> {
                                        c.setSlipStatCd(SlipStatus.COMPLETED_APPROVAL.getCode());
                                        c.setFnlAprverId(loginId);
                                        c.setFnlApprDtm(LocalDate.now());
                                        c.setChgId(loginId);
                                        slipHeaderRepository.save(c);

                                        */
/* 법인카드 이용내역 처리 완료 *//*

                                        if (c.getSlipTypeCd().equals(SlipType.CARD.getCode())) {
                                            cardUseListRepository.findById(c.getUseDtlsNo()).ifPresent(cardUseList -> {
                                                cardUseList.setUseDtlsStatCd(DealStatus.COMPLETED.getCode());
                                                cardUseList.setChgId(loginId);
                                                cardUseListRepository.save(cardUseList);
                                            });
                                        }
                                    });
                                }
                            });
                        } else {
                            // 전표 단 건 결재 처리
                            slipHeaderRepository.findByEaSlipNo(docMngno).ifPresent(slipHeader -> {
                                slipHeader.setSlipStatCd(SlipStatus.COMPLETED_APPROVAL.getCode()); // 결재완료
                                slipHeader.setFnlAprverId(loginId);
                                slipHeader.setFnlApprDtm(LocalDate.now());
                                slipHeader.setChgId(loginId);
                                slipHeaderRepository.save(slipHeader);

                                */
/* 법인카드 이용내역 처리 완료 *//*

                                if (slipHeader.getSlipTypeCd().equals(SlipType.CARD.getCode())) {
                                    cardUseListRepository.findById(slipHeader.getUseDtlsNo()).ifPresent(cardUseList -> {
                                        cardUseList.setUseDtlsStatCd(DealStatus.COMPLETED.getCode());
                                        cardUseList.setChgId(loginId);
                                        cardUseListRepository.save(cardUseList);
                                    });
                                }
                            });
                        }

                    } else if (docType.equals(ApprovalType.TYPE_BUDGET.getCode())) {
                        // 결재문서가 예산의 경우 예산 테이블 업데이트
                        budgetHeaderRepository.findById(docMngno).ifPresent(budgetHeader -> {
                            budgetHeader.setBudStatCd(BudgetStatus.COMPLETED_APPROVAL.getCode()); // 결재완료
                            budgetHeader.setFnlAprverId(loginId);
                            budgetHeader.setFnlApprDtm(LocalDateTime.now());
                            budgetHeader.setChgId(loginId);
                            budgetHeaderRepository.save(budgetHeader);
                        });
                    }
*/

                } else {
                    /*
                     * TB_APPR_HD.DOC_STAT_CD = 'ING' 결재 진행
                     */
                    // ApprovalHeader
                    approvalHeader.setDocStatCd(ApprovalState.PROGRESS_APPROVAL.getCode());
                    approvalHeader.setFnlSeq(currentApprSeq);
                    approvalHeader.setChgId(loginId);
                    approvalHeaderRepository.save(approvalHeader);

                    // ApprovalDetail
                    approvalDetailRepository.findByApprNoAndApprSeq(currentApproNo, currentApprSeq).ifPresent(c -> {
                        c.setApprDesc(approvalDetailDto.getApprDesc());
                        c.setAAprverId(loginId);
                        c.setAAprverDeptNm(emp.get().getDeptNm());
                        c.setAAprverJobNm(emp.get().getJobNm());
                        c.setAAprverNm(emp.get().getEmpNm());
                        c.setChgId(loginId);
                        c.setChgDtm(LocalDateTime.now());
                        approvalDetailRepository.save(c);
                    });


                    //TODO : 해당 전자결재 승인 단계시 처리할 문서의 업무로직 구현
/*
                    *//*
                     * 결재문서에 걸려있는 문서(전표/예산)
                     *//*
                    if (docType.equals(ApprovalType.TYPE_SLIP.getCode())) {
                        // 결재문서가 전표의 경우 전표 테이블 업데이트
                        if (apprType.equals("GR")) {
                            // 전표 그룹결재 처리
                            slipGroupRepository.findById(docMngno).ifPresent(slipGroup -> {
                                slipGroup.setGrApprStatCd(GroupApprStatus.PROGRESS_APPROVAL.getCode());
                                slipGroup.setChgId(loginId);
                                slipGroup.setChgDtm(LocalDateTime.now());
                                slipGroupRepository.save(slipGroup);

                                List<SlipHeader> slipHeaders = slipHeaderRepository.findByGrSlipNo(docMngno);
                                for (SlipHeader slipHeader : slipHeaders) {
                                    slipHeaderRepository.findByEaSlipNo(slipHeader.getEaSlipNo()).ifPresent(c -> {
                                        c.setSlipStatCd(SlipStatus.PROGRESS_APPROVAL.getCode());
                                        c.setChgId(loginId);
                                        slipHeaderRepository.save(c);

                                        *//* 법인카드 이용내역 처리 진행 *//*
                                        if (c.getSlipTypeCd().equals(SlipType.CARD.getCode())) {
                                            cardUseListRepository.findById(c.getUseDtlsNo()).ifPresent(cardUseList -> {
                                                cardUseList.setUseDtlsStatCd(DealStatus.IN_PROGRESS.getCode());
                                                cardUseList.setChgId(loginId);
                                                cardUseListRepository.save(cardUseList);
                                            });
                                        }
                                    });
                                }
                            });
                        } else {
                            // 전표 단 건 결재 처리
                            slipHeaderRepository.findByEaSlipNo(docMngno).ifPresent(slipHeader -> {
                                slipHeader.setSlipStatCd(SlipStatus.PROGRESS_APPROVAL.getCode()); // 결재진행
                                slipHeader.setFnlAprverId(loginId);
                                slipHeader.setFnlApprDtm(LocalDate.now());
                                slipHeader.setChgId(loginId);
                                slipHeaderRepository.save(slipHeader);

                                *//* 법인카드 이용내역 처리 진행 *//*
                                if (slipHeader.getSlipTypeCd().equals(SlipType.CARD.getCode())) {
                                    cardUseListRepository.findById(slipHeader.getUseDtlsNo()).ifPresent(cardUseList -> {
                                        cardUseList.setUseDtlsStatCd(DealStatus.IN_PROGRESS.getCode());
                                        cardUseList.setChgId(loginId);
                                        cardUseListRepository.save(cardUseList);
                                    });
                                }
                            });
                        }

                    } else if (docType.equals(ApprovalType.TYPE_BUDGET.getCode())) {
                        // 결재문서가 예산의 경우 예산 테이블 업데이트
                        budgetHeaderRepository.findById(docMngno).ifPresent(budgetHeader -> {
                            budgetHeader.setBudStatCd(BudgetStatus.PROGRESS_APPROVAL.getCode()); // 결재진행
                            budgetHeader.setFnlAprverId(loginId);
                            budgetHeader.setFnlApprDtm(LocalDateTime.now());
                            budgetHeader.setChgId(loginId);
                            budgetHeaderRepository.save(budgetHeader);
                        });
                    }*/
                }
            } else if (approvalDetailDto.getApprFgCd().equals("R")) {
                /*
                 * TB_APPR_HD.DOC_STAT_CD = "REJ" 반려
                 */
                // ApprovalHeader
                approvalHeader.setDocStatCd(ApprovalState.REJECT_APPROVAL.getCode());
                approvalHeader.setFnlSeq(currentApprSeq);
                approvalHeader.setChgId(loginId);
                approvalHeaderRepository.save(approvalHeader);

                // ApprovalDetail
                approvalDetailRepository.findByApprNoAndApprSeq(currentApproNo, currentApprSeq).ifPresent(c -> {
                    c.setApprDesc(approvalDetailDto.getApprDesc());
                    c.setAAprverId(loginId);
                    c.setAAprverDeptNm(emp.get().getDeptNm());
                    c.setAAprverJobNm(emp.get().getJobNm());
                    c.setAAprverNm(emp.get().getEmpNm());
                    c.setChgId(loginId);
                    c.setChgDtm(LocalDateTime.now());
                    approvalDetailRepository.save(c);
                });


                //TODO : 해당 전자결재 반려시 처리할 문서의 업무로직 구현
/*
                *//*
                 * 결재문서에 걸려있는 문서(전표/예산)
                 *//*
                if (docType.equals(ApprovalType.TYPE_SLIP.getCode())) {
                    // 결재문서가 전표의 경우 전표 테이블 업데이트
                    if (apprType.equals("GR")) {
                        // Group전표
                        slipGroupRepository.findById(docMngno).ifPresent(slipGroup -> {
                            slipGroup.setGrApprStatCd(GroupApprStatus.REJECT_APPROVAL.getCode());
                            slipGroup.setChgId(loginId);
                            slipGroup.setChgDtm(LocalDateTime.now());
                            slipGroupRepository.save(slipGroup);

                            List<SlipHeader> slipHeaders = slipHeaderRepository.findByGrSlipNo(docMngno);
                            for (SlipHeader slipHeader : slipHeaders) {
                                slipHeaderRepository.findByEaSlipNo(slipHeader.getEaSlipNo()).ifPresent(c -> {
                                    c.setSlipStatCd(SlipStatus.REJECT_APPROVAL.getCode());
                                    c.setChgId(loginId);
                                    slipHeaderRepository.save(c);

                                    *//*
                                     * 법인카드 이용내역 '미처리'로 갱신
                                     *//*
                                    if (c.getSlipTypeCd().equals(SlipType.CARD.getCode())) {
                                        *//*
                                         * 법인카드 전표 slipHeader.getUseDtlsNo() : 이용내역번호 (법인카드 사용내역 매핑 키)
                                         *//*
                                        cardUseListRepository.findById(c.getUseDtlsNo()).ifPresent(cardUseList -> {
                                            cardUseList.setUseDtlsStatCd(DealStatus.UNTREATED.getCode()); // '미처리'
                                            cardUseList.setChgId(loginId);
                                            cardUseListRepository.save(cardUseList);
                                        });
                                    }
                                    *//* 예산통제 - 복원 *//*
                                    Map<String, String> spResult = budgetRepositoryCustom
                                            .runBudgetControlProcedure("REJ", c.getEaSlipNo());
                                    String oRtnCd = spResult.get("oRtnCd");
                                    String oRtnMsg = spResult.get("oRtnMsg");
                                    if (!oRtnCd.equals("S")) {
                                        throw new RuntimeException(oRtnMsg);
                                    }
                                });
                            }
                        });
                    } else {
                        // 일반전표
                        slipHeaderRepository.findByEaSlipNo(docMngno).ifPresent(slipHeader -> {
                            slipHeader.setSlipStatCd(SlipStatus.REJECT_APPROVAL.getCode()); // 결재반려
                            slipHeader.setFnlAprverId(loginId);
                            slipHeader.setFnlApprDtm(LocalDate.now());
                            slipHeader.setChgId(loginId);
                            slipHeaderRepository.save(slipHeader);

                            *//*
                             * 법인카드/전자세금계산서 전표의 경우 '미처리'로 갱신
                             *//*
                            if (slipHeader.getSlipTypeCd().equals(SlipType.CARD.getCode())) {
                                *//*
                                 * 법인카드 전표 slipHeader.getUseDtlsNo() : 이용내역번호 (법인카드 사용내역 매핑 키)
                                 *//*
                                cardUseListRepository.findById(slipHeader.getUseDtlsNo()).ifPresent(cardUseList -> {
                                    cardUseList.setUseDtlsStatCd(DealStatus.UNTREATED.getCode()); // '미처리'
                                    cardUseList.setChgId(loginId);
                                    cardUseListRepository.save(cardUseList);
                                });
                            } else if (slipHeader.getSlipTypeCd().equals(SlipType.EBILL.getCode())) {
                                *//*
                                 * 전자세금계산서 전표
                                 *//*
                                etaxXmlHeaderRepository.findByInvSeq(slipHeader.getEtaxSeq())
                                        .ifPresent(etaxXmlHeader -> {
                                            etaxXmlHeader.setDealStatCd(DealStatus.UNTREATED.getCode()); // '미처리'
                                            etaxXmlHeader.setChgId(loginId);
                                            etaxXmlHeaderRepository.save(etaxXmlHeader);
                                        });
                            }

                            *//* 예산통제 - 복원 *//*
                            Map<String, String> spResult = budgetRepositoryCustom.runBudgetControlProcedure("REJ",
                                    slipHeader.getEaSlipNo());
                            String oRtnCd = spResult.get("oRtnCd");
                            String oRtnMsg = spResult.get("oRtnMsg");
                            if (!oRtnCd.equals("S")) {
                                throw new RuntimeException(oRtnMsg);
                            }
                        });
                    }

                } else if (docType.equals(ApprovalType.TYPE_BUDGET.getCode())) {
                    // 결재문서가 예산의 경우 예산 테이블 업데이트
                    budgetHeaderRepository.findById(docMngno).ifPresent(budgetHeader -> {
                        budgetHeader.setBudStatCd(BudgetStatus.REJECT_APPROVAL.getCode()); // 결재반려
                        budgetHeader.setFnlApprDtm(LocalDateTime.now());
                        budgetHeader.setFnlAprverId(loginId);
                        budgetHeader.setChgId(loginId);
                        budgetHeaderRepository.save(budgetHeader);

                        *//* 예산통제 - 복원 *//*
                        Map<String, String> spResult = budgetRepositoryCustom.runBudgetControlProcedure("REJ",
                                budgetHeader.getBudReqNo());
                        String oRtnCd = spResult.get("oRtnCd");
                        String oRtnMsg = spResult.get("oRtnMsg");
                        if (!oRtnCd.equals("S")) {
                            throw new RuntimeException(oRtnMsg);
                        }
                    });
                }*/
            }
        });

        return new ResponseEntity<>("결재하였습니다.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApprovalHeader> requestApproval(ApprovalHeader approvalHeader) {

        String apprNo = approvalHeader.getApprNo();

        User loginUser = util.getLoginUser();
        String loginId = loginUser.getLoginId();
        String loginUserNm = loginUser.getUserName();
        String compCd = loginUser.getCompCd();
        String attribute1 = loginUser.getAttribute1();
        /*
         * String loginId = "341002"; String compCd = "101600";
         */

        String approvalType = approvalHeader.getDocTypeCd();
        String docMngNo = approvalHeader.getDocMngNo();

        approvalHeaderRepository.findByDocMngNo(docMngNo).ifPresent(c -> {
            throw new RuntimeException("이미 상신된 문서입니다. (문서관리번호: " + docMngNo + ")");
        });

        approvalHeader.setApprNo(StringUtils.isEmpty(apprNo) ? this.getApprNo() : apprNo);
        approvalHeader.setCompCd(compCd);
        approvalHeader.setDocStatCd(ApprovalState.REQUEST_APPROVAL.getCode());
        approvalHeader.setDraftId(loginId);
        approvalHeader.setDraftDtm(LocalDateTime.now());
        approvalHeader.setFnlSeq((short) 1);
        approvalHeader.setRegId(loginId);
        approvalHeader.setChgId(loginId);

        if (approvalHeader.getApprovalDetails().size() > 0) {
            // on Web browser
            for (ApprovalDetail approvalDetail : approvalHeader.getApprovalDetails()) {
                String apprTypeCd = approvalDetail.getApprTypeCd();

                EmployeeKey empKey = new EmployeeKey(approvalDetail.getAprverId(), compCd);
                Optional<Employee> emp = employeeRepository.findById(empKey);

                approvalDetail.setApprNo(approvalHeader.getApprNo());
                approvalDetail.setCompCd(compCd);
                approvalDetail.setApprFgCd("X");
                approvalDetail.setAprverNm(emp.get().getEmpNm());
                approvalDetail.setAprverDeptNm(emp.get().getDeptNm());
                approvalDetail.setAprverJobNm(emp.get().getJobNm());
                approvalDetail.setRegId(loginId);
                approvalDetail.setChgId(loginId);

                if (apprTypeCd.equals(ApprovalLineType.DRAFT.getCode())) {
                    // 기안자 결재 승인 처리
                    approvalDetail.setApprFgCd("A");
                    approvalDetail.setAAprverId(approvalDetail.getAprverId());
                    approvalDetail.setAAprverNm(approvalDetail.getAprverNm());
                    approvalDetail.setAAprverDeptNm(approvalDetail.getAprverDeptNm());
                    approvalDetail.setAAprverJobNm(approvalDetail.getAprverJobNm());
                    approvalDetail.setApprDtm(LocalDateTime.now());
                }
            }
        }

        /*
         * 결재 상신 테이블 : TB_APPR_HD, TB_APPR_DT
         */
        approvalHeaderRepository.save(approvalHeader);

        // --------- Transaction Management Test
        // ApprovalHeader approvalHeaderTest = new ApprovalHeader();
        // approvalHeaderRepository.save(approvalHeaderTest);


        //TODO : 전자결재 상신 시 처리할 문서의 업무 로직 구현
/*
        if (approvalType.equals(ApprovalType.TYPE_SLIP.getCode())) {
            slipHeaderRepository.findByEaSlipNo(docMngNo).ifPresent(slipHeader -> {
                *//*
                 * Check remain budget(잔여예산 체크)
                 *//*
                // String cctrCd = slipHeader.getBaseDeptCd();
                String postDt = slipHeader.getPostDt();
                if (postDt.length() > 0) {
                    postDt = postDt.replaceAll("[^0-9]", "");
                    postDt = postDt.substring(0, 6);
                }

                List<SlipDetail> details = slipDetailRepository.findByEaSlipNo(slipHeader.getEaSlipNo());
                if (details.size() > 0) {
                    for (SlipDetail slipDetail : details) {
                        int itemSeq = slipDetail.getItemSeq();

                        *//* 라인별 마감확인 *//*
                        String slipDtCompCd = slipDetail.getCompCd();// 라인별 회사코드
                        String slipDtCctrCd = slipDetail.getCctrCd();// 라인별 비용부서
                        String slipDtBaCd = "";// 라인별 사업영역
                        CostCenterKey costCenterKey = new CostCenterKey();
                        costCenterKey.setCompCd(slipDtCompCd);
                        costCenterKey.setCctrCd(slipDtCctrCd);
                        Optional<CostCenter> costCenter = costCenterRepository.findById(costCenterKey);
                        if (costCenter.isPresent()) {
                            slipDtBaCd = costCenter.get().getBaCd();
                        } else {
                            throw new RuntimeException(itemSeq + " 번 라인 비용부서: [" + slipDtCctrCd + "]의 사업영역이 없습니다.");
                        }
                        Optional<AcctPeriod> acctPeriod = acctPeriodRepository
                                .findByCompCdAndBaCdAndClosYm(slipDtCompCd, slipDtBaCd, postDt);
                        if (acctPeriod.isPresent()) {
                            if (acctPeriod.get().getClosStatCd().equals("Close"))
                                throw new RuntimeException("해당 영역의 회계 기간이 마감 되었습니다.");
                        } else {
                            throw new RuntimeException(
                                    itemSeq + " 번 라인 비용부서: [" + slipDtCctrCd + "]의 회계기간이 조회되지 않습니다.");
                        }

                        *//* 차변 계정의 잔여예산 체크 *//*
                        String lnTypeCd = slipDetail.getLnTypeCd();// Line Type(ITEM)
                        String dcCd = slipDetail.getDcCd();// Credit/Debit
                        String acctCd = slipDetail.getAcctCd();// 계정코드
                        Optional<Account> ctrlYn = accountRepository.findByCompCdAndUseYnAndCtrlYnAndAcctCd(compCd, "Y",
                                "Y", acctCd);// 통제계정여부
                        String acctAmt = String.valueOf(slipDetail.getAcctAmt());// 차변금액
                        String lnSgtxt = slipDetail.getLnSgtxt();// 라인적요

                        if (lnTypeCd.equals("ITEM") && dcCd.equals("D") && ctrlYn.isPresent()) {
                            String rmBudget = budgetHeaderRepository.getRemainBudget(compCd, postDt, slipDtCctrCd,
                                    acctCd);
                            if (Integer.parseInt(acctAmt) > Integer.parseInt(rmBudget)) {
                                throw new RuntimeException(itemSeq + " 번 '" + lnSgtxt + "'의 신청금액이 잔여예산보다 작아야 합니다.");
                            }
                        }
                    }
                }
                *//*
                 * 전표 테이블 갱신 테이블 : TB_SLIP_HD 예산통제 - 가차감:결재요청
                 *//*
                Map<String, String> spResult = budgetRepositoryCustom.runBudgetControlProcedure("REQ",
                        slipHeader.getEaSlipNo());
                String oRtnCd = spResult.get("oRtnCd");
                String oRtnMsg = spResult.get("oRtnMsg");
                if (!oRtnCd.equals("S")) {
                    throw new RuntimeException(oRtnMsg);
                }

                slipHeader.setSlipStatCd(SlipStatus.REQUEST_APPROVAL.getCode());
                slipHeader.setElecApprId(approvalHeader.getApprNo());
                slipHeader.setElecApprDtm(LocalDate.now());
                slipHeaderRepository.save(slipHeader);

                *//*
                 * on Mobile set default values
                 *//*
                *//* List<ApprovalDetail> apprDts = new ArrayList<>(); *//*
                if (approvalHeader.getApprovalDetails().size() <= 0) {
                    Short apprSeq = 1;

                    *//* 기안자 결재 승인 지정 *//*
                    EmployeeKey empKey = new EmployeeKey(loginId, compCd);
                    Optional<Employee> emp = employeeRepository.findById(empKey);

                    ApprovalDetail draftUser = new ApprovalDetail();
                    draftUser.setApprNo(approvalHeader.getApprNo());
                    draftUser.setApprSeq(apprSeq);
                    draftUser.setCompCd(compCd);
                    draftUser.setApprTypeCd(ApprovalLineType.DRAFT.getCode());
                    draftUser.setApprFgCd("A");
                    draftUser.setAprverId(loginId);
                    draftUser.setAprverNm(emp.get().getEmpNm());
                    draftUser.setAprverDeptNm(emp.get().getDeptNm());
                    draftUser.setAprverJobNm(emp.get().getJobNm());
                    draftUser.setRegId(loginId);
                    draftUser.setRegDtm(LocalDateTime.now());
                    draftUser.setChgId(loginId);
                    draftUser.setChgDtm(LocalDateTime.now());
                    draftUser.setAAprverId(loginId);
                    draftUser.setAAprverNm(emp.get().getEmpNm());
                    draftUser.setAAprverDeptNm(emp.get().getDeptNm());
                    draftUser.setAAprverJobNm(emp.get().getJobNm());
                    draftUser.setApprDtm(LocalDateTime.now());
                    *//* apprDts.add(draftUser); *//*
                    approvalDetailRepository.save(draftUser);

                    *//* 전결규정 결재라인 적용 *//*
                    String docTypeCd = approvalType;// SLIP
                    String dtlTypeCd = slipHeader.getSlipTypeCd();// Slip Type
                    String curCd = slipHeader.getCurCd();
                    String maxAmt = String.valueOf(slipHeader.getTotAmt());
                    List<ApprovalRuleDto> arLines = this.getApprRuleLines(docTypeCd, dtlTypeCd, curCd, maxAmt);
                    for (ApprovalRuleDto approvalRuleDto : arLines) {
                        apprSeq++;
                        ApprovalDetail approvalDetail = new ApprovalDetail();

                        approvalDetail.setApprNo(approvalHeader.getApprNo());
                        approvalDetail.setApprSeq(apprSeq);
                        approvalDetail.setCompCd(compCd);
                        draftUser.setApprTypeCd(approvalRuleDto.getApprTypeCd());
                        approvalDetail.setApprFgCd("X");
                        approvalDetail.setAprverId(approvalRuleDto.getAprverId());
                        approvalDetail.setAprverNm(approvalRuleDto.getAprverUser());
                        approvalDetail.setAprverDeptNm(approvalRuleDto.getCctrNm());
                        approvalDetail.setAprverJobNm(approvalRuleDto.getJobNm());
                        approvalDetail.setRegId(loginId);
                        approvalDetail.setRegDtm(LocalDateTime.now());
                        approvalDetail.setChgId(loginId);
                        approvalDetail.setChgDtm(LocalDateTime.now());

                        *//* apprDts.add(approvalDetail); *//*
                        approvalDetailRepository.save(approvalDetail);
                    }
                }
            });
        } else if (approvalType.equals(ApprovalType.TYPE_BUDGET.getCode())) {
            *//*
             * 예산 테이블 갱신 테이블 : TB_BUD_HD
             *//*
            budgetHeaderRepository.findById(docMngNo).ifPresent(budgetHeader -> {
                *//* 예산통제 - 가차감:결재요청 *//*
                Map<String, String> spResult = budgetRepositoryCustom.runBudgetControlProcedure("REQ",
                        budgetHeader.getBudReqNo());
                String oRtnCd = spResult.get("oRtnCd");
                String oRtnMsg = spResult.get("oRtnMsg");
                if (!oRtnCd.equals("S")) {
                    throw new RuntimeException(oRtnMsg);
                }

                budgetHeader.setBudStatCd(BudgetStatus.REQUEST_APPROVAL.getCode());
                budgetHeader.setElecApprId(approvalHeader.getApprNo());
                budgetHeader.setElecApprDtm(LocalDateTime.now());
                budgetHeaderRepository.save(budgetHeader);

                *//*
                 * on Mobile set default values
                 *//*
                *//* List<ApprovalDetail> apprDts = new ArrayList<>(); *//*
                if (approvalHeader.getApprovalDetails().size() <= 0) {
                    Short apprSeq = 1;

                    *//* 기안자 결재 승인 지정 *//*
                    EmployeeKey empKey = new EmployeeKey(loginId, compCd);
                    Optional<Employee> emp = employeeRepository.findById(empKey);

                    ApprovalDetail draftUser = new ApprovalDetail();
                    draftUser.setApprNo(approvalHeader.getApprNo());
                    draftUser.setApprSeq(apprSeq);
                    draftUser.setCompCd(compCd);
                    draftUser.setApprTypeCd(ApprovalLineType.DRAFT.getCode());
                    draftUser.setApprFgCd("A");
                    draftUser.setAprverId(loginId);
                    draftUser.setAprverNm(emp.get().getEmpNm());
                    draftUser.setAprverDeptNm(emp.get().getDeptNm());
                    draftUser.setAprverJobNm(emp.get().getJobNm());
                    draftUser.setRegId(loginId);
                    draftUser.setRegDtm(LocalDateTime.now());
                    draftUser.setChgId(loginId);
                    draftUser.setChgDtm(LocalDateTime.now());
                    draftUser.setAAprverId(loginId);
                    draftUser.setAAprverNm(emp.get().getEmpNm());
                    draftUser.setAAprverDeptNm(emp.get().getDeptNm());
                    draftUser.setAAprverJobNm(emp.get().getJobNm());
                    draftUser.setApprDtm(LocalDateTime.now());
                    *//* apprDts.add(draftUser); *//*
                    approvalDetailRepository.save(draftUser);

                    *//* 전결규정 결재라인 적용 *//*
                    String docTypeCd = approvalType;// BUDGET
                    String dtlTypeCd = budgetHeader.getBrwTypeCd();// budget request Type
                    String curCd = "KRW";
                    String maxAmt = String.valueOf(budgetHeader.getReqTotAmt());
                    List<ApprovalRuleDto> arLines = this.getApprRuleLines(docTypeCd, dtlTypeCd, curCd, maxAmt);
                    for (ApprovalRuleDto approvalRuleDto : arLines) {
                        apprSeq++;
                        ApprovalDetail approvalDetail = new ApprovalDetail();

                        approvalDetail.setApprNo(approvalHeader.getApprNo());
                        approvalDetail.setApprSeq(apprSeq);
                        approvalDetail.setCompCd(compCd);
                        draftUser.setApprTypeCd(approvalRuleDto.getApprTypeCd());
                        approvalDetail.setApprFgCd("X");
                        approvalDetail.setAprverId(approvalRuleDto.getAprverId());
                        approvalDetail.setAprverNm(approvalRuleDto.getAprverUser());
                        approvalDetail.setAprverDeptNm(approvalRuleDto.getCctrNm());
                        approvalDetail.setAprverJobNm(approvalRuleDto.getJobNm());
                        approvalDetail.setRegId(loginId);
                        approvalDetail.setRegDtm(LocalDateTime.now());
                        approvalDetail.setChgId(loginId);
                        approvalDetail.setChgDtm(LocalDateTime.now());

                        *//* apprDts.add(approvalDetail); *//*
                        approvalDetailRepository.save(approvalDetail);
                    }
                }
            });
        }*/

        //TODO : 전자결재 승인시 모바일 푸시 알림 기능 확인하세요.
        /*
         * push to mobile
         */
        ApprPushMessage apprPushMessage = new ApprPushMessage();
        apprPushMessage.setTitle(approvalHeader.getDocTitleNm());
        apprPushMessage.setDraftUser(loginUserNm);
        apprPushMessage.setApprNo(approvalHeader.getApprNo());
        apprPushMessage.setFcmToken(attribute1);
        pushService.pushApprMessage(apprPushMessage);

        return new ResponseEntity<>(approvalHeader, HttpStatus.CREATED);
    }

    @Override
    public List<ApprovalEmployeeDto> getApprovalEmpList() {
        return approvalRepositoryCustom.getApprovalEmpList();
    }

    @Override
    public List<ApprovalDelegateDto> getApprovalDlgtList(ApprovalDelegateDto approvalDelegateDto) {
        return approvalRepositoryCustom.getApprovalDlgtList(approvalDelegateDto);
    }

    @Override
    public ResponseEntity<String> saveApprovalDlgt(ApprovalDelegateDto approvalDelegateDto) {
        User loginUser = util.getLoginUser();
        String loginId = loginUser.getLoginId();
        String compCd = loginUser.getCompCd();
        //String loginId = "341002";
        //String compCd = "101600";

        // 위임자
        String adlgId = approvalDelegateDto.getAdlgId();
        // 수임자
        String actId = approvalDelegateDto.getActId();
        // 위임 순번
        Short adlgSeq = Optional.ofNullable(approvalDelegateDto.getAdlgSeq()).orElse((short) 0);
        ApprovalDelegateKey approvalDelegateKey = new ApprovalDelegateKey(adlgId, actId, adlgSeq);

        if (adlgSeq == 0) {
            /* 신규 위임 */
            Optional<ApprovalDelegate> approvalDelegate = approvalDelegateRepository
                    .findTopByAdlgIdAndActIdOrderByAdlgSeqDesc(adlgId, actId);

            if (approvalDelegate.isPresent()) {
                /*
                 * 같은 위임자, 수임자 조건인 경우 Max seq 처리
                 */
                if (approvalDelegate.get().getAdlgStatCd().equals(ApprovalDlgtStatus.DELEGATING.getCode())) {
                    /*
                     * Validation 상태가 위임인 건 중에서 위임자, 수임자 조건으로 위임기간이 겹치는 건이 있는지 확인
                     */

                    SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");
                    Date adlgStrDt = new Date();// 입력한
                    Date adlgEndDt = new Date();// 입력한
                    Date adlgStrDtStd = new Date();// 조회한
                    Date adlgEndDtStd = new Date();// 조회한
                    try {
                        adlgStrDt = dt.parse(approvalDelegateDto.getAdlgStrDt().replaceAll("-", ""));
                        adlgEndDt = dt.parse(approvalDelegateDto.getAdlgEndDt().replaceAll("-", ""));
                        adlgStrDtStd = dt.parse(approvalDelegate.get().getAdlgStrDt().replaceAll("-", ""));
                        adlgEndDtStd = dt.parse(approvalDelegate.get().getAdlgEndDt().replaceAll("-", ""));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (adlgStrDt.after(adlgEndDtStd) || adlgEndDt.before(adlgStrDtStd)) {
                        // 위임기간이 겹치지 않는 경우
                    } else {
                        // 위임기간이 겹치는 경우
                        throw new RuntimeException("위임기간이 중복되는 건이 존재합니다.");
                    }
                }

                /* set Value */
                ApprovalDelegate c = new ApprovalDelegate();

                short i = approvalDelegate.get().getAdlgSeq();
                i = (short) (i + 1);

                c.setAdlgId(approvalDelegateDto.getAdlgId());
                c.setActId(approvalDelegateDto.getActId());
                c.setCompCd(compCd);
                c.setAdlgSeq(i);
                c.setAdlgStatCd(ApprovalDlgtStatus.DELEGATING.getCode());
                c.setAdlgStrDt(approvalDelegateDto.getAdlgStrDt());
                c.setAdlgEndDt(approvalDelegateDto.getAdlgEndDt());
                c.setRegId(loginId);
                c.setRegDtm(LocalDateTime.now());
                c.setChgId(loginId);
                c.setChgDtm(LocalDateTime.now());

                approvalDelegateRepository.save(c);
            } else {
                /* seq : 1 */
                adlgSeq++;

                ApprovalDelegate approvalDelegate1 = new ApprovalDelegate();
                approvalDelegate1.setAdlgId(approvalDelegateDto.getAdlgId());
                approvalDelegate1.setActId(approvalDelegateDto.getActId());
                approvalDelegate1.setAdlgSeq(adlgSeq);
                approvalDelegate1.setAdlgStatCd(ApprovalDlgtStatus.DELEGATING.getCode());
                approvalDelegate1.setAdlgStrDt(approvalDelegateDto.getAdlgStrDt());
                approvalDelegate1.setAdlgEndDt(approvalDelegateDto.getAdlgEndDt());
                approvalDelegate1.setCompCd(compCd);
                approvalDelegate1.setRegId(loginId);
                approvalDelegate1.setRegDtm(LocalDateTime.now());
                approvalDelegate1.setChgId(loginId);
                approvalDelegate1.setChgDtm(LocalDateTime.now());

                approvalDelegateRepository.save(approvalDelegate1);
            }
        } else {
            /* 기존 기간 수정 */
            approvalDelegateRepository.findById(approvalDelegateKey).ifPresent(approvalDelegate -> {
                /* approvalDelegate.setAdlgSeq(finalAdlgSeq); */
                approvalDelegate.setAdlgStrDt(approvalDelegateDto.getAdlgStrDt());
                approvalDelegate.setAdlgEndDt(approvalDelegateDto.getAdlgEndDt());
                approvalDelegate.setChgId(loginId);
                approvalDelegate.setChgDtm(LocalDateTime.now());

                approvalDelegateRepository.save(approvalDelegate);
            });
        }

        return new ResponseEntity<>("Saved.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> cancelApprovalDlgt(ApprovalDelegateDto approvalDelegateDto) {
        User loginUser = util.getLoginUser();
        String loginId = loginUser.getLoginId();
        //String loginId = "341002";

        ApprovalDelegateKey approvalDelegateKey = new ApprovalDelegateKey(approvalDelegateDto.getAdlgId(),
                approvalDelegateDto.getActId(), approvalDelegateDto.getAdlgSeq());

        approvalDelegateRepository.findById(approvalDelegateKey).ifPresent(approvalDelegate -> {
            approvalDelegate.setAdlgStatCd(ApprovalDlgtStatus.CANCEL.getCode());
            approvalDelegate.setAdlgRvcDtm(LocalDateTime.now());
            approvalDelegate.setChgId(loginId);
            approvalDelegate.setChgDtm(LocalDateTime.now());

            approvalDelegateRepository.save(approvalDelegate);
        });

        return new ResponseEntity<>("Canceled.", HttpStatus.OK);
    }

    @Override
    public List<ApprovalRuleDto> getApprRuleList(ApprovalRuleDto approvalRuleDto) {
        return approvalRepositoryCustom.getApprRuleList(approvalRuleDto);
    }

    @Override
    public ResponseEntity<String> deleteApprovalRule(List<ApprovalRuleDto> approvalRuleDtoList) {
        User loginUser = util.getLoginUser();
        String compCd = loginUser.getCompCd();

        for (ApprovalRuleDto approvalRuleDto : approvalRuleDtoList) {
            ApprovalRuleKey approvalRuleKey = new ApprovalRuleKey(compCd,
                    approvalRuleDto.getDocTypeCd(),
                    approvalRuleDto.getDtlTypeCd(),
                    approvalRuleDto.getCurCd(),
                    approvalRuleDto.getRuleSeq());

            approvalRuleRepository.deleteById(approvalRuleKey);
        }

        return new ResponseEntity<>("removed.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> saveApprovalRules(List<ApprovalRule> approvalRules) {
        User loginUser = util.getLoginUser();
        String loginId = loginUser.getLoginId();
        String compCd = loginUser.getCompCd();
        //String loginId = "341002";
        //String compCd = "101600";

        for (ApprovalRule approvalRule : approvalRules) {
            String docTypeCd = approvalRule.getDocTypeCd();
            String dtlTypeCd = approvalRule.getDtlTypeCd();
            String curCd = approvalRule.getCurCd();
            Short ruleSeq = Optional.ofNullable(approvalRule.getRuleSeq()).orElse((short) 0);

            ApprovalRuleKey approvalRuleKey = new ApprovalRuleKey(compCd, docTypeCd, dtlTypeCd, curCd, ruleSeq);

            if (ruleSeq == 0) {
                // insert
                // search max Rule sequence
                Optional<ApprovalRule> maxRule = approvalRuleRepository
                        .findTopByCompCdAndDocTypeCdAndDtlTypeCdAndCurCdOrderByRuleSeqDesc(compCd, docTypeCd, dtlTypeCd,
                                curCd);

                if (maxRule.isPresent()) {
                    ruleSeq = maxRule.get().getRuleSeq();
                }

                ruleSeq++;
                approvalRule.setCompCd(compCd);
                approvalRule.setRuleSeq(ruleSeq);
                approvalRule.setRegId(loginId);
                approvalRule.setRegiDtm(LocalDateTime.now());
                approvalRule.setChgId(loginId);
                approvalRule.setChgDtm(LocalDateTime.now());
                approvalRuleRepository.save(approvalRule);
            } else {
                // update
                approvalRuleRepository.findById(approvalRuleKey).ifPresent(c -> {
                    c.setUseYn(approvalRule.getUseYn());
                    c.setMaxAmt(approvalRule.getMaxAmt());
                    c.setApprTypeCd(approvalRule.getApprTypeCd());
                    c.setFixYn(approvalRule.getFixYn());
                    c.setAprverClassCd(approvalRule.getAprverClassCd());
                    c.setAprverClassVal(approvalRule.getAprverClassVal());
                    c.setRemark(approvalRule.getRemark());
                    c.setChgId(loginId);
                    c.setChgDtm(LocalDateTime.now());
                    approvalRuleRepository.save(c);
                });
            }
        }

        return new ResponseEntity<>("saved.", HttpStatus.OK);
    }

    @Override
    public List<ApprovalHeaderDto> getApprIngList(ApprovalHeaderDto approvalHeaderDto) {
        String loginId = util.getLoginUserId();
        approvalHeaderDto.setLoginId(loginId);
        //approvalHeaderDto.setLoginId("340363");
        return approvalRepositoryCustom.getApprIngList(approvalHeaderDto);
    }

    @Override
    public List<ApprovalHeaderDto> getApprAprList(ApprovalHeaderDto approvalHeaderDto) {
        String loginId = util.getLoginUserId();
        approvalHeaderDto.setLoginId(loginId);
        return approvalRepositoryCustom.getApprAprList(approvalHeaderDto);
    }

    @Override
    public List<ApprovalHeaderDto> getApprRejList(ApprovalHeaderDto approvalHeaderDto) {
        String loginId = util.getLoginUserId();
        approvalHeaderDto.setLoginId(loginId);
        return approvalRepositoryCustom.getApprRejList(approvalHeaderDto);
    }

    @Override
    public List<ApprovalEmployeeDto> getApprovalDeptTreeList() {
        return approvalRepositoryCustom.getApprovalDeptTreeList();
    }

    @Override
    public List<ApprovalRuleDto> getApprRuleLines(String docTypeCd, String dtlTypeCd, String curCd, String maxAmt) {
        User loginUser = util.getLoginUser();
        String loginId = loginUser.getLoginId();
        String compCd = loginUser.getCompCd();

        String clean1 = maxAmt.replaceAll("[^0-9]", "");

        ApprovalRuleDto approvalRuleDto = new ApprovalRuleDto();
        approvalRuleDto.setCompCd(compCd);
        approvalRuleDto.setDocTypeCd(docTypeCd);
        approvalRuleDto.setDtlTypeCd(dtlTypeCd);
        approvalRuleDto.setCurCd(curCd);
        approvalRuleDto.setMaxAmt(BigDecimal.valueOf(Long.parseLong(clean1)));

        return approvalRepositoryCustom.getApprRuleLines(loginId, approvalRuleDto);
    }

    @Override
    public ResponseEntity<String> getDelegatingCheck(String adlgId, String actId) {
        //String compCd = util.getLoginCompCd();
        String compCd = "101600";

        LocalDate date = LocalDate.now();
        String todayDt = String.valueOf(date).replaceAll("[^0-9]", "");

        Optional<ApprovalDelegate> delegate = approvalDelegateRepository
                .findByAdlgIdAndActIdAndCompCdAndAdlgStatCdAndAdlgStrDtLessThanEqualAndAdlgEndDtGreaterThanEqual(adlgId,
                        actId, compCd, "10", todayDt, todayDt);

        String result = "";
        if (delegate.isPresent()) {
            result = "Y";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
