package com.iljin.apiServer.template.system.emp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeService {

    /* get Employees */
    List<EmployeeDto> getEmpListByEmpNameOrEmpNo(String value);

    /* get Employee detail*/
    EmployeeDto getEmployeeDetailByLoginId(String loginId);

    /* save Employee - New or Old */
    @Modifying
    @Transactional
    EmployeeDto saveEmployeeByLoginId(String loginId, EmployeeDto employeeDto);

    /* delete Employee */
    @Modifying
    @Transactional
    String deleteEmployeeByLoginId(String loginId);

    /* search Employees by EmpNo || EmpNm || DeptNm */
    List<Employee> searchEmployeesEmpNoOrEmpNmOrDeptNm(String value);

    /* search Employees by department */
    List<Employee> getEmpsByDept(String deptCd);

    List<EmployeeDto> getEmpDeptList();
}
