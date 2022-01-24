/* Menu */
CREATE TABLE A_MENU (
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  MENU_NO varchar(10) NOT NULL COMMENT '메뉴순번',
  MENU_NM varchar(60) DEFAULT NULL COMMENT '메뉴명',
  PROGRAM_FILE_NM varchar(60) DEFAULT NULL COMMENT '프로그램명',
  UPPER_MENU_NO varchar(10) DEFAULT NULL COMMENT '상위메뉴명',
  MENU_ORDR int(5) DEFAULT NULL COMMENT '메뉴순서',
  MENU_DC varchar(250) DEFAULT NULL COMMENT '메뉴설명',
  RELATE_IMAGE_PATH varchar(100) DEFAULT NULL COMMENT '연결이미지경로',
  RELATE_IMAGE_NM varchar(60) DEFAULT NULL COMMENT '연결이미지명',
  PRIMARY KEY (COMP_CD,MENU_NO),
  UNIQUE KEY a_menu_comp_cd_idx (COMP_CD,MENU_NO) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

/* Menu by Role */
CREATE TABLE A_MUNE_ROLE (
  ROLE_CD varchar(30) NOT NULL COMMENT '권한코드',
  MENU_NO varchar(10) NOT NULL COMMENT '메뉴순번',
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  PRIMARY KEY (COMP_CD,ROLE_CD,MENU_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

/* Role */
CREATE TABLE A_ROLE (
  COMP_CD varchar(30) NOT NULL COMMENT '회사코드',
  ROLE_CD varchar(30) NOT NULL COMMENT '권한코드',
  ROLE_NM varchar(60) DEFAULT NULL COMMENT '권한명',
  ROLE_SELECT_CD varchar(30) DEFAULT NULL COMMENT '조회권한',
  ROLE_DC varchar(200) DEFAULT NULL COMMENT '권한설명',
  PRIMARY KEY (COMP_CD,ROLE_CD)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

/* User */
CREATE TABLE A_USER (
  ID int(11) NOT NULL AUTO_INCREMENT,
  SESSION_P_ID char(36) DEFAULT NULL,
  LOGIN_ID varchar(30) DEFAULT NULL COMMENT '로그인ID_사번',
  LOGIN_PW varchar(255) NOT NULL COMMENT '로그인PW',
  COMP_CD varchar(10) DEFAULT NULL COMMENT '회사코드',
  DEPT_CD varchar(10) DEFAULT NULL COMMENT '부서코드',
  EMPLOYEE_NO varchar(30) DEFAULT NULL COMMENT 'employee no',
  USER_NAME varchar(80) DEFAULT NULL COMMENT '사용자명',
  ENABLE_FLAG tinyint(1) DEFAULT 1,
  ATTRIBUTE_1 varchar(255) DEFAULT NULL,
  ATTRIBUTE_2 varchar(30) DEFAULT NULL,
  ATTRIBUTE_3 varchar(30) DEFAULT NULL,
  ATTRIBUTE_4 varchar(30) DEFAULT NULL,
  ATTRIBUTE_5 varchar(30) DEFAULT NULL,
  CREATED_BY int(11) DEFAULT NULL COMMENT '생성자',
  CREATION_DATE datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  MODIFIED_BY int(11) DEFAULT NULL COMMENT '수정자',
  MODIFIED_DATE datetime DEFAULT NULL ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (id),
  UNIQUE KEY A_USER_login_id_uindex (login_id),
  KEY A_USER_FK1 (created_by),
  KEY A_USER_FK2 (modified_by),
  KEY A_USER_FK3 (session_p_id),
  CONSTRAINT A_USER_FK1 FOREIGN KEY (CREATED_BY) REFERENCES A_USER (id),
  CONSTRAINT A_USER_FK2 FOREIGN KEY (MODIFIED_BY) REFERENCES A_USER (id),
  CONSTRAINT A_USER_FK3 FOREIGN KEY (SESSION_P_ID) REFERENCES SPRING_SESSION (PRIMARY_ID)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='사용자'
;

/* USER Personal Menu */
CREATE TABLE A_USER_MENU (
  USER_ID varchar(10) NOT NULL COMMENT '사원번호',
  MENU_NO varchar(10) NOT NULL COMMENT '메뉴순번',
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  MENU_ORDR int(5) DEFAULT NULL COMMENT '메뉴순서',
  MENU_ICON_CD varchar(30) DEFAULT NULL COMMENT '메뉴아이콘코드',
  PRIMARY KEY (COMP_CD,USER_ID,MENU_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

/* USER ROLE */
CREATE TABLE A_USER_ROLE (
  ID int(11) NOT NULL COMMENT 'ID',
  USER_ID int(11) DEFAULT NULL COMMENT 'A_USER.ID',
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  ROLE varchar(30) NOT NULL COMMENT '권한',
  CREATED_BY int(11) DEFAULT NULL COMMENT '생성자',
  CREATION_DATE datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  MODIFIED_BY int(11) DEFAULT NULL COMMENT '수정자',
  MODIFIED_DATE datetime DEFAULT NULL ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (id,comp_cd),
  KEY A_USER_ROLE_FK (user_id),
  CONSTRAINT A_USER_ROLE_FK FOREIGN KEY (USER_ID) REFERENCES A_USER (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자_권한'
;

/* OAUTH2.0 */
CREATE TABLE A_OAUTH (
  ID int(11) NOT NULL AUTO_INCREMENT,
  EMP_NO varchar(100) NOT NULL,
  EMAIL varchar(100) NOT NULL,
  NAME varchar(100) DEFAULT NULL,
  PICTURE varchar(100) DEFAULT NULL,
  TYPE varchar(100) DEFAULT NULL,
  CREATE_DATE datetime DEFAULT NULL,
  MODIFIED_DATE datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8
;

/* Spring Session */
CREATE TABLE SPRING_SESSION (
  PRIMARY_ID char(36) NOT NULL,
  SESSION_ID char(36) NOT NULL,
  CREATION_TIME bigint(20) NOT NULL,
  LAST_ACCESS_TIME bigint(20) NOT NULL,
  MAX_INACTIVE_INTERVAL int(11) NOT NULL,
  EXPIRY_TIME bigint(20) NOT NULL,
  PRINCIPAL_NAME varchar(100) DEFAULT NULL,
  PRIMARY KEY (PRIMARY_ID),
  UNIQUE KEY SPRING_SESSION_IX1 (SESSION_ID),
  KEY SPRING_SESSION_IX2 (EXPIRY_TIME),
  KEY SPRING_SESSION_IX3 (PRINCIPAL_NAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC
;

/* Spring Session Attributes */
CREATE TABLE SPRING_SESSION_ATTRIBUTES (
  SESSION_PRIMARY_ID char(36) NOT NULL,
  ATTRIBUTE_NAME varchar(200) NOT NULL,
  ATTRIBUTE_BYTES blob NOT NULL,
  PRIMARY KEY (SESSION_PRIMARY_ID,ATTRIBUTE_NAME),
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES spring_session (PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC
;

/* File */
CREATE TABLE A_FILE (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  COMP_CD varchar(10) DEFAULT NULL COMMENT '회사코드',
  FILE_TYPE varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '관리구분(예시:모바일,전표,예산)',
  DOCUMENT_H_ID varchar(20) DEFAULT NULL COMMENT '문서관리번호(예시:전표,예산)',
  REMARK varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '파일설명',
  SEQ int(11) DEFAULT NULL COMMENT '파일순서',
  ORIGINAL_NAME varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '원본파일명',
  STORED_NAME varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '저장파일명',
  DOWNLOAD_URL varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '파일경로',
  FILE_CAT varchar(30) DEFAULT NULL COMMENT '파일종류',
  FILE_SIZE int(11) DEFAULT NULL COMMENT '용량(Byte)',
  ATTRIBUTE_1 varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '썸네일_저장파일명',
  ATTRIBUTE_2 varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '썸네일_파일경로',
  ATTRIBUTE_3 varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '원본 관리구분(모바일)',
  ATTRIBUTE_4 varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '비고4',
  ATTRIBUTE_5 varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '비고5',
  CREATED_BY int(11) DEFAULT NULL COMMENT '생성자',
  CREATION_DATE datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  MODIFIED_BY int(11) DEFAULT NULL COMMENT '수정자',
  MODIFIED_DATE datetime DEFAULT NULL ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (ID),
  UNIQUE KEY U_FILE_STORED_NAME_UINDEX (STORED_NAME),
  KEY U_FILE_FK1 (DOCUMENT_H_ID),
  KEY U_FILE_FK2 (created_by),
  KEY U_FILE_FK3 (modified_by)
) ENGINE=InnoDB AUTO_INCREMENT=1653 DEFAULT CHARSET=utf8 COMMENT='파일'
;


/* ================================================================================================================== */

/* 기준코드 Header */
CREATE TABLE TB_CODE_HD (
  GROUP_CD varchar(30) NOT NULL COMMENT '그룹코드',
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  GROUP_NM varchar(100) DEFAULT NULL COMMENT '그룹코드명',
  GROUP_DESC varchar(200) DEFAULT NULL COMMENT '그룹코드설명',
  USE_YN varchar(1) DEFAULT NULL COMMENT '사용여부',
  REMARK varchar(200) DEFAULT NULL COMMENT '비고',
  REG_ID varchar(20) DEFAULT NULL COMMENT '등록자ID',
  REG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일시',
  CHG_ID varchar(20) DEFAULT NULL COMMENT '수정자ID',
  CHG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (GROUP_CD,COMP_CD),
  UNIQUE KEY PK_TB_CODE_HD (GROUP_CD,COMP_CD)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공통코드그룹'
;

/* 기준코드 Detail */
CREATE TABLE TB_CODE_DT (
  GROUP_CD varchar(30) NOT NULL COMMENT '그룹코드',
  DETAIL_CD varchar(30) NOT NULL COMMENT '상세코드',
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  DETAIL_NM varchar(100) DEFAULT NULL COMMENT '상세코드명',
  USE_YN varchar(1) DEFAULT NULL COMMENT '사용여부',
  ORDER_SEQ int(11) DEFAULT NULL COMMENT '정렬순서',
  DETAIL_DESC varchar(1000) DEFAULT NULL COMMENT '설명',
  REMARK1 varchar(200) DEFAULT NULL COMMENT '비고1',
  REMARK2 varchar(200) DEFAULT NULL COMMENT '비고2',
  REMARK3 varchar(200) DEFAULT NULL COMMENT '비고3',
  REMARK4 varchar(200) DEFAULT NULL COMMENT '비고4',
  REMARK5 varchar(200) DEFAULT NULL COMMENT '비고5',
  REG_ID varchar(20) DEFAULT NULL COMMENT '등록자ID',
  REG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일시',
  CHG_ID varchar(20) DEFAULT NULL COMMENT '수정자ID',
  CHG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
  UNIQUE KEY PK_TB_CODE_DT (GROUP_CD,DETAIL_CD,COMP_CD)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공통코드상세'
;

/* Employee */
CREATE TABLE TB_MST_EMP (
  EMP_NO varchar(10) NOT NULL COMMENT '사원번호',
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  EMP_NM varchar(50) DEFAULT NULL COMMENT '사원성명',
  COMP_NM varchar(100) DEFAULT NULL COMMENT '회사명',
  DEPT_CD varchar(10) DEFAULT NULL COMMENT '부서코드',
  DEPT_NM varchar(100) DEFAULT NULL COMMENT '부서명',
  CCTR_CD varchar(10) DEFAULT NULL COMMENT '비용부서코드',
  CCTR_NM varchar(100) DEFAULT NULL COMMENT '비용부서명',
  BP_CD varchar(10) DEFAULT NULL COMMENT '사업장코드',
  BP_NM varchar(100) DEFAULT NULL COMMENT '사업장명',
  UPPER_DEPT_CD varchar(10) DEFAULT NULL COMMENT '상위부서코드',
  UPPER_DEPT_NM varchar(100) DEFAULT NULL COMMENT '상위부서명',
  EMP_DV_CD varchar(10) DEFAULT NULL COMMENT '사원구분코드',
  EMP_DV_NM varchar(100) DEFAULT NULL COMMENT '사원구분명',
  JOB_GROUP_CD varchar(10) DEFAULT NULL COMMENT '직군코드',
  JOB_GROUP_NM varchar(100) DEFAULT NULL COMMENT '직군명',
  JOB_DUT_CD varchar(10) DEFAULT NULL COMMENT '직책코드',
  JOB_DUT_NM varchar(100) DEFAULT NULL COMMENT '직책명',
  JOB_GRADE_CD varchar(10) DEFAULT NULL COMMENT '직급코드',
  JOB_GRADE_NM varchar(100) DEFAULT NULL COMMENT '직급명',
  JOB_CD varchar(10) DEFAULT NULL COMMENT '직무코드',
  JOB_NM varchar(100) DEFAULT NULL COMMENT '직무명',
  TITLE_CD varchar(10) DEFAULT NULL COMMENT '호칭코드',
  TITLE_NM varchar(100) DEFAULT NULL COMMENT '호칭명',
  SERVE_CD varchar(2) DEFAULT NULL COMMENT '재직상태코드(10:재직, 20:휴직, 30:퇴직)',
  SERVE_NM varchar(100) DEFAULT NULL COMMENT '재직상태명',
  HIRE_CD varchar(2) DEFAULT NULL COMMENT '채용구분코드',
  HIRE_NM varchar(100) DEFAULT NULL COMMENT '채용구분명',
  GRP_JOIN_DT varchar(8) DEFAULT NULL COMMENT '그룹입사일자',
  JOIN_DT varchar(8) DEFAULT NULL COMMENT '입사일자',
  RETIRE_DT varchar(8) DEFAULT NULL COMMENT '퇴사일자',
  EMAIL varchar(224) DEFAULT NULL COMMENT '이메일',
  OFF_TEL_NO varchar(64) DEFAULT NULL COMMENT '사무실전화번호',
  MOB_TEL_NO varchar(64) DEFAULT NULL COMMENT '모바일전화번호',
  VEND_CD varchar(10) DEFAULT NULL COMMENT '거래처코드',
  PICODE varchar(20) DEFAULT NULL COMMENT 'I/F ID',
  PISTAT varchar(1) DEFAULT NULL COMMENT 'I/F 상태',
  PIDATE varchar(8) DEFAULT NULL COMMENT 'I/F 일자',
  PITIME varchar(6) DEFAULT NULL COMMENT 'I/F 시간',
  PIUSER varchar(20) DEFAULT NULL COMMENT 'I/F 유저',
  PIMSG varchar(400) DEFAULT NULL COMMENT '전송메시지',
  PIMSGID varchar(32) DEFAULT NULL COMMENT '메시지ID',
  PRIMARY KEY (EMP_NO,COMP_CD),
  UNIQUE KEY PK_TB_MST_EMP (EMP_NO,COMP_CD)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='마스터_임직원'
;

/* Department */
CREATE TABLE TB_MST_DEPT (
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  DEPT_NO varchar(10) DEFAULT NULL COMMENT '부서코드',
  DEPT_NAME varchar(100) DEFAULT NULL COMMENT '부서명',
  ADMR_DEPT_NO varchar(10) DEFAULT NULL COMMENT '관리부서(상위부서)',
  LOCATION varchar(50) DEFAULT NULL COMMENT '위치',
  PRIMARY KEY (COMP_CD,DEPT_NO),
  UNIQUE KEY PK_TB_MST_DEPT (COMP_CD,DEPT_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='마스터_부서'
;

/* Login History */
CREATE TABLE TB_USER_LOGIN_HISTORY (
  LOG_ID int(11) NOT NULL AUTO_INCREMENT COMMENT '고유ID 자동순번',
  CONNECT_ID varchar(30) DEFAULT NULL COMMENT '접속ID',
  CONNECT_IP varchar(30) DEFAULT NULL COMMENT '접속IP',
  CONNECT_MTHD varchar(30) DEFAULT NULL COMMENT '접속방법(W/M) - Web/Mobile',
  CONNECT_ERROR varchar(100) DEFAULT NULL COMMENT '접속오류메시지',
  CONNECT_URL varchar(100) DEFAULT current_timestamp() COMMENT '접속URL',
  CREATION_DATE date DEFAULT NULL COMMENT '접속일시',
  PRIMARY KEY (log_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
;

/* 결재 헤더 */
CREATE TABLE TB_APPR_HD (
  APPR_NO varchar(30) NOT NULL COMMENT '결재번호',
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  DOC_TYPE_CD varchar(10) DEFAULT NULL COMMENT '문서유형코드',
  DOC_MNG_NO varchar(30) DEFAULT NULL COMMENT '문서관리번호',
  DOC_TITLE_NM varchar(100) DEFAULT NULL COMMENT '문서제목',
  DOC_STAT_CD varchar(10) DEFAULT NULL COMMENT '문서상태코드',
  DRAFT_ID varchar(20) DEFAULT NULL COMMENT '기안자ID',
  DRAFT_DTM datetime DEFAULT current_timestamp() COMMENT '기안일시',
  FNL_SEQ smallint(6) DEFAULT NULL COMMENT '최종순번',
  REMARK varchar(100) DEFAULT NULL COMMENT '비고',
  DOC_IN_VAL varchar(20) DEFAULT NULL COMMENT '문서고유번호',
  REG_ID varchar(20) DEFAULT NULL COMMENT '등록자ID',
  REG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일시',
  CHG_ID varchar(20) DEFAULT NULL COMMENT '수정자ID',
  CHG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (APPR_NO),
  UNIQUE KEY PK_TB_APPR_HD (APPR_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='결재헤더'
;

/* 결재 상세 */
CREATE TABLE TB_APPR_DT (
  APPR_NO varchar(30) NOT NULL COMMENT '결재번호',
  APPR_SEQ smallint(6) NOT NULL COMMENT '결재순번',
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  APPR_TYPE_CD varchar(10) DEFAULT NULL COMMENT '결재유형코드',
  APPR_FG_CD varchar(10) DEFAULT NULL COMMENT '결재구분코드',
  APRVER_ID varchar(20) DEFAULT NULL COMMENT '결재자사번',
  APRVER_NM varchar(100) DEFAULT NULL COMMENT '결재자성명',
  APRVER_DEPT_NM varchar(200) DEFAULT NULL COMMENT '결재자부서명',
  APRVER_JOB_NM varchar(100) DEFAULT NULL COMMENT '결재자직급명',
  FIX_YN varchar(1) DEFAULT NULL COMMENT '고정결재여부',
  A_APRVER_ID varchar(20) DEFAULT NULL COMMENT '실결재자ID',
  A_APRVER_NM varchar(100) DEFAULT NULL COMMENT '실결재자성명',
  A_APRVER_DEPT_NM varchar(200) DEFAULT NULL COMMENT '실결재자부서명',
  A_APRVER_JOB_NM varchar(100) DEFAULT NULL COMMENT '실결재자직급명',
  APPR_DTM datetime DEFAULT NULL COMMENT '결재일시',
  APPR_DESC varchar(200) DEFAULT NULL COMMENT '결재의견',
  REMARK varchar(400) DEFAULT NULL COMMENT '비고',
  REG_ID varchar(20) DEFAULT NULL COMMENT '등록자ID',
  REG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일시',
  CHG_ID varchar(20) DEFAULT NULL COMMENT '수정자ID',
  CHG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (APPR_NO,APPR_SEQ),
  UNIQUE KEY PK_TB_APPR_DT (APPR_NO,APPR_SEQ)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='결재라인'
;

/* 전결규정 */
CREATE TABLE TB_APPR_RULE (
  COMP_CD varchar(10) NOT NULL COMMENT '회사코드',
  DOC_TYPE_CD varchar(10) NOT NULL COMMENT '문서유형코드',
  DTL_TYPE_CD varchar(10) NOT NULL COMMENT '상세유형코드',
  CUR_CD varchar(10) NOT NULL COMMENT '통화코드',
  RULE_SEQ smallint(6) NOT NULL COMMENT '순번',
  USE_YN varchar(1) DEFAULT NULL COMMENT '사용여부',
  MAX_AMT decimal(15,0) DEFAULT NULL COMMENT '상한금액',
  APPR_TYPE_CD varchar(10) DEFAULT NULL COMMENT '결재유형코드',
  FIX_YN varchar(1) DEFAULT NULL COMMENT '고정결재여부',
  APRVER_CLASS_CD varchar(10) DEFAULT NULL COMMENT '결재자분류코드',
  APRVER_CLASS_VAL varchar(20) DEFAULT NULL COMMENT '결재자분류값',
  REMARK varchar(100) DEFAULT NULL COMMENT '비고',
  REG_ID varchar(20) NOT NULL COMMENT '등록자ID',
  REGI_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일시',
  CHG_ID varchar(20) NOT NULL COMMENT '변경자ID',
  CHG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '변경일시',
  PRIMARY KEY (COMP_CD,DOC_TYPE_CD,DTL_TYPE_CD,CUR_CD,RULE_SEQ),
  UNIQUE KEY PK_TB_APPR_RULE (COMP_CD,DOC_TYPE_CD,DTL_TYPE_CD,CUR_CD,RULE_SEQ)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='전결규정'
;

/* 결재 위임 */
CREATE TABLE TB_APPR_DELEGATE(
  ADLG_ID varchar(20) NOT NULL COMMENT '위임자ID',
  ACT_ID varchar(20) NOT NULL COMMENT '수임자ID',
  ADLG_SEQ smallint(6) NOT NULL COMMENT '순번',
  COMP_CD varchar(10) DEFAULT NULL COMMENT '회사코드',
  ADLG_STAT_CD varchar(10) DEFAULT NULL COMMENT '위임상태코드',
  ADLG_STR_DT varchar(8) DEFAULT NULL COMMENT '위임시작일자',
  ADLG_END_DT varchar(8) DEFAULT NULL COMMENT '위임종료일자',
  ADLG_RSN varchar(200) DEFAULT NULL COMMENT '위임사유내용',
  ADLG_EXE_DTM datetime DEFAULT NULL COMMENT '위임실행일시',
  ADLG_RVC_DTM datetime DEFAULT NULL COMMENT '위임해제일시',
  REG_ID varchar(20) DEFAULT NULL COMMENT '등록자ID',
  REG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일시',
  CHG_ID varchar(20) DEFAULT NULL COMMENT '수정자ID',
  CHG_DTM datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (ADLG_ID,ACT_ID,ADLG_SEQ),
  UNIQUE KEY PK_TB_APPR_DELEGATE (ADLG_ID,ACT_ID,ADLG_SEQ)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='결재위임'
;
