package com.iljin.apiServer.template.system.emp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iljin.apiServer.core.util.Error;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/emp")
public class EmployeeController {
    private final EmployeeService employeeService;

    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<Error> empNotFound(EmployeeException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Subject : 임직원 List 조회
     * Features : 전체 조회  임직원 이름, 사번으로 조회
     */
    @GetMapping(value={"/list", "/list/{value}"})
    public ResponseEntity<List<EmployeeDto>> getEmployeeList(@PathVariable(required = false) String value) {
        List<EmployeeDto> employees = employeeService.getEmpListByEmpNameOrEmpNo(value);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Subject : 임직원 detail 조회
     * Features : 임직원 상세조회
     */
    @GetMapping("/{loginId}")
    public ResponseEntity<EmployeeDto> getEmployeeDetail(@PathVariable(required = false) String loginId) {
        EmployeeDto employee = employeeService.getEmployeeDetailByLoginId(loginId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    /**
     * Subject : 임직원 정보 저장
     * Features : 신규 저장, 수정
     */
    @PutMapping("/{loginId}")
    public ResponseEntity<EmployeeDto> saveEmployee(@PathVariable(required = true) String loginId, @RequestBody EmployeeDto employeeDto) {
        EmployeeDto employee = employeeService.saveEmployeeByLoginId(loginId, employeeDto);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    /**
     * Subject : 임직원 정보 삭제
     * Features : 삭제
     */
    @DeleteMapping("/{loginId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(required = true) String loginId) {
        String response = employeeService.deleteEmployeeByLoginId(loginId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Subject : 임직원 부서 List 조회
     */
    @GetMapping(value="/dept")
    public ResponseEntity<List<EmployeeDto>> getEmployeeDeptList() {
        List<EmployeeDto> deptList = employeeService.getEmpDeptList();
        return new ResponseEntity<>(deptList, HttpStatus.OK);
    }

    /**
     * 임직원 조회
     */
    @GetMapping(value={"/search", "/search/{value}"})
    public ResponseEntity<List<Employee>> searchEmployees(@PathVariable(required = false) String value) {
        List<Employee> employees = employeeService.searchEmployeesEmpNoOrEmpNmOrDeptNm(value);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * 부서별 임지원 조회
     * */
    @GetMapping("/dept/{deptCd}")
    public ResponseEntity<List<Employee>> getEmployeesByDept(@PathVariable(required = true) String deptCd) {
        List<Employee> list = employeeService.getEmpsByDept(deptCd);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
