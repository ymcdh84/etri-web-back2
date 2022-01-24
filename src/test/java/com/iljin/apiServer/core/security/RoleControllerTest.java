package com.iljin.apiServer.core.security;

import com.google.gson.Gson;
import com.iljin.apiServer.core.mail.MailDto;
import com.iljin.apiServer.core.security.role.RoleType;
import com.iljin.apiServer.core.security.role.UserRoleDto;
import com.iljin.apiServer.core.security.role.UserRoleKey;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class RoleControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("RoleController/{method-name}/{step}")).build();
    }

    @Test
    public void getAllTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/role/roles")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void addRoleTest() throws Exception {
        /*
        UserDto userDto = new UserDto();

        Gson gson = new Gson();
        String userInfo = gson.toJson(userDto);

        this.mockMvc.perform(post("/api/v1/role/add")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(userInfo))
                .andExpect(status().isOk());
        */
    }

    @Test
    public void getRoleByIdTest() throws Exception {
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setId(Long.valueOf(1));
        userRoleDto.setCompCd("101600");

        Gson gson = new Gson();
        String userRoleInfo = gson.toJson(userRoleDto);

        this.mockMvc.perform(post("/api/v1/role/user/role-key")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(userRoleInfo))
                .andExpect(status().isOk());
    }

    @Test
    public void getRoleTest() throws Exception {
        String role = "ADMIN";

        this.mockMvc.perform(get("/api/v1/role/by-role/{role}", role)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getRoleByLoginIdTest() throws Exception {
        String loginId = "admin";

        this.mockMvc.perform(get("/api/v1/role/login-id/{loginId}", loginId)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getRolesByUserNameTest() throws Exception {
        String userName = "관리자";

        this.mockMvc.perform(get("/api/v1/role/user-name/{userName}", userName)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRoleByIdTest() throws Exception {
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setId(Long.valueOf(999));
        userRoleDto.setCompCd("101600");

        Gson gson = new Gson();
        String userRoleInfo = gson.toJson(userRoleDto);

        this.mockMvc.perform(post("/api/v1/role/delete")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(userRoleInfo))
                .andExpect(status().isOk());
    }
}
