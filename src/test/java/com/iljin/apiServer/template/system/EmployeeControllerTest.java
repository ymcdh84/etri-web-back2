package com.iljin.apiServer.template.system;

import com.google.gson.Gson;
import com.iljin.apiServer.template.system.emp.EmployeeDto;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("EmployeeController/{method-name}/{step}")).build();
    }

    @Test
    public void getEmployeesTest() throws Exception {

        this.mockMvc.perform(get("/api/emp/")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());

        String value = "Administrator";

        this.mockMvc.perform(get("/api/emp/{value}", value)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmpsByDeptTest() throws Exception {

        String deptCd = "O000000001";

        this.mockMvc.perform(get("/api/emp/dept/{deptCd}", deptCd)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeListTest() throws Exception {
        String value = "admin";

        this.mockMvc.perform(get("/api/emp/list/{value}", value)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeDetailByLoginIdTest() throws Exception {
        String loginId = "admin";

        this.mockMvc.perform(get("/api/emp/{loginId}", loginId)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void saveOldEmployeeTest() throws Exception {
        String loginId = "admin";
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmpNo(loginId);
        employeeDto.setEmpNm("Administrator");
        employeeDto.setDeptCd("O000000001");
        employeeDto.setDeptNm("일진씨앤에스(주)");
        employeeDto.setUpperDeptCd(null);
        employeeDto.setUpperDeptNm(null);
        employeeDto.setJobGradeCd("Admin");
        employeeDto.setJobGradeNm("Admin");
        employeeDto.setJobDutCd("Administrator");
        employeeDto.setJobDutNm("Administrator");
        employeeDto.setRole("ADMIN");
        employeeDto.setServeCd("10");
        employeeDto.setServeNm("재직");
        employeeDto.setEnableFlag(true);
        employeeDto.setEmail("admin@iljin.co.kr");
        employeeDto.setMobTelNo("01031844190");

        Gson gson = new Gson();
        String params = gson.toJson(employeeDto);

        this.mockMvc.perform(put("/api/emp/{loginId}", loginId)
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(params))
                .andExpect(status().isOk());
    }

    @Test
    public void saveNewEmployeeTest() throws Exception {
        String loginId = "user02";
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmpNo(loginId);
        employeeDto.setEmpNm("USER-02");
        employeeDto.setPassword("admin");
        employeeDto.setDeptCd("O000000001");
        employeeDto.setDeptNm("일진씨앤에스(주)");
        employeeDto.setUpperDeptCd(null);
        employeeDto.setUpperDeptNm(null);
        employeeDto.setJobGradeCd("Admin");
        employeeDto.setJobGradeNm("Admin");
        employeeDto.setJobDutCd("Administrator");
        employeeDto.setJobDutNm("Administrator");
        employeeDto.setRole("USER");
        employeeDto.setServeCd("10");
        employeeDto.setServeNm("재직");
        employeeDto.setEnableFlag(true);
        employeeDto.setEmail("user02@iljin.co.kr");
        employeeDto.setMobTelNo("01031844190");

        Gson gson = new Gson();
        String params = gson.toJson(employeeDto);

        this.mockMvc.perform(put("/api/emp/{loginId}", loginId)
                .header("Host", "localhost:8081")
                .contentType(MediaType.APPLICATION_JSON).content(params))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeByLoginIdTest() throws Exception {
        String loginId = "user02";

        this.mockMvc.perform(delete("/api/emp/{loginId}", loginId)
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeDeptListTest() throws Exception {

        this.mockMvc.perform(get("/api/emp/dept")
                .header("Host", "localhost:8081"))
                .andExpect(status().isOk());
    }
}
