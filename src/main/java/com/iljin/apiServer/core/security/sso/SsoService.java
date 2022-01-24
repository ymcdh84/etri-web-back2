package com.iljin.apiServer.core.security.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SsoService {
    String ssoLogin(HttpServletRequest req, HttpServletResponse res);
}
