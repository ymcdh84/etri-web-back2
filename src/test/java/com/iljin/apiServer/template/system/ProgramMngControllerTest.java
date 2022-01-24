package com.iljin.apiServer.template.system;

import com.google.gson.Gson;
import com.iljin.apiServer.template.approval.dlgt.ApprovalDelegateDto;
import com.iljin.apiServer.template.system.menu.MenuDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ProgramMngControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("ProgramMngController/{method-name}/{step}")).build();
    }

    @Test
    public void getMenuListTest() throws Exception {
        MenuDto menuDto = new MenuDto();
        menuDto.setMenuNm("메뉴");

        Gson gson = new Gson();
        String searchFilter = gson.toJson(menuDto);

        this.mockMvc.perform(post("/api/program/list")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(searchFilter))
                .andExpect(status().isOk());
    }

    @Test
    public void saveMenuListTest() throws Exception {
        List<MenuDto> list = new ArrayList<>();

        MenuDto menuDto = new MenuDto();
        menuDto.setCompCd("101600");
        menuDto.setMenuNo("8000000");
        menuDto.setUpperMenuNo("0");
        menuDto.setMenuNm("TEST");
        menuDto.setProgramFileNm("/test");
        menuDto.setMenuOrder(Integer.parseInt("1"));
        menuDto.setRelateImageNm("/");
        menuDto.setRelateImagePath("/");
        menuDto.setMenuDc("TEST_MENU");

        list.add(menuDto);

        Gson gson = new Gson();
        String searchFilter = gson.toJson(list);

        this.mockMvc.perform(post("/api/program/save")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(searchFilter))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserTest() throws Exception {

        this.mockMvc.perform(delete("/api/program/")
                .param("compCd", "101600")
                .param("menuNo", "8000000")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }
}
