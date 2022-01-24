package com.iljin.apiServer.core.security.sso;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import com.saerom.onepass.agent.OpAgent;

@Slf4j
@RequiredArgsConstructor
@Service
public class SsoServiceImpl implements SsoService{
    private final HashMap configMap = new HashMap();
    @Value("${opagent.app.ptk.use}")
    String appPtkUse;
    @Value("${opagent.sso.server.context}")
    String ssoServerContext;
    @Value("${opagent.sso.server.login.url}")
    String ssoServerLoginUrl;
    @Value("${opagent.sso.server.logout.url}")
    String ssoServerLogoutUrl;
    @Value("${opagent.sso.server.ip}")
    String ssoServerIp;
    @Value("${opagent.sso.server.port}")
    String ssoServerPort;
    @Value("${opagent.cookieUse}")
    String cookieUse;
    @Value("${opagent.validate.request.timeout}")
    String validateRequestTimeout;
    @Value("${opagent.sso.client.login}")
    String ssoClientLogin;
    @Value("${opagent.login.view.emate.apps}")
    String loginViewEmateApps;
    @Value("${opagent.sso.server.url}")
    String ssoServerUrl;
    @Value("${opagent.apps.login.url}")
    String appsLoginUrl;
    @Value("${opagent.apps.logout.url}")
    String appsLogoutUrl;

    @Override
    public String ssoLogin(HttpServletRequest req, HttpServletResponse res) {
        OpAgent onePass = new OpAgent(req, res, configMap);

        try {
            int ssoResult = onePass.op_SsoValidate();

            switch(ssoResult) {

                case OpAgent.OP_ERR:
                    log.error("SSO 오류 발생: " + OpAgent.OP_ERR);
                    return "-1";
                case OpAgent.OP_ERR_NO_AUTH_TOKEN:
                    log.error("SSO 오류 발생: 인증 Token 이 없음 " + OpAgent.OP_ERR_NO_AUTH_TOKEN);
                    return "-1";
                case OpAgent.OP_ERR_INVALID_AUTH_TOKEN:
                    log.error("SSO 오류 발생: 유효하지 않은 인증 " + OpAgent.OP_ERR_INVALID_AUTH_TOKEN);
                    return "-1";
                case OpAgent.OP_ERR_HTTP_COMM_ERR:
                    log.error("SSO 오류 발생: HTTP 통신 에러 Token " + OpAgent.OP_ERR_HTTP_COMM_ERR);
                    return "-1";
                case OpAgent.OP_ERR_XML_PARSING_FAILED:
                    log.error("SSO 오류 발생: 서버로 부터 전달받은 XML 파싱 오류 " + OpAgent.OP_ERR_XML_PARSING_FAILED);
                    return "-1";
                case OpAgent.OP_OK:
                    log.info(
                            "SSO 성공: " +
                                    "로그인 ID : " + onePass.op_uId +
                                    "SSO 비밀번호 : " + onePass.op_passwd +
                                    "사번 : " + onePass.op_empNumber +
                                    "부서코드 : " + onePass.op_groupId
                    );
                    return onePass.op_empNumber;
                default: return "-1";
            }
        } catch (Exception ex) {
            log.error("SSO 오류 발생: 예기치 않은 오류 " + ex);
            return "-1";
        }
    }
}
