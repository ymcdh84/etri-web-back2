package com.iljin.apiServer.core.security;

import com.google.gson.Gson;
import com.iljin.apiServer.core.security.user.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class LoginControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("LoginController/{method-name}/{step}")).build();
    }

    @Test
    public void index() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void loginTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setLoginId("admin");
        userDto.setLoginPw("admin");
        userDto.setEnableFlag(true);

        Gson gson = new Gson();
        String loginInfo = gson.toJson(userDto);

        this.mockMvc.perform(post("/login")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(loginInfo))
                .andExpect(status().isOk());
    }

    @Test
    public void logoutTest() throws Exception {
        this.mockMvc.perform(get("/logout")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }
}
