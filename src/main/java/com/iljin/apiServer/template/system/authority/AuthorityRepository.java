package com.iljin.apiServer.template.system.authority;

import com.iljin.apiServer.core.security.role.Role;
import com.iljin.apiServer.core.security.role.RoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Role, RoleKey> {
    @Query(value = "" +
            "SELECT role.roleCd," +
            "       role.compCd," +
            "       role.roleNm," +
            "       role.roleSelectCd," +
            "       dt.detailNm AS roleSelectNm" +
            "  FROM Role role" +
            "       LEFT OUTER JOIN CodeDetail dt ON dt.compCd = role.compCd AND dt.groupCd = 'ROLE_SELECT_CD' AND dt.useYn = 'Y' AND dt.detailCd = role.roleSelectCd" +
            " WHERE role.compCd = :compCd")
    List<Object[]> getAuthoritiesByCompCd(@Param("compCd") String compCd);

    @Query(value = "" +
            "SELECT CASE WHEN mr.roleCd = :roleCd THEN '1' ELSE '0' END AS ROLE_CK" +
            "       ,mr.roleCd" +
            "       ,menu.menuNo " +
            "       ,menu.menuNm " +
            "       ,CONCAT(CONCAT(CONCAT('LV ', LTRIM(LENGTH(REPLACE(menu.menuNo, '0', '')))), ' - '), menu.menuNm) AS MENU_DC" +
            "       ,mr.compCd" +
            "       ,LENGTH(REPLACE(menu.menuNo, '0', '')) AS menuLv" +
            "       ,menu.menuOrder" +
            "       ,menu.upperMenuNo        " +
            "  FROM Menu menu" +
            "       LEFT OUTER JOIN MenuAuth mr ON mr.roleCd = :roleCd AND mr.compCd = :compCd AND mr.menuNo = menu.menuNo" +
            " WHERE 1=1" +
            "   AND menu.menuNo <> 0" +
            " ORDER BY menu.menuNo, menu.menuOrder ")
    List<Object[]> getMenuByAuthority(@Param("compCd") String compCd, @Param("roleCd") String roleCd);

    @Query(value = "" +
            "SELECT CASE" +
            "         WHEN ur.role = :roleCd THEN '1'" +
            "         ELSE '0' END AS ROLE_CHK," +
            "       ur.role AS CRT_ROLE," +
            "       user.loginId," +
            "       user.userName," +
            "       emp.jobDutNm," +
            "       emp.deptNm," +
            "       user.compCd" +
            "  FROM User user" +
            "       LEFT OUTER JOIN Employee emp ON emp.compCd = user.compCd AND emp.empNo = user.loginId" +
            "       LEFT OUTER JOIN UserRole ur ON ur.compCd = user.compCd AND ur.userId = user.id" +
            " WHERE user.compCd = :compCd" +
            " ORDER BY CASE WHEN emp.upperDeptCd IS NULL THEN '0' ELSE emp.upperDeptCd END ASC, emp.deptCd ASC, LENGTH(emp.jobNm) DESC")
    List<Object[]> getUserInfoByAuthority(@Param("compCd") String compCd, @Param("roleCd") String roleCd);
}
