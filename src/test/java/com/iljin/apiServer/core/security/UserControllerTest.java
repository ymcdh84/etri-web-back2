package com.iljin.apiServer.core.security;

import com.google.gson.Gson;
import com.iljin.apiServer.core.security.role.UserRoleDto;
import com.iljin.apiServer.core.security.user.User;
import com.iljin.apiServer.core.security.user.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class UserControllerTest {
    private final String host = "localhost:9081";

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("UserController/{method-name}/{step}")).build();
    }

    @Test
    public void getUsersTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/user/list")
                .header("Host", host))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        String id = "1";

        this.mockMvc.perform(get("/api/v1/user/id/{id}", id)
                .header("Host", host))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByLoginIdTest() throws Exception {
        String loginId = "admin";

        this.mockMvc.perform(get("/api/v1/user/login-id/{loginId}", loginId)
                .header("Host", host))
                .andExpect(status().isOk());
    }

    @Test
    public void addUserTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setLoginId("user01");
        userDto.setLoginPw("user01");
        userDto.setUserName("test-user");
        userDto.setEnableFlag(true);
        userDto.setCompCd("company");
        userDto.setDeptCd("department");
        userDto.setRole("USER");

        Gson gson = new Gson();
        String userDtoInfo = gson.toJson(userDto);

        this.mockMvc.perform(post("/api/v1/user/add")
                .header("Host", host)
                .contentType(MediaType.APPLICATION_JSON).content(userDtoInfo))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserTest() throws Exception {
        String loginId = "user01";

        this.mockMvc.perform(delete("/api/v1/user/{loginId}", loginId)
                .header("Host", host))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserTest() throws Exception {
        String loginId = "admin";

        UserDto userDto = new UserDto();
        userDto.setLoginId(loginId);
        userDto.setLoginPw("admin");
        userDto.setUserName("TEST");
        userDto.setEnableFlag(false);
        userDto.setRole("ADMIN");

        Gson gson = new Gson();
        String userDtoInfo = gson.toJson(userDto);

        this.mockMvc.perform(put("/api/v1/user/update")
                .header("Host", host)
                .contentType(MediaType.APPLICATION_JSON).content(userDtoInfo))
                .andExpect(status().isOk());
    }
}
