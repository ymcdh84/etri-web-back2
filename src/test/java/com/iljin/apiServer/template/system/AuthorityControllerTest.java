package com.iljin.apiServer.template.system;

import com.google.gson.Gson;
import com.iljin.apiServer.template.system.authority.AuthorityDto;
import com.iljin.apiServer.template.system.authority.MenuAuthDto;
import com.iljin.apiServer.template.system.code.CodeDto;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class AuthorityControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("AuthorityController/{method-name}/{step}")).build();
    }

    /* 권한관리 조회(회사별) */
    @Test
    public void getAuthoritiesTest() throws Exception {

        this.mockMvc.perform(get("/api/auth/")
                .param("compCd", "101600")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    /* 권한관리 저장/수정 */
    @Test
    public void saveAuthoritiesTest() throws Exception {
        AuthorityDto authorityDto = new AuthorityDto();
        List<AuthorityDto> list = new ArrayList<>();

        authorityDto.setCompCd("101600");
        authorityDto.setRoleCd("TEST");
        authorityDto.setRoleNm("test");
        authorityDto.setRoleSelectCd("10");
        authorityDto.setRoleDc("description");
        list.add(authorityDto);

        authorityDto = new AuthorityDto();
        authorityDto.setCompCd("101600");
        authorityDto.setRoleCd("TEST2");
        authorityDto.setRoleNm("test2");
        authorityDto.setRoleSelectCd("20");
        authorityDto.setRoleDc("description2");
        list.add(authorityDto);

        Gson gson = new Gson();
        String params = gson.toJson(list);

        this.mockMvc.perform(put("/api/auth/")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(params))
                .andExpect(status().isOk());
    }

    /* 권한 삭제 */
    @Test
    public void deleteAuthorityTest() throws Exception {
        String compCd = "101600";
        String roleCd = "TEST";

        this.mockMvc.perform(delete("/api/auth/")
                .param("roleCd", roleCd)
                .param("compCd", compCd)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    /* 권한관리 - 권한별 메뉴 조회 */
    @Test
    public void getMenuByAuthorityTest() throws Exception {

        this.mockMvc.perform(get("/api/auth/menu")
                .param("roleCd", "TEST2")
                .param("compCd", "101600")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    /* 권한관리 - 권한별 메뉴 수정 */
    @Test
    public void saveMenuByAuthorityTest() throws Exception {
        String roleCd = "TEST2";
        String compCd = "101600";

        MenuAuthDto menuAuthDto = new MenuAuthDto();
        List<MenuAuthDto> list = new ArrayList<>();

        menuAuthDto.setCompCd("101600");
        menuAuthDto.setRoleCd("TEST2");
        menuAuthDto.setMenuNo("7000000");
        list.add(menuAuthDto);

        menuAuthDto = new MenuAuthDto();
        menuAuthDto.setCompCd("101600");
        menuAuthDto.setRoleCd("TEST2");
        menuAuthDto.setMenuNo("7010000");
        list.add(menuAuthDto);

        Gson gson = new Gson();
        String params = gson.toJson(list);

        this.mockMvc.perform(put("/api/auth/menu/{roleCd}/{compCd}", roleCd, compCd)
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(params))
                .andExpect(status().isOk());
    }

    /* 권한관리 - 권한별 사용자 조회 */
    @Test
    public void getUserInfoByAuthorityTest() throws Exception {

        this.mockMvc.perform(get("/api/auth/user")
                .param("roleCd", "TEST2")
                .param("compCd", "101600")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    /* 권한관리 - 권한별 사용자 수정 */
    @Test
    public void saveUserInfoByAuthorityTest() throws Exception {
        String roleCd = "TEST2";
        String compCd = "101600";

        AuthorityDto authorityDto = new AuthorityDto();
        List<AuthorityDto> list = new ArrayList<>();

        authorityDto.setRoleChk("1");
        authorityDto.setEmpNo("test");
        list.add(authorityDto);

        Gson gson = new Gson();
        String params = gson.toJson(list);

        this.mockMvc.perform(put("/api/auth/user/{roleCd}/{compCd}", roleCd, compCd)
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(params))
                .andExpect(status().isOk());
    }
}
