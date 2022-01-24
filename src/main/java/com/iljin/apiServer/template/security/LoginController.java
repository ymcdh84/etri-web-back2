package com.iljin.apiServer.template.security;

import com.iljin.apiServer.core.security.AuthToken;
import com.iljin.apiServer.core.security.oauth.OAuthService;
import com.iljin.apiServer.core.security.sso.SsoService;
import com.iljin.apiServer.core.security.user.UserDto;
import com.iljin.apiServer.core.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class LoginController {
    @GetMapping("/")
    public String index() {
        return "Hello, World";
    }

    private final UserService userService;
    private final OAuthService oAuthService;
    private final SsoService ssoService;

    @Value("${address.frontend}")
    private String frontAddress;

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody UserDto userDto, HttpSession session, HttpServletRequest request) {
        return userService.login(userDto, session, request);
    }

    @PostMapping("/login/sso")
    public ResponseEntity<AuthToken> ssoLogin(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        ResponseEntity<AuthToken> result;
        String loginId = ssoService.ssoLogin(req, resp);


        if (!"-1".equals(loginId)) {
            result = userService.ssoLogin(loginId, session);
        } else return null;

        return result;
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        userService.logout(session);
        return new ResponseEntity<> ("로그아웃 되었습니다.", HttpStatus.OK);
    }

    /*@GetMapping(value = "/login/oauth2/code/google")
    protected ResponseEntity<HttpHeaders> oauthOfGoogle(
            @RequestParam(value = "code") String code,
            HttpSession session
    ) throws URISyntaxException, ServletException, IOException {
        HttpHeaders httpHeaders = oAuthService.getAuthTokenWithOauthOfGoogle(session, code);

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }*/

    /*@GetMapping(value = "/login/oauth2/code/naver")
    public ResponseEntity<HttpHeaders> oauthOfNaver(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "state") String state,
            HttpSession session
    ) throws Exception {
        HttpHeaders httpHeaders = oAuthService.getAuthTokenWithOauthOfNaver(session, code, state);

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }*/

}
