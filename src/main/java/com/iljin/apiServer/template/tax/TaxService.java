package com.iljin.apiServer.template.tax;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface TaxService {

    //인증서 Key값 추출
    public Map<String, Object> certificateKey(String root) throws Exception;

    //홈택스 서명 문자열 호출 API
    public Map<String, Object> urlHometaxWqAction() throws JsonProcessingException, Exception;

    //홈택스 로그인 API
    public Map<String, Object> urlHometaxPubcLogin(Map pWqInfo) throws JsonProcessingException, Exception;

    //개인키 랜덤값 추출
    public String randomPrivateKey(String privB64, String certB64, String idn, String pw);

    //서명값 호출 함수
    public String signedResult(String root, String singMsg, String pw);

    //로그인 사용자 정보 호출
    public Map<String, Object> urlHometaxLoginInfo(Map pWqInfo) throws JsonProcessingException, Exception;

    //teetSeesionId 추출
    public Map<String, Object> urlHometaxTEETSessionID(Map pWqInfo) throws Exception;

    // SSO 토큰 함수 호출
    public String urlHometaxSsoToken(Map pWqInfo) throws Exception;

    // SSO 로그인(전자세금계산서 시스템 로그인)
    public String urlHometaxSsoLogin(Map pWqInfo) throws Exception;

    // NetFunnelId 취득 함수 (전자세금 계산서 조회 위한 ID)
    public String urlHometaxNetFunnelId(Map pWqInfo) throws Exception;

    // 세금계산서 조회
    public String urlHometaxSchTaxBill(Map pWqInfo) throws Exception;

    // 세금계산서 xml 파일 다운로드(jsoup 사용)
    public List<String> downLoadTaxBillXml(Map pWqInfo, String taxList) throws Exception;

    // 세금계산서 xml 파일 다운로드(jsoup 미사용)
    public void downLoadTaxBillXmlWithoutJsoup(Map pWqInfo, String taxList) throws Exception;
}
