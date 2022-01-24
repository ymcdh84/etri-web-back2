package com.iljin.apiServer.template.system.dept;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, DepartmentKey> {
    @Query(value = "" +
            "SELECT dept.deptNo" +
            "       ,dept.deptName" +
            "       ,dept.admrDeptNo" +
            "       ,a.deptName" +
            "  FROM Department dept" +
            "       LEFT OUTER JOIN Department a ON a.deptNo = dept.admrDeptNo" +
            " ORDER BY dept.deptNo ASC")
    List<Object[]> getDepartmentsByCombo();

    Optional<Department> findByDeptNo(String deptCd);
}
