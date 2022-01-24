package com.iljin.apiServer.template.system;

import com.google.gson.Gson;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class CodeControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("CodeController/{method-name}/{step}")).build();
    }

    @Test
    public void getComboBoxTest() throws Exception {

        this.mockMvc.perform(get("/api/code/combo")
                .param("groupCd", "APPR_FG_CD")
                .param("remark1", "")
                .param("remark2", "")
                .param("remark3", "")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCodeDetailsTest() throws Exception {

        this.mockMvc.perform(get("/api/code/detail")
                .param("groupCd", "DOC_STAT_CD")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCodeAllTest() throws Exception {

        this.mockMvc.perform(get("/api/code/")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getGroupCodeListTest() throws Exception {
        CodeDto codeDto = new CodeDto();
        codeDto.setCompCd("101600");
        codeDto.setGroupCd("DOC_STAT_CD");
        codeDto.setUseYn("Y");

        Gson gson = new Gson();
        String searchFilter = gson.toJson(codeDto);

        this.mockMvc.perform(post("/api/code/list")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(searchFilter))
                .andExpect(status().isOk());
    }

    @Test
    public void getGroupCodeDetailListTest() throws Exception {
        CodeDto codeDto = new CodeDto();
        codeDto.setGroupCd("DOC_STAT_CD");

        Gson gson = new Gson();
        String searchFilter = gson.toJson(codeDto);

        this.mockMvc.perform(post("/api/code/detail")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(searchFilter))
                .andExpect(status().isOk());
    }

    @Test
    public void saveCodeListsTest() throws Exception {
        CodeDto codeDto = new CodeDto();
        Map<String, List<CodeDto>> param = new HashMap<>();
        List<CodeDto> codeHeader = new ArrayList<>();
        List<CodeDto> codeDetail = new ArrayList<>();

        codeDto.setCompCd("101600");
        codeDto.setGroupCd("TEST01");
        codeDto.setGroupNm("test01");
        codeDto.setGroupDesc("desc01");
        codeHeader.add(codeDto);
        codeDto = new CodeDto();
        codeDto.setCompCd("101600");
        codeDto.setGroupCd("TEST02");
        codeDto.setGroupNm("test02");
        codeDto.setGroupDesc("desc02");
        codeHeader.add(codeDto);

        codeDto = new CodeDto();
        codeDto.setCompCd("101600");
        codeDto.setGroupCd("TEST01");
        codeDto.setDetailCd("DT01");
        codeDto.setDetailNm("DT01NM");
        codeDto.setUseYn("Y");
        codeDto.setOrderSeq(1);
        codeDto.setDetailDesc("DESC01");
        codeDetail.add(codeDto);
        codeDto = new CodeDto();
        codeDto.setCompCd("101600");
        codeDto.setGroupCd("TEST01");
        codeDto.setDetailCd("DT02");
        codeDto.setDetailNm("DT02NM");
        codeDto.setUseYn("Y");
        codeDto.setOrderSeq(2);
        codeDto.setDetailDesc("DESC02");
        codeDetail.add(codeDto);

        param.put("codeHeader",codeHeader);
        param.put("codeDetail",codeDetail);

        Gson gson = new Gson();
        String params = gson.toJson(param);

        this.mockMvc.perform(put("/api/code/save")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(params))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCodeTest() throws Exception {
        String groupCd = "TEST02";

        this.mockMvc.perform(delete("/api/code/delete/{groupCd}", groupCd)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }
}
