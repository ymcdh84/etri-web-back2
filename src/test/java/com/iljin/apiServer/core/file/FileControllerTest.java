package com.iljin.apiServer.core.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class FileControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("FileController/{method-name}/{step}")).build();
    }

    @Test
    public void uploadFileTest() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        this.mockMvc.perform(multipart("/api/v1/file/upload")
                .file(file)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFileByIdTest() throws Exception {
        String id = "1633";

        this.mockMvc.perform(delete("/api/v1/file/{id}", id)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void uploadMultipleFilesTest() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "files",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        MockMultipartFile file2
                = new MockMultipartFile(
                "files",
                "hi.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hi, everyone!".getBytes()
        );

        this.mockMvc.perform(multipart("/api/v1/file/upload/files")
                .file(file)
                .file(file2)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void downloadByStoredNameTest() throws Exception {
        String storedFileName = "203dcc6f-47c9-444f-9d4a-ace648c414d5.txt";

        this.mockMvc.perform(get("/api/v1/file/download/{storedFileName:.*}", storedFileName)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }
}
