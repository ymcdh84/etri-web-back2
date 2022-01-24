package com.iljin.apiServer.template.approval;

import com.google.gson.Gson;
import com.iljin.apiServer.core.security.user.UserDto;
import com.iljin.apiServer.template.approval.dlgt.ApprovalDelegateDto;
import com.iljin.apiServer.template.approval.rule.ApprovalRuleDto;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ApprovalControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("ApprovalController/{method-name}/{step}")).build();
    }

    @Test
    public void getApprDeptTreeListTest() throws Exception {
        this.mockMvc.perform(get("/api/appr/detail/approvalLine")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getApprDelegatingListTest() throws Exception {
        ApprovalDelegateDto approvalDelegateDto = new ApprovalDelegateDto();
        approvalDelegateDto.setCompCd("101600");
        approvalDelegateDto.setAdlgStrDt("20200301");
        approvalDelegateDto.setAdlgEndDt("20200930");
        approvalDelegateDto.setAdlgStatCd("30");
        approvalDelegateDto.setAdlgId("340363");
        approvalDelegateDto.setActId("341002");

        Gson gson = new Gson();
        String searchFilter = gson.toJson(approvalDelegateDto);

        this.mockMvc.perform(post("/api/appr/delegating/list")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(searchFilter))
                .andExpect(status().isOk());
    }

    @Test
    public void saveDelegatingTest() throws Exception {
        ApprovalDelegateDto approvalDelegateDto = new ApprovalDelegateDto();
        //approvalDelegateDto.setAdlgSeq(Short.valueOf("3"));    //수정 시 필요한 파라미터
        approvalDelegateDto.setAdlgStrDt("20200901");
        approvalDelegateDto.setAdlgEndDt("20200930");
        approvalDelegateDto.setAdlgId("340363");
        approvalDelegateDto.setActId("341002");

        Gson gson = new Gson();
        String searchFilter = gson.toJson(approvalDelegateDto);

        this.mockMvc.perform(post("/api/appr/delegating/save")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(searchFilter))
                .andExpect(status().isOk());
    }

    @Test
    public void cancelDelegatingTest() throws Exception {
        ApprovalDelegateDto approvalDelegateDto = new ApprovalDelegateDto();
        approvalDelegateDto.setAdlgSeq(Short.valueOf("3"));
        approvalDelegateDto.setAdlgId("340363");
        approvalDelegateDto.setActId("341002");

        Gson gson = new Gson();
        String searchFilter = gson.toJson(approvalDelegateDto);

        this.mockMvc.perform(post("/api/appr/delegating/cancel")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(searchFilter))
                .andExpect(status().isOk());
    }

    @Test
    public void getDelegatingCheckTest() throws Exception {

        this.mockMvc.perform(get("/api/appr/delegatingCheck")
                .param("adlgId", "340363")
                .param("actId", "341002")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getApprRuleListTest() throws Exception {
        ApprovalRuleDto approvalRuleDto = new ApprovalRuleDto();
        approvalRuleDto.setCompCd("101600");
        approvalRuleDto.setDocTypeCd("SLIP");    //문서 유형
        approvalRuleDto.setDtlTypeCd("E1");    //해당 문서의 상세 유형
        approvalRuleDto.setUseYn("Y");    //전결규정 사용 유무

        Gson gson = new Gson();
        String approvalRule = gson.toJson(approvalRuleDto);

        this.mockMvc.perform(post("/api/appr/rule/list")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(approvalRule))
                .andExpect(status().isOk());
    }
}
