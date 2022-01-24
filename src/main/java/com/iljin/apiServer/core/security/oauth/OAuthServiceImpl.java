package com.iljin.apiServer.core.security.oauth;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.iljin.apiServer.core.security.AuthToken;
import com.iljin.apiServer.core.security.role.RoleRepository;
import com.iljin.apiServer.core.security.role.UserRole;
import com.iljin.apiServer.core.security.user.User;
import com.iljin.apiServer.core.security.user.UserRepository;
import com.iljin.apiServer.core.security.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OAuthServiceImpl implements OAuthService{
    private final UserRepository userRepository;
    private final OAuthRepository OAuthRepository;
    private final RoleRepository roleRepository;

    /* Google */
    /*@Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String googleTokenUri;
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String googleUserInfoUri;*/

    /* Naver */
    /*@Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;
    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String naverTokenUri;
    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverUserInfoUri;*/

    @Value("${address.frontend}")
    private String frontAddress;
    @Value("${address.backend}")
    private String backAddress;

    private String getHttpConnection(String uri, String provider, String accessToken) throws IOException {
        try {
            String header = "";

            if (provider.equals("google")) {
                uri +=  "?access_token=" + accessToken;
            } else if (provider.equals("naver")) {
                header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
            }

            URL url = new URL(uri);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", header);

            int responseCode = conn.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            return res.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String getHttpConnection(String uri, String param) throws IOException {
        URL url = new URL(uri);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        try (OutputStream stream = conn.getOutputStream()) {
            try (BufferedWriter wd = new BufferedWriter(new OutputStreamWriter(stream))) {
                wd.write(param);
            }
        }
        int responseCode = conn.getResponseCode();

        String line;
        StringBuffer buffer = new StringBuffer();
        try (InputStream stream = conn.getInputStream()) {
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(stream))) {
                while ((line = rd.readLine()) != null) {
                    buffer.append(line);
                    buffer.append('\r');
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    private AuthToken loginWithEmail(HttpSession session, String userEmail) {
        Optional<OAuth> sUser = OAuthRepository.findByEmail(userEmail);

        /* 기존에 등록된 사용자면 */
        if(sUser.isPresent()) {
            String loginId = sUser.get().getEmpNo();

            Optional<User> user = userRepository.findByLoginId(loginId);
            Optional<AuthToken> result =
                    user.map(obj -> {
                        // Create Granted Authority Rules
                        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                        // 1. username, password를 조합하여 UsernamePasswordAuthenticationToken 생성
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginId, null, grantedAuthorities);

                        return getAuthToken(session, loginId, obj, token);
                    });
            return result
                    .orElseGet(() -> new AuthToken(
                            null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null
                            , null));
        }

        return new AuthToken(
                null
                , null
                , null
                , null
                , null
                , null
                , null
                , null
                , null
                , null
                , null
                , null
                , null);
    }

    @NotNull
    private AuthToken getAuthToken(final HttpSession session, final String loginId, final User obj, final UsernamePasswordAuthenticationToken token) {
        // 4. Authentication 인스턴스를 SecurityContextHolder의 SecurityContext에 설정
        SecurityContextHolder.getContext().setAuthentication(token);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        Optional<User> user = userRepository.findByLoginId(loginId);

        List<Role> authList = roleRepository.findByRoleCdIn(user.get().getRoles().stream().map(UserRole::getRole).collect(Collectors.toList()));

        return new AuthToken(obj.getUserName(),
                loginId,
                user.get().getCompCd(),
                user.get().getEmployee().getCompNm(),
                user.get().getEmployee().getDeptCd(),
                user.get().getEmployee().getDeptNm(),
                user.get().getEmployee().getJobDutCd(),
                user.get().getEmployee().getJobDutNm(),
                user.get().getEmployee().getJobGradeCd(),
                user.get().getEmployee().getJobGradeNm(),
                session.getId(),
                user.get().getAttribute2(),
                authList);
    }

/*    private String getUserInfo(String access_token) {
        try {
            String header = "Bearer " + access_token; // Bearer 다음에 공백 추가

            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            return res.toString();
        } catch (Exception e) {
            System.err.println(e);
            return "Err";
        }
    }*/

    /*@Override
    public HttpHeaders getAuthTokenWithOauthOfGoogle(HttpSession session, String code) throws IOException, URISyntaxException {
        String userEmail = "";

        String query = "code=" + code;
        query += "&client_id=" + googleClientId;
        query += "&client_secret=" + googleClientSecret;
        query += "&redirect_uri=" + backAddress +"/login/oauth2/code/google";
        query += "&grant_type=authorization_code";

        *//* Google Authorization *//*
        String tokenJson = getHttpConnection(googleTokenUri, query);

        Gson gson = new Gson();
        SocialToken token = gson.fromJson(tokenJson, SocialToken.class);

        *//* request Google User resource *//*
        String ret = getHttpConnection(googleUserInfoUri,"google", token.getAccess_token());

        JsonParser parser = new JsonParser();
        JsonElement accessElement = parser.parse(ret);
        userEmail = accessElement.getAsJsonObject().get("email").getAsString();

        AuthToken authToken = loginWithEmail(session, userEmail);

        URI redirectUri = new URI( frontAddress + "/login?loginId=" +authToken.getLoginId() + "&token=" + authToken.getToken());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return httpHeaders;
    }*/

    /*@Override
    public HttpHeaders getAuthTokenWithOauthOfNaver(HttpSession session, String code, String state) throws IOException, URISyntaxException {
        String userEmail = "";

        //String clientId = naverClientId;//애플리케이션 클라이언트 아이디값
        //String secret = naverClientSecret;

        StringBuilder query = new StringBuilder();
        query.append("grant_type=authorization_code");
        query.append("&client_id=" + naverClientId);
        query.append("&client_secret=" + naverClientSecret);
        query.append("&code=" + code);
        query.append("&state=" + state);

        *//* Naver Authorization *//*
        String accessTokenJson = getHttpConnection(naverTokenUri, query.toString());

        Gson gson = new Gson();
        SocialToken token = gson.fromJson(accessTokenJson, SocialToken.class);

        *//* request Naver User resource *//*
        //String ret = getUserInfo(token.getAccess_token());
        String ret = getHttpConnection(naverUserInfoUri,"naver", token.getAccess_token());

        JsonParser parser = new JsonParser();
        JsonElement accessElement = parser.parse(ret);
        userEmail = accessElement.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();

        AuthToken authToken = loginWithEmail(session, userEmail);

        URI redirectUri = new URI(frontAddress + "/login?loginId=" +authToken.getLoginId() + "&token=" + authToken.getToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return httpHeaders;
    }*/
}
