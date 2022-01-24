package com.iljin.apiServer.core.mail;

import com.google.gson.Gson;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class MailControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("MailController/{method-name}/{step}")).build();
    }

    @Test
    public void sendEmailTest() throws Exception {
        MailDto mailDto = new MailDto();
        mailDto.setFrom("jh.yoon@iljin.co.kr");
        mailDto.setTo("jh.yoon@iljin.co.kr");
        mailDto.setSubject("Simple test");
        mailDto.setText("Simple mailing test.");

        Gson gson = new Gson();
        String mailInfo = gson.toJson(mailDto);

        /*this.mockMvc.perform(post("/api/v1/mail")
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(mailInfo))
                .andExpect(status().isOk());*/
    }
}
