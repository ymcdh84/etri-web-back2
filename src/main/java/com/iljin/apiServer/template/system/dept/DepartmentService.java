package com.iljin.apiServer.template.system.dept;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<DepartmentDto> getDepartmentsByCombo();

    Optional<Department> getDepartmentByDeptNo(String deptCd);
}
