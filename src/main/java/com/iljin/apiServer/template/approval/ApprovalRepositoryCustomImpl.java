package com.iljin.apiServer.template.approval;

import com.iljin.apiServer.template.approval.dlgt.ApprovalDelegateDto;
import com.iljin.apiServer.template.approval.rule.ApprovalRuleDto;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ApprovalRepositoryCustomImpl implements ApprovalRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ApprovalHeaderDto> getApprTodoList(ApprovalHeaderDto approvalHeaderDto) {

        String loginId = approvalHeaderDto.getLoginId();

        /* search Conditions */
        String delegateChk = approvalHeaderDto.getDelegateChk();
        String docTypeCd = approvalHeaderDto.getDocTypeCd();
        String searchDtmFr = approvalHeaderDto.getSearchDtmFr();
        String searchDtmTo = approvalHeaderDto.getSearchDtmTo();
        String docTitleNm = approvalHeaderDto.getDocTitleNm();
        String draftUserId = approvalHeaderDto.getDraftUserId();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT HD.DOC_TYPE_CD" + "       ,A.DETAIL_NM AS DOC_TYPE" + "       ,HD.DOC_STAT_CD"
                + "       ,B.DETAIL_NM AS DOC_STAT_NM" + "       ,HD.DOC_TITLE_NM" + "       ,C.DETAIL_NM AS APPR_TYPE"
                + "       ,D.DETAIL_NM AS APPR_STATUS" + "       ,HD.APPR_NO" + "       ,HD.DOC_MNG_NO"
                + "       ,HD.DRAFT_ID AS DRAFT_USER_ID" + "       ,E.APRVER_NM AS DRAFT_USER_NAME"
                + "       ,E.APRVER_JOB_NM AS DRAFT_USER_JOB"
                + "       ,DATE_FORMAT(HD.DRAFT_DTM, '%Y-%m-%d') AS DRAFT_DTM" + "       , CASE"
                + "              WHEN SUBSTR(HD.DOC_MNG_NO,1,2) = 'GR'  THEN" + "                   H.TOT_AMT"
                + "              ELSE" + "                   F.TOT_AMT" + "        END AS TOT_AMT"
                + "       ,F.HD_SGTXT" + "       ,DATE_FORMAT(F.ELEC_APPR_DTM, '%Y-%m-%d') AS SLIP_APPR_DTM"
                + "       ,G.REQ_TOT_AMT" + "       ,G.REQ_RSN"
                + "       ,DATE_FORMAT(G.ELEC_APPR_DTM, '%Y-%m-%d') AS BUD_APPR_DTM" + "  FROM TB_APPR_HD HD"
                + "       INNER JOIN TB_APPR_DT DT ON HD.APPR_NO = DT.APPR_NO"
                + "       AND DT.APPR_SEQ = (SELECT MIN(T.APPR_SEQ) FROM TB_APPR_DT T WHERE T.APPR_NO = HD.APPR_NO AND T.APPR_FG_CD = 'X')");
        if (!StringUtils.isEmpty(delegateChk)) {// 위임포함
            sb.append(" AND DT.APRVER_ID IN (" + "   SELECT DL.ADLG_ID" + "     FROM TB_APPR_DELEGATE DL"
                    + "    WHERE DL.ACT_ID = :loginId" + "      AND DL.ADLG_STAT_CD = '10'"
                    + "      AND DL.ADLG_STR_DT <= DATE_FORMAT(NOW(), '%Y%m%d')"
                    + "      AND DL.ADLG_END_DT >= DATE_FORMAT(NOW(), '%Y%m%d')" + "   UNION ALL" + "   SELECT :loginId"
                    + "    FROM DUAL) ");
        } else {
            sb.append(" AND DT.APRVER_ID = :loginId");
        }
        sb.append("     LEFT OUTER JOIN TB_CODE_DT A ON A.GROUP_CD = 'DOC_TYPE_CD' AND A.DETAIL_CD = HD.DOC_TYPE_CD"
                + "       LEFT OUTER JOIN TB_CODE_DT B ON B.GROUP_CD = 'DOC_STAT_CD' AND B.DETAIL_CD = HD.DOC_STAT_CD"
                + "       LEFT OUTER JOIN TB_CODE_DT C ON C.GROUP_CD = 'APPR_TYPE_CD' AND C.DETAIL_CD = DT.APPR_TYPE_CD"
                + "       LEFT OUTER JOIN TB_CODE_DT D ON D.GROUP_CD = 'APPR_FG_CD' AND D.DETAIL_CD = DT.APPR_FG_CD"
                + "       LEFT OUTER JOIN TB_APPR_DT E ON E.APPR_NO = HD.APPR_NO AND E.APRVER_ID = HD.DRAFT_ID"
                + "       LEFT OUTER JOIN TB_SLIP_HD F ON F.EA_SLIP_NO = HD.DOC_MNG_NO"
                + "       LEFT OUTER JOIN TB_BUD_HD G ON G.BUD_REQ_NO = HD.DOC_MNG_NO"
                + "       LEFT OUTER JOIN TB_SLIP_GR H ON H.GR_SLIP_NO = HD.DOC_MNG_NO"
                + " WHERE HD.DOC_STAT_CD IN('REQ', 'ING')");

        if (!StringUtils.isEmpty(docTypeCd)) {// 문서유형
            sb.append(" AND HD.DOC_TYPE_CD = :docTypeCd");
        }
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') >= :searchDtmFr");
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') <= :searchDtmTo");
        }
        if (!StringUtils.isEmpty(draftUserId)) {// 기안자
            sb.append(" AND HD.DRAFT_ID = :draftUserId");
        }
        if (!StringUtils.isEmpty(docTitleNm)) {// 제목
            sb.append(" AND HD.DOC_TITLE_NM LIKE CONCAT('%', :docTitleNm, '%')");
        }
        sb.append(" ORDER BY HD.APPR_NO DESC");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("loginId", loginId);
        if (!StringUtils.isEmpty(docTypeCd)) {// 문서유형
            query.setParameter("docTypeCd", docTypeCd);
        }
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            query.setParameter("searchDtmFr", searchDtmFr.replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            query.setParameter("searchDtmTo", searchDtmTo.replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty(draftUserId)) {// 기안자
            query.setParameter("draftUserId", draftUserId);
        }
        if (!StringUtils.isEmpty(docTitleNm)) {// 제목
            query.setParameter("docTitleNm", docTitleNm);
        }

        return new JpaResultMapper().list(query, ApprovalHeaderDto.class);
    }

    @Override
    public List<ApprovalHeaderDto> getApprDoneList(ApprovalHeaderDto approvalHeaderDto) {

        String loginId = approvalHeaderDto.getLoginId();

        /* search Conditions */
        String docTypeCd = approvalHeaderDto.getDocTypeCd();
        String searchDtmFr = approvalHeaderDto.getSearchDtmFr();
        String searchDtmTo = approvalHeaderDto.getSearchDtmTo();
        String docTitleNm = approvalHeaderDto.getDocTitleNm();
        String docStatCd = approvalHeaderDto.getDocStatCd();
        String draftUserId = approvalHeaderDto.getDraftUserId();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT HD.DOC_TYPE_CD" + "       ,A.DETAIL_NM AS DOC_TYPE" + "       ,HD.DOC_STAT_CD"
                + "       ,B.DETAIL_NM AS DOC_STAT_NM" + "       ,HD.DOC_TITLE_NM" + "       ,C.DETAIL_NM AS APPR_TYPE"
                + "       ,D.DETAIL_NM AS APPR_STATUS" + "       ,HD.APPR_NO" + "       ,HD.DOC_MNG_NO"
                + "       ,HD.DRAFT_ID AS DRAFT_USER_ID" + "       ,E.APRVER_NM AS DRAFT_USER_NAME"
                + "       ,E.APRVER_JOB_NM AS DRAFT_USER_JOB"
                + "       ,DATE_FORMAT(HD.DRAFT_DTM, '%Y-%m-%d') AS DRAFT_DTM" + "       , CASE"
                + "              WHEN SUBSTR(HD.DOC_MNG_NO,1,2) = 'GR'  THEN" + "                   H.TOT_AMT"
                + "              ELSE" + "                   F.TOT_AMT" + "         END AS TOT_AMT"
                + "       ,F.HD_SGTXT" + "       ,DATE_FORMAT(F.ELEC_APPR_DTM, '%Y-%m-%d') AS SLIP_APPR_DTM"
                + "       ,G.REQ_TOT_AMT" + "       ,G.REQ_RSN"
                + "       ,DATE_FORMAT(G.ELEC_APPR_DTM, '%Y-%m-%d') AS BUD_APPR_DTM" + "  FROM TB_APPR_HD HD"
                + "       INNER JOIN TB_APPR_DT DT ON HD.APPR_NO = DT.APPR_NO AND DT.APPR_FG_CD IN ('A', 'R') AND DT.A_APRVER_ID = :loginId"
                + "       LEFT OUTER JOIN TB_CODE_DT A ON A.GROUP_CD = 'DOC_TYPE_CD' AND A.DETAIL_CD = HD.DOC_TYPE_CD"
                + "       LEFT OUTER JOIN TB_CODE_DT B ON B.GROUP_CD = 'DOC_STAT_CD' AND B.DETAIL_CD = HD.DOC_STAT_CD"
                + "       LEFT OUTER JOIN TB_CODE_DT C ON C.GROUP_CD = 'APPR_TYPE_CD' AND C.DETAIL_CD = DT.APPR_TYPE_CD"
                + "       LEFT OUTER JOIN TB_CODE_DT D ON D.GROUP_CD = 'APPR_FG_CD' AND D.DETAIL_CD = DT.APPR_FG_CD"
                + "       LEFT OUTER JOIN TB_APPR_DT E ON E.APPR_NO = HD.APPR_NO AND E.APRVER_ID = HD.DRAFT_ID"
                + "       LEFT OUTER JOIN TB_SLIP_HD F ON F.EA_SLIP_NO = HD.DOC_MNG_NO"
                + "       LEFT OUTER JOIN TB_BUD_HD G ON G.BUD_REQ_NO = HD.DOC_MNG_NO"
                + "       LEFT OUTER JOIN TB_SLIP_GR H ON H.GR_SLIP_NO = HD.DOC_MNG_NO     " +

                " WHERE HD.DOC_STAT_CD IN('ING', 'APR', 'REJ')");

        if (!StringUtils.isEmpty(docTypeCd)) {// 문서유형
            sb.append(" AND HD.DOC_TYPE_CD = :docTypeCd");
        }
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') >= :searchDtmFr");
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') <= :searchDtmTo");
        }
        if (!StringUtils.isEmpty(docStatCd)) {// 문서상태
            sb.append(" AND HD.DOC_STAT_CD = :docStatCd");
        }
        if (!StringUtils.isEmpty(draftUserId)) {// 기안자
            sb.append(" AND HD.DRAFT_ID = :draftUserId");
        }
        if (!StringUtils.isEmpty(docTitleNm)) {// 제목
            sb.append(" AND HD.DOC_TITLE_NM LIKE CONCAT('%', :docTitleNm, '%')");
        }
        sb.append(" ORDER BY HD.APPR_NO DESC");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("loginId", loginId);
        if (!StringUtils.isEmpty(docTypeCd)) {// 문서유형
            query.setParameter("docTypeCd", docTypeCd);
        }
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            query.setParameter("searchDtmFr", searchDtmFr.replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            query.setParameter("searchDtmTo", searchDtmTo.replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty(docStatCd)) {// 문서상태
            query.setParameter("docStatCd", docStatCd);
        }
        if (!StringUtils.isEmpty(draftUserId)) {// 기안자
            query.setParameter("draftUserId", draftUserId);
        }
        if (!StringUtils.isEmpty(docTitleNm)) {// 제목
            query.setParameter("docTitleNm", docTitleNm);
        }

        return new JpaResultMapper().list(query, ApprovalHeaderDto.class);
    }

    @Override
    public List<ApprovalHeaderDto> getApprReqList(ApprovalHeaderDto approvalHeaderDto) {

        /* search Conditions */
        String docTypeCd = approvalHeaderDto.getDocTypeCd();
        String searchDtmFr = approvalHeaderDto.getSearchDtmFr();
        String searchDtmTo = approvalHeaderDto.getSearchDtmTo();
        String docTitleNm = approvalHeaderDto.getDocTitleNm();
        String docStatCd = approvalHeaderDto.getDocStatCd();
        String draftUserId = approvalHeaderDto.getDraftUserId();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT HD.DOC_TYPE_CD" + "       ,A.DETAIL_NM AS DOC_TYPE" + "       ,HD.DOC_STAT_CD"
                + "       ,B.DETAIL_NM AS DOC_STAT_NM" + "       ,HD.DOC_TITLE_NM" + "       ,C.DETAIL_NM AS APPR_TYPE"
                + "       ,D.DETAIL_NM AS APPR_STATUS" + "       ,HD.APPR_NO" + "       ,HD.DOC_MNG_NO"
                + "       ,HD.DRAFT_ID AS DRAFT_USER_ID" + "       ,E.APRVER_NM AS DRAFT_USER_NAME"
                + "       ,E.APRVER_JOB_NM AS DRAFT_USER_JOB"
                + "       ,DATE_FORMAT(HD.DRAFT_DTM, '%Y-%m-%d') AS DRAFT_DTM" + "       , CASE"
                + "              WHEN SUBSTR(HD.DOC_MNG_NO,1,2) = 'GR'  THEN" + "                   H.TOT_AMT"
                + "              ELSE" + "                   F.TOT_AMT" + "         END AS TOT_AMT"
                + "       ,F.HD_SGTXT" + "       ,DATE_FORMAT(F.ELEC_APPR_DTM, '%Y-%m-%d') AS SLIP_APPR_DTM"
                + "       ,G.REQ_TOT_AMT" + "       ,G.REQ_RSN"
                + "       ,DATE_FORMAT(G.ELEC_APPR_DTM, '%Y-%m-%d') AS BUD_APPR_DTM" + "  FROM TB_APPR_HD HD"
                + "       INNER JOIN TB_APPR_DT DT ON HD.APPR_NO = DT.APPR_NO AND DT.APPR_TYPE_CD = '10'"
                + "       LEFT OUTER JOIN TB_CODE_DT A ON A.GROUP_CD = 'DOC_TYPE_CD' AND A.DETAIL_CD = HD.DOC_TYPE_CD"
                + "       LEFT OUTER JOIN TB_CODE_DT B ON B.GROUP_CD = 'DOC_STAT_CD' AND B.DETAIL_CD = HD.DOC_STAT_CD"
                + "       LEFT OUTER JOIN TB_CODE_DT C ON C.GROUP_CD = 'APPR_TYPE_CD' AND C.DETAIL_CD = DT.APPR_TYPE_CD"
                + "       LEFT OUTER JOIN TB_CODE_DT D ON D.GROUP_CD = 'APPR_FG_CD' AND D.DETAIL_CD = DT.APPR_FG_CD"
                + "       LEFT OUTER JOIN TB_APPR_DT E ON E.APPR_NO = HD.APPR_NO AND E.APRVER_ID = HD.DRAFT_ID"
                + "       LEFT OUTER JOIN TB_SLIP_HD F ON F.EA_SLIP_NO = HD.DOC_MNG_NO"
                + "       LEFT OUTER JOIN TB_BUD_HD G ON G.BUD_REQ_NO = HD.DOC_MNG_NO"
                + "       LEFT OUTER JOIN TB_SLIP_GR H ON H.GR_SLIP_NO = HD.DOC_MNG_NO" + " WHERE 1=1 ");

        if (!StringUtils.isEmpty(docTypeCd)) {// 문서유형
            sb.append(" AND HD.DOC_TYPE_CD = :docTypeCd");
        }
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') >= :searchDtmFr");
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') <= :searchDtmTo");
        }
        if (!StringUtils.isEmpty(docStatCd)) {// 문서상태
            sb.append(" AND HD.DOC_STAT_CD = :docStatCd");
        }
        if (!StringUtils.isEmpty(draftUserId)) {// 기안자:로그인한 사용자
            sb.append(" AND HD.DRAFT_ID = :draftUserId");
        }
        if (!StringUtils.isEmpty(docTitleNm)) {// 제목
            sb.append(" AND HD.DOC_TITLE_NM LIKE CONCAT('%', :docTitleNm, '%')");
        }
        sb.append(" ORDER BY HD.APPR_NO DESC");

        Query query = entityManager.createNativeQuery(sb.toString());
        if (!StringUtils.isEmpty(docTypeCd)) {// 문서유형
            query.setParameter("docTypeCd", docTypeCd);
        }
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            query.setParameter("searchDtmFr", searchDtmFr.replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            query.setParameter("searchDtmTo", searchDtmTo.replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty(docStatCd)) {// 문서상태
            query.setParameter("docStatCd", docStatCd);
        }
        if (!StringUtils.isEmpty(draftUserId)) {// 기안자
            query.setParameter("draftUserId", draftUserId);
        }
        if (!StringUtils.isEmpty(docTitleNm)) {// 제목
            query.setParameter("docTitleNm", docTitleNm);
        }

        return new JpaResultMapper().list(query, ApprovalHeaderDto.class);
    }

    @Override
    public List<ApprovalHeaderDto> getApprHeader(String docNo) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT HD.DOC_TYPE_CD"
                + "         , (SELECT DETAIL_NM FROM tb_code_dt WHERE GROUP_CD = 'DOC_TYPE_CD' AND DETAIL_CD = HD.DOC_TYPE_CD) AS DOC_TYPE"
                + "         , HD.DOC_STAT_CD"
                + "         , (SELECT DETAIL_NM FROM tb_code_dt WHERE GROUP_CD = 'DOC_STAT_CD' AND DETAIL_CD = HD.DOC_STAT_CD) AS DOC_STAT_NM"
                + "         , HD.DOC_TITLE_NM AS DOC_TITLE_NM" + "         , '-' AS APPR_TYPE"
                + "         , '-' AS APPR_STATUS" + "         , HD.APPR_NO AS APPR_NO"
                + "         , HD.DOC_MNG_NO AS DOC_MNG_NO" + "         , HD.DRAFT_ID AS DRAFT_USER_ID"
                + "         , (SELECT T.APRVER_NM FROM TB_APPR_DT T WHERE T.APPR_NO = :docNo AND T.APRVER_ID = HD.DRAFT_ID) AS DRAFT_USER_NAME"
                + "         , (SELECT T.APRVER_JOB_NM FROM TB_APPR_DT T WHERE T.APPR_NO = :docNo AND T.APRVER_ID = HD.DRAFT_ID) AS DRAFT_USER_JOB"
                + "         , DATE_FORMAT(HD.DRAFT_DTM, '%Y-%m-%d') AS DRAFT_DTM" + "    FROM TB_APPR_HD HD"
                + "   WHERE HD.APPR_NO = :docNo");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("docNo", docNo);

        return new JpaResultMapper().list(query, ApprovalHeaderDto.class);
    }

    @Override
    public List<ApprovalDetailDto> getApprDetail(String docNo) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DT.APPR_NO" + "       , DT.APPR_SEQ" + "       , DT.APPR_TYPE_CD"
                + "       , (SELECT DETAIL_NM FROM TB_CODE_DT WHERE GROUP_CD = 'APPR_TYPE_CD' AND DETAIL_CD = DT.APPR_TYPE_CD) AS APPR_TYPE"
                + "       , DT.APRVER_ID"
                + "       , CONCAT(DT.APRVER_NM, ' ', DT.APRVER_JOB_NM, ' / ', DT.APRVER_DEPT_NM) AS APRVER_USER"
                + "       , DT.A_APRVER_ID"
                + "       , CONCAT(DT.A_APRVER_NM, ' ', DT.A_APRVER_JOB_NM, ' / ', DT.A_APRVER_DEPT_NM) AS A_APRVER_USER"
                + "       , DT.APPR_FG_CD AS APPR_FG_CD" + "       , CASE DT.APPR_FG_CD WHEN 'X' THEN ''"
                + "           ELSE (SELECT DETAIL_NM FROM TB_CODE_DT WHERE GROUP_CD = 'APPR_FG_CD' AND DETAIL_CD = DT.APPR_FG_CD)"
                + "         END AS APPR_STATUS" + "       , DATE_FORMAT(DT.APPR_DTM, '%Y%m%d') AS APPR_DTM"
                + "       , APPR_DESC" + "  FROM TB_APPR_DT DT" + " WHERE DT.APPR_NO = :docNo");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("docNo", docNo);

        return new JpaResultMapper().list(query, ApprovalDetailDto.class);
    }

    @Override
    public List<ApprovalEmployeeDto> getApprovalEmpList() {

        StringBuilder sb = new StringBuilder();
        sb.append("" + " WITH RECURSIVE cte (CCTR_CD, CCTR_NM, UPPER_CD, LVL_CD, DEPTH_NM) AS " + " ( "
                + " SELECT CCTR_CD " + "        , CCTR_NM " + "        , UPPER_CD " + "        , LVL_CD "
                + "        , CCTR_NM AS DEPTH_NM" + "   FROM tb_mst_cctr" + "  WHERE UPPER_CD = ''" + " "
                + "  UNION ALL " + " " + " SELECT r.CCTR_CD " + "        , r.CCTR_NM " + "        , r.UPPER_CD "
                + "        , r.LVL_CD " + "        , CASE r.LVL_CD" + "            WHEN '3'"
                + "            THEN CONCAT(cte.DEPTH_NM, '-', LENGTH(CAST(SUBSTRING(r.CCTR_CD, 2, LENGTH(r.CCTR_CD)-1) AS UNSIGNED INTEGER)), '-', CAST(CAST(SUBSTRING(r.CCTR_CD, 2, LENGTH(r.CCTR_CD)-1) AS UNSIGNED INTEGER) AS CHAR), '-', r.CCTR_NM)"
                + "        WHEN '4'"
                + "            THEN CONCAT(cte.DEPTH_NM, '-', CAST(CAST(SUBSTRING(r.CCTR_CD, 2, LENGTH(r.CCTR_CD)-1) AS UNSIGNED INTEGER) AS CHAR), '-', r.CCTR_NM)"
                + "        ELSE CONCAT(cte.DEPTH_NM, '-', r.CCTR_NM)" + "      END DEPTH_NM" + "   FROM tb_mst_cctr r"
                + "  INNER JOIN cte ON" + "        r.UPPER_CD = cte.CCTR_CD " + " ) " + " SELECT DEPT.LVL_CD"
                + "        , DEPT.CCTR_CD" + "        , DEPT.CCTR_NM" + "        , DEPT.UPPER_CD"
                + "        , EMP.EMP_NO" + "        , EMP.EMP_NM" + "        , EMP.JOB_CD" + "        , EMP.JOB_NM"
                + "        , EMP.DUT_CD" + "        , EMP.DUT_NM" + "        , DEPT.DEPTH_NM"
                + "   FROM cte DEPT, TB_MST_EMP EMP" + "  WHERE DEPT.CCTR_CD = EMP.CCTR_CD"
                + "  ORDER BY DEPT.DEPTH_NM " + "          ,(CASE EMP.JOB_NM" + "              WHEN '팀장' THEN 1"
                + "              WHEN '파트장' THEN 2" + "              WHEN '팀원' THEN 3" + "              ELSE 4"
                + "            END) " + "          , EMP.EMP_NM");

        Query query = entityManager.createNativeQuery(sb.toString());

        return new JpaResultMapper().list(query, ApprovalEmployeeDto.class);
    }

    @Override
    public List<ApprovalDelegateDto> getApprovalDlgtList(ApprovalDelegateDto approvalDelegateDto) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " + "    DL.ADLG_SEQ" + "    , DL.ADLG_ID"
                + "    , (SELECT CONCAT(EMP.EMP_NM, ' / ', EMP.DEPT_NM) FROM TB_MST_EMP EMP WHERE EMP.EMP_NO = DL.ADLG_ID) AS ADLG_NM"
                + "    , DL.ACT_ID"
                + "    , (SELECT CONCAT(EMP.EMP_NM, ' / ', EMP.DEPT_NM) FROM TB_MST_EMP EMP WHERE EMP.EMP_NO = DL.ACT_ID) AS ACT_NM"
                + "    , DL.COMP_CD" + "    , DL.ADLG_STAT_CD"
                + "    , (SELECT CD.DETAIL_NM FROM TB_CODE_DT CD WHERE CD.GROUP_CD = 'ADLG_STAT_CD' AND CD.DETAIL_CD = DL.ADLG_STAT_CD) AS ADLG_STAT_NM"
                + "    , DL.ADLG_STR_DT" + "    , DL.ADLG_END_DT" + "    , DL.ADLG_RSN" + "    , DL.ADLG_EXE_DTM"
                + "    , DL.ADLG_RVC_DTM" + "      FROM TB_APPR_DELEGATE DL" + "     WHERE 1=1");
        if (!StringUtils.isEmpty(approvalDelegateDto.getCompCd())) {
            sb.append(" AND DL.COMP_CD = :compCd");
        }
        if (!StringUtils.isEmpty(approvalDelegateDto.getAdlgStrDt())) {
            sb.append(" AND DL.ADLG_STR_DT >= :adlgStrDt");
        }
        if (!StringUtils.isEmpty(approvalDelegateDto.getAdlgEndDt())) {
            sb.append(" AND DL.ADLG_END_DT <= :adlgEndDt");
        }
        if (!StringUtils.isEmpty((approvalDelegateDto.getAdlgStatCd()))) {
            sb.append(" AND DL.ADLG_STAT_CD = :adlgStatCd");
        }
        if (!StringUtils.isEmpty(approvalDelegateDto.getAdlgId())) {
            sb.append(" AND DL.ADLG_ID = :adlgId");
        }
        if (!StringUtils.isEmpty(approvalDelegateDto.getActId())) {
            sb.append(" AND DL.ACT_ID = :actId");
        }

        Query query = entityManager.createNativeQuery(sb.toString());
        if (!StringUtils.isEmpty(approvalDelegateDto.getCompCd())) {
            query.setParameter("compCd", approvalDelegateDto.getCompCd());
        }
        if (!StringUtils.isEmpty(approvalDelegateDto.getAdlgStrDt())) {
            query.setParameter("adlgStrDt", approvalDelegateDto.getAdlgStrDt().replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty(approvalDelegateDto.getAdlgEndDt())) {
            query.setParameter("adlgEndDt", approvalDelegateDto.getAdlgEndDt().replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty((approvalDelegateDto.getAdlgStatCd()))) {
            query.setParameter("adlgStatCd", approvalDelegateDto.getAdlgStatCd());
        }
        if (!StringUtils.isEmpty(approvalDelegateDto.getAdlgId())) {
            query.setParameter("adlgId", approvalDelegateDto.getAdlgId());
        }
        if (!StringUtils.isEmpty(approvalDelegateDto.getActId())) {
            query.setParameter("actId", approvalDelegateDto.getActId());
        }

        return new JpaResultMapper().list(query, ApprovalDelegateDto.class);
    }

    @Override
    public List<ApprovalRuleDto> getApprRuleList(ApprovalRuleDto approvalRuleDto) {

        StringBuilder sb = new StringBuilder();
        sb.append("" +
                "SELECT" +
                "    R.COMP_CD ," +
                "    R.DOC_TYPE_CD ," +
                "    (SELECT t.DETAIL_NM" +
                "       FROM tb_code_dt t" +
                "      WHERE t.GROUP_CD = 'DOC_TYPE_CD'" +
                "        AND t.DETAIL_CD = R.DOC_TYPE_CD) AS DOC_TYPE_NM ," +
                "    R.DTL_TYPE_CD ," +
                "    (SELECT t.DETAIL_NM" +
                "       FROM tb_code_dt t" +
                "      WHERE t.GROUP_CD =" +
                "        CASE" +
                "            WHEN R.DOC_TYPE_CD = 'SLIP' THEN 'SLIP_TYPE_CD'" +
                "            WHEN R.DOC_TYPE_CD = 'BDGT' THEN 'BRW_TYPE_CD'" +
                "            ELSE ''" +
                "        END" +
                "        AND t.DETAIL_CD = R.DTL_TYPE_CD ) AS DTL_TYPE_NM ," +
                "    R.CUR_CD ," +
                "    (SELECT t.DETAIL_NM" +
                "       FROM tb_code_dt t" +
                "      WHERE t.GROUP_CD = 'CUR_CD'" +
                "        AND t.DETAIL_CD = R.CUR_CD ) AS CUR_NM ," +
                "    R.RULE_SEQ ," +
                "    R.USE_YN ," +
                "    R.MAX_AMT ," +
                "    R.APPR_TYPE_CD ," +
                "    (SELECT t.DETAIL_NM" +
                "       FROM tb_code_dt t" +
                "      WHERE t.GROUP_CD = 'APPR_TYPE_CD'" +
                "        AND t.DETAIL_CD = R.APPR_TYPE_CD ) AS APPR_TYPE_NM ," +
                "    R.FIX_YN ," +
                "    R.APRVER_CLASS_CD ," +
                "    (SELECT t.DETAIL_NM" +
                "       FROM tb_code_dt t" +
                "      WHERE t.GROUP_CD = 'APRVER_CLASS_CD'" +
                "        AND t.DETAIL_CD = R.APRVER_CLASS_CD ) AS APRVER_CLASS_NM ," +
                "    R.APRVER_CLASS_VAL ," +
                "    R.REMARK" +
                "  FROM TB_APPR_RULE R" +
                " WHERE 1 = 1");
        if (!StringUtils.isEmpty(approvalRuleDto.getCompCd())) {
            sb.append("  AND R.COMP_CD = :compCd");
        }
        if (!StringUtils.isEmpty(approvalRuleDto.getDocTypeCd())) {
            sb.append("  AND R.DOC_TYPE_CD = :docTypeCd");
        }
        if (!StringUtils.isEmpty(approvalRuleDto.getDtlTypeCd())) {
            sb.append("  AND R.DTL_TYPE_CD = :dtlTypeCd");
        }
        if (!StringUtils.isEmpty(approvalRuleDto.getUseYn())) {
            sb.append("  AND R.USE_YN = :useYn");
        }

        sb.append(" ORDER BY R.DOC_TYPE_CD, R.DTL_TYPE_CD, R.CUR_CD, R.RULE_SEQ");

        Query query = entityManager.createNativeQuery(sb.toString());
        if (!StringUtils.isEmpty(approvalRuleDto.getCompCd())) {
            query.setParameter("compCd", approvalRuleDto.getCompCd());
        }
        if (!StringUtils.isEmpty(approvalRuleDto.getDocTypeCd())) {
            query.setParameter("docTypeCd", approvalRuleDto.getDocTypeCd());
        }
        if (!StringUtils.isEmpty(approvalRuleDto.getDtlTypeCd())) {
            query.setParameter("dtlTypeCd", approvalRuleDto.getDtlTypeCd());
        }
        if (!StringUtils.isEmpty(approvalRuleDto.getUseYn())) {
            query.setParameter("useYn", approvalRuleDto.getUseYn());
        }

        return new JpaResultMapper().list(query, ApprovalRuleDto.class);
    }

    @Override
    public List<ApprovalHeaderDto> getApprIngList(ApprovalHeaderDto approvalHeaderDto) {
        String loginId = approvalHeaderDto.getLoginId();

        String searchDtmFr = approvalHeaderDto.getSearchDtmFr();
        String searchDtmTo = approvalHeaderDto.getSearchDtmTo();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT" + "    HD.DOC_TYPE_CD ," + "    A.DETAIL_NM AS DOC_TYPE ," + "    HD.DOC_STAT_CD ,"
                + "    B.DETAIL_NM AS DOC_STAT_NM ," + "    HD.DOC_TITLE_NM ," + "    C.DETAIL_NM AS APPR_TYPE ,"
                + "    D.DETAIL_NM AS APPR_STATUS ," + "    HD.APPR_NO ," + "    HD.DOC_MNG_NO ,"
                + "    HD.DRAFT_ID AS DRAFT_USER_ID ," + "    E.APRVER_NM AS DRAFT_USER_NAME ,"
                + "    E.APRVER_JOB_NM AS DRAFT_USER_JOB ," + "    DATE_FORMAT(HD.DRAFT_DTM, '%Y-%m-%d') AS DRAFT_DTM ,"
                + "    F.TOT_AMT ," + "    F.HD_SGTXT ," + "    F.ELEC_APPR_DTM AS SLIP_APPR_DTM ,"
                + "    G.REQ_TOT_AMT ," + "    G.REQ_RSN ," + "    G.ELEC_APPR_DTM AS BUD_APPR_DTM" + "  FROM"
                + "    TB_APPR_HD HD"
                + "    LEFT OUTER JOIN TB_APPR_DT DT ON HD.APPR_NO = DT.APPR_NO AND DT.APPR_FG_CD IN ('A') AND DT.A_APRVER_ID = :loginId"
                + "    LEFT OUTER JOIN TB_CODE_DT A ON A.GROUP_CD = 'DOC_TYPE_CD' AND A.DETAIL_CD = HD.DOC_TYPE_CD"
                + "    LEFT OUTER JOIN TB_CODE_DT B ON B.GROUP_CD = 'DOC_STAT_CD' AND B.DETAIL_CD = HD.DOC_STAT_CD"
                + "    LEFT OUTER JOIN TB_CODE_DT C ON C.GROUP_CD = 'APPR_TYPE_CD' AND C.DETAIL_CD = DT.APPR_TYPE_CD"
                + "    LEFT OUTER JOIN TB_CODE_DT D ON D.GROUP_CD = 'APPR_FG_CD' AND D.DETAIL_CD = DT.APPR_FG_CD"
                + "    LEFT OUTER JOIN TB_APPR_DT E ON E.APPR_NO = HD.APPR_NO AND E.APRVER_ID = HD.DRAFT_ID"
                + "    LEFT OUTER JOIN TB_SLIP_HD F ON F.EA_SLIP_NO = HD.DOC_MNG_NO"
                + "    LEFT OUTER JOIN TB_BUD_HD G ON G.BUD_REQ_NO = HD.DOC_MNG_NO" + " WHERE HD.DOC_STAT_CD = 'ING'");// 결재진행
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') >= :searchDtmFr");
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') <= :searchDtmTo");
        }

        sb.append(" ORDER BY HD.DOC_MNG_NO");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("loginId", loginId);
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            query.setParameter("searchDtmFr", searchDtmFr.replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            query.setParameter("searchDtmTo", searchDtmTo.replaceAll("-", ""));
        }

        return new JpaResultMapper().list(query, ApprovalHeaderDto.class);
    }

    @Override
    public List<ApprovalHeaderDto> getApprAprList(ApprovalHeaderDto approvalHeaderDto) {
        /*
         * 결재진행된 것들 중에서 결재완료된 문서 getApprIngList() -> getApprAprList()
         * TB_APPR_DT.APPR_FG_CD IN ('A', 'R') TB_APPR_HD.DOC_STAT_CD IN ('APR', 'REJ')
         */

        String loginId = approvalHeaderDto.getLoginId();

        String searchDtmFr = approvalHeaderDto.getSearchDtmFr();
        String searchDtmTo = approvalHeaderDto.getSearchDtmTo();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT" + "    HD.DOC_TYPE_CD ," + "    A.DETAIL_NM AS DOC_TYPE ," + "    HD.DOC_STAT_CD ,"
                + "    B.DETAIL_NM AS DOC_STAT_NM ," + "    HD.DOC_TITLE_NM ," + "    C.DETAIL_NM AS APPR_TYPE ,"
                + "    D.DETAIL_NM AS APPR_STATUS ," + "    HD.APPR_NO ," + "    HD.DOC_MNG_NO ,"
                + "    HD.DRAFT_ID AS DRAFT_USER_ID ," + "    E.APRVER_NM AS DRAFT_USER_NAME ,"
                + "    E.APRVER_JOB_NM AS DRAFT_USER_JOB ," + "    DATE_FORMAT(HD.DRAFT_DTM, '%Y-%m-%d') AS DRAFT_DTM ,"
                + "    F.TOT_AMT ," + "    F.HD_SGTXT ," + "    F.ELEC_APPR_DTM AS SLIP_APPR_DTM ,"
                + "    G.REQ_TOT_AMT ," + "    G.REQ_RSN ," + "    G.ELEC_APPR_DTM AS BUD_APPR_DTM" + "  FROM"
                + "    TB_APPR_HD HD"
                + "    LEFT OUTER JOIN TB_APPR_DT DT ON HD.APPR_NO = DT.APPR_NO AND DT.APPR_FG_CD IN ('A', 'R') AND DT.A_APRVER_ID = :loginId"
                + "    LEFT OUTER JOIN TB_CODE_DT A ON A.GROUP_CD = 'DOC_TYPE_CD' AND A.DETAIL_CD = HD.DOC_TYPE_CD"
                + "    LEFT OUTER JOIN TB_CODE_DT B ON B.GROUP_CD = 'DOC_STAT_CD' AND B.DETAIL_CD = HD.DOC_STAT_CD"
                + "    LEFT OUTER JOIN TB_CODE_DT C ON C.GROUP_CD = 'APPR_TYPE_CD' AND C.DETAIL_CD = DT.APPR_TYPE_CD"
                + "    LEFT OUTER JOIN TB_CODE_DT D ON D.GROUP_CD = 'APPR_FG_CD' AND D.DETAIL_CD = DT.APPR_FG_CD"
                + "    LEFT OUTER JOIN TB_APPR_DT E ON E.APPR_NO = HD.APPR_NO AND E.APRVER_ID = HD.DRAFT_ID"
                + "    LEFT OUTER JOIN TB_SLIP_HD F ON F.EA_SLIP_NO = HD.DOC_MNG_NO"
                + "    LEFT OUTER JOIN TB_BUD_HD G ON G.BUD_REQ_NO = HD.DOC_MNG_NO"
                + " WHERE HD.DOC_STAT_CD IN ('APR', 'REJ') ");
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') >= :searchDtmFr");
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') <= :searchDtmTo");
        }

        sb.append(" ORDER BY HD.DOC_MNG_NO");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("loginId", loginId);
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            query.setParameter("searchDtmFr", searchDtmFr.replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            query.setParameter("searchDtmTo", searchDtmTo.replaceAll("-", ""));
        }

        return new JpaResultMapper().list(query, ApprovalHeaderDto.class);
    }

    @Override
    public List<ApprovalHeaderDto> getApprRejList(ApprovalHeaderDto approvalHeaderDto) {

        String loginId = approvalHeaderDto.getLoginId();

        String searchDtmFr = approvalHeaderDto.getSearchDtmFr();
        String searchDtmTo = approvalHeaderDto.getSearchDtmTo();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT" + "    HD.DOC_TYPE_CD ," + "    A.DETAIL_NM AS DOC_TYPE ," + "    HD.DOC_STAT_CD ,"
                + "    B.DETAIL_NM AS DOC_STAT_NM ," + "    HD.DOC_TITLE_NM ," + "    C.DETAIL_NM AS APPR_TYPE ,"
                + "    D.DETAIL_NM AS APPR_STATUS ," + "    HD.APPR_NO ," + "    HD.DOC_MNG_NO ,"
                + "    HD.DRAFT_ID AS DRAFT_USER_ID ," + "    E.APRVER_NM AS DRAFT_USER_NAME ,"
                + "    E.APRVER_JOB_NM AS DRAFT_USER_JOB ," + "    DATE_FORMAT(HD.DRAFT_DTM, '%Y-%m-%d') AS DRAFT_DTM ,"
                + "    F.TOT_AMT ," + "    F.HD_SGTXT ," + "    F.ELEC_APPR_DTM AS SLIP_APPR_DTM ,"
                + "    G.REQ_TOT_AMT ," + "    G.REQ_RSN ," + "    G.ELEC_APPR_DTM AS BUD_APPR_DTM" + "  FROM"
                + "    TB_APPR_HD HD"
                + "    LEFT OUTER JOIN TB_APPR_DT DT ON HD.APPR_NO = DT.APPR_NO AND DT.APPR_FG_CD IN ('A', 'R')  AND DT.A_APRVER_ID = :loginId"
                + "    LEFT OUTER JOIN TB_CODE_DT A ON A.GROUP_CD = 'DOC_TYPE_CD' AND A.DETAIL_CD = HD.DOC_TYPE_CD"
                + "    LEFT OUTER JOIN TB_CODE_DT B ON B.GROUP_CD = 'DOC_STAT_CD' AND B.DETAIL_CD = HD.DOC_STAT_CD"
                + "    LEFT OUTER JOIN TB_CODE_DT C ON C.GROUP_CD = 'APPR_TYPE_CD' AND C.DETAIL_CD = DT.APPR_TYPE_CD"
                + "    LEFT OUTER JOIN TB_CODE_DT D ON D.GROUP_CD = 'APPR_FG_CD' AND D.DETAIL_CD = DT.APPR_FG_CD"
                + "    LEFT OUTER JOIN TB_APPR_DT E ON E.APPR_NO = HD.APPR_NO AND E.APRVER_ID = HD.DRAFT_ID"
                + "    LEFT OUTER JOIN TB_SLIP_HD F ON F.EA_SLIP_NO = HD.DOC_MNG_NO"
                + "    LEFT OUTER JOIN TB_BUD_HD G ON G.BUD_REQ_NO = HD.DOC_MNG_NO" + " WHERE HD.DOC_STAT_CD = 'REJ'");
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') >= :searchDtmFr");
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            sb.append(" AND DATE_FORMAT(HD.DRAFT_DTM, '%Y%m%d') <= :searchDtmTo");
        }

        sb.append(" ORDER BY HD.DOC_MNG_NO");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("loginId", loginId);
        if (!StringUtils.isEmpty(searchDtmFr)) {// 기안일자
            query.setParameter("searchDtmFr", searchDtmFr.replaceAll("-", ""));
        }
        if (!StringUtils.isEmpty(searchDtmTo)) {
            query.setParameter("searchDtmTo", searchDtmTo.replaceAll("-", ""));
        }

        return new JpaResultMapper().list(query, ApprovalHeaderDto.class);
    }

    @Override
    public List<ApprovalEmployeeDto> getApprovalDeptTreeList() {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte (CCTR_CD, CCTR_NM, UPPER_CD, LVL_CD, DEPTH_NM) AS " + "    ( "
                + "    SELECT CCTR_CD " + "           , CCTR_NM " + "           , UPPER_CD " + "           , LVL_CD "
                + "           , CCTR_NM AS DEPTH_NM" + "    FROM tb_mst_cctr" + "    WHERE UPPER_CD = ''"
                + "    UNION ALL " + "    SELECT r.CCTR_CD " + "           , r.CCTR_NM " + "           , r.UPPER_CD "
                + "           , r.LVL_CD " + "           , CASE r.LVL_CD" + "               WHEN '3'"
                + "                   THEN CONCAT(cte.DEPTH_NM, '-', LENGTH(CAST(SUBSTRING(r.CCTR_CD, 2, LENGTH(r.CCTR_CD)-1) AS UNSIGNED INTEGER)), '-', CAST(CAST(SUBSTRING(r.CCTR_CD, 2, LENGTH(r.CCTR_CD)-1) AS UNSIGNED INTEGER) AS CHAR), '-', r.CCTR_NM)"
                + "               WHEN '4'"
                + "                   THEN CONCAT(cte.DEPTH_NM, '-', CAST(CAST(SUBSTRING(r.CCTR_CD, 2, LENGTH(r.CCTR_CD)-1) AS UNSIGNED INTEGER) AS CHAR), '-', r.CCTR_NM)"
                + "               ELSE CONCAT(cte.DEPTH_NM, '-', r.CCTR_NM)" + "             END DEPTH_NM"
                + "    FROM tb_mst_cctr r" + "    INNER JOIN cte ON" + "        r.UPPER_CD = cte.CCTR_CD " + "    ) "
                + " SELECT DEPT.LVL_CD , DEPT.CCTR_CD , DEPT.CCTR_NM , DEPT.UPPER_CD , DEPT.DEPTH_NM" + " FROM cte DEPT"
                + " ORDER BY DEPT.DEPTH_NM");
        Query query = entityManager.createNativeQuery(sb.toString());

        return new JpaResultMapper().list(query, ApprovalEmployeeDto.class);
    }

    @Override
    public List<ApprovalRuleDto> getApprRuleLines(String loginId, ApprovalRuleDto approvalRuleDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("" + "SELECT T.*" + "  FROM (" + "    SELECT AR.RULE_SEQ," + "           AR.MAX_AMT,"
                + "           AR.APPR_TYPE_CD," + "           DT.DETAIL_NM AS APPR_TYPE," + "           AR.FIX_YN,"
                + "           AR.APRVER_CLASS_CD," + "           AR.APRVER_CLASS_VAL," + "           CASE"
                + "               WHEN AR.APRVER_CLASS_CD = 'Dept' THEN (SELECT EMP_NO FROM TB_MST_EMP WHERE CCTR_CD = AR.APRVER_CLASS_VAL AND JOB_CD LIKE '%장')"
                + "               WHEN AR.APRVER_CLASS_CD = 'Emp' THEN AR.APRVER_CLASS_VAL"
                + "           ELSE A.EMP_NO END APRVER_ID," + "           CASE"
                + "               WHEN AR.APRVER_CLASS_CD = 'Dept' THEN (SELECT EMP_NM FROM TB_MST_EMP WHERE CCTR_CD = AR.APRVER_CLASS_VAL AND JOB_CD LIKE '%장')"
                + "               WHEN AR.APRVER_CLASS_CD = 'Emp' THEN (SELECT EMP_NM FROM TB_MST_EMP WHERE EMP_NO = AR.APRVER_CLASS_VAL)"
                + "             ELSE A.EMP_NM END APRVER_USER," + "             CASE"
                + "               WHEN AR.APRVER_CLASS_CD = 'Dept' THEN (SELECT JOB_NM FROM TB_MST_EMP WHERE CCTR_CD = AR.APRVER_CLASS_VAL AND JOB_CD LIKE '%장')"
                + "               WHEN AR.APRVER_CLASS_CD = 'Emp' THEN (SELECT JOB_NM FROM TB_MST_EMP WHERE EMP_NO = AR.APRVER_CLASS_VAL)"
                + "             ELSE A.JOB_NM END JOB_NM," + "             CASE"
                + "               WHEN AR.APRVER_CLASS_CD = 'Dept' THEN (SELECT DEPT_NM FROM TB_MST_EMP WHERE CCTR_CD = AR.APRVER_CLASS_VAL AND JOB_CD LIKE '%장')"
                + "               WHEN AR.APRVER_CLASS_CD = 'Emp' THEN (SELECT DEPT_NM FROM TB_MST_EMP WHERE EMP_NO = AR.APRVER_CLASS_VAL)"
                + "           ELSE A.DEPT_NM END CCTR_NM" + "      FROM TB_APPR_RULE AR"
                + "           LEFT OUTER JOIN TB_CODE_DT DT ON DT.COMP_CD = AR.COMP_CD AND DT.GROUP_CD = 'APPR_TYPE_CD' AND DT.USE_YN = 'Y' AND DT.DETAIL_CD = AR.APPR_TYPE_CD"
                + "           LEFT OUTER JOIN (" + "           SELECT CC.CCTR_CD," + "                  CC.UPPER_CD,"
                + "                  CC.ORG_FG_CD," + "                  EM.EMP_NO," + "                  EM.EMP_NM,"
                + "                  EM.JOB_NM," + "                  EM.DEPT_NM"
                + "             FROM TB_MST_CCTR CC, TB_MST_EMP EM" + "            WHERE CC.CCTR_CD = (SELECT CCTR_CD"
                + "                                  FROM TB_MST_EMP"
                + "                                 WHERE EMP_NO = :loginId)" + "              AND CC.UPPER_CD <> ''"
                + "              AND EM.CCTR_CD = CC.CCTR_CD" + "              AND EM.JOB_CD LIKE '%장'"
                + "           UNION ALL" + "           SELECT CC.CCTR_CD," + "                  CC.UPPER_CD,"
                + "                  CC.ORG_FG_CD," + "                  EM.EMP_NO," + "                  EM.EMP_NM,"
                + "                  EM.JOB_NM," + "                  EM.DEPT_NM"
                + "             FROM TB_MST_CCTR CC, TB_MST_EMP EM"
                + "            WHERE CC.CCTR_CD = (SELECT A.UPPER_CD"
                + "                                  FROM TB_MST_CCTR A"
                + "                                 WHERE A.CCTR_CD = (SELECT CCTR_CD"
                + "                                                      FROM TB_MST_EMP"
                + "                                                     WHERE EMP_NO = :loginId))"
                + "              AND CC.UPPER_CD <> ''" + "              AND EM.CCTR_CD = CC.CCTR_CD"
                + "              AND EM.JOB_CD LIKE '%장'" + "           UNION ALL" + "           SELECT CC.CCTR_CD,"
                + "                  CC.UPPER_CD," + "                  CC.ORG_FG_CD," + "                  EM.EMP_NO,"
                + "                  EM.EMP_NM," + "                  EM.JOB_NM," + "                  EM.DEPT_NM"
                + "             FROM TB_MST_CCTR CC, TB_MST_EMP EM" + "            WHERE CC.CCTR_CD = ("
                + "                  SELECT B.UPPER_CD" + "                    FROM TB_MST_CCTR B"
                + "                   WHERE B.CCTR_CD = (SELECT A.UPPER_CD"
                + "                                         FROM TB_MST_CCTR A"
                + "                                        WHERE A.CCTR_CD = (SELECT CCTR_CD"
                + "                                                             FROM TB_MST_EMP"
                + "                                                            WHERE EMP_NO = :loginId)))"
                + "              AND CC.UPPER_CD <> ''" + "              AND EM.CCTR_CD = CC.CCTR_CD"
                + "              AND EM.JOB_CD LIKE '%장') A ON A.ORG_FG_CD = AR.APRVER_CLASS_CD" + "     WHERE 1=1"
                + "       AND AR.COMP_CD = :compCd" + "       AND AR.DOC_TYPE_CD = :docTypeCd"
                + "       AND AR.DTL_TYPE_CD = :dtlTypeCd" + "       AND AR.CUR_CD = :curCd"
                + "       AND AR.USE_YN = 'Y'"
                + "       AND ((AR.MAX_AMT <> '0' AND AR.MAX_AMT < IFNULL(:maxAmt, 0)) OR (IFNULL(AR.MAX_AMT, 0) = '0'))"
                + " ) T" + " WHERE T.APRVER_ID <> :loginId" + " ORDER BY T.RULE_SEQ");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("loginId", loginId);
        query.setParameter("compCd", approvalRuleDto.getCompCd());
        query.setParameter("docTypeCd", approvalRuleDto.getDocTypeCd());
        query.setParameter("dtlTypeCd", approvalRuleDto.getDtlTypeCd());
        query.setParameter("curCd", approvalRuleDto.getCurCd());
        query.setParameter("maxAmt", approvalRuleDto.getMaxAmt());

        return new JpaResultMapper().list(query, ApprovalRuleDto.class);
    }
}
