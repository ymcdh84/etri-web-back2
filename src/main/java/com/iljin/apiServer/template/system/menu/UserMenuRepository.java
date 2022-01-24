package com.iljin.apiServer.template.system.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMenuRepository extends JpaRepository<UserMenu, UserMenuKey> {

    @Query(value = "" +
            "SELECT um.menuNo" +
            "       ,m.menuNm" +
            "       ,m.programFileNm" +
            "       ,um.menuIconCd" +
            "  FROM UserMenu um" +
            "       LEFT JOIN Menu m ON m.menuNo = um.menuNo" +
            " WHERE um.userId = :userId" +
            " ORDER BY um.menuOrdr")
    List<Object[]> getCustomQuickMenuList(@Param("userId") String loginId);

    void deleteByCompCdAndUserId(String compCd, String loginId);

    @Query(value = "" +
            "SELECT CASE WHEN LENGTH(um.menuNo) > 0 THEN '1' ELSE '0' END AS REG_YN" +
            "       ,menu.menuNo" +
            "       ,CONCAT(CONCAT(m.menuNm, ' - '), menu.menuNm) AS MENU_NM" +
            "       ,um.menuIconCd" +
            "  FROM Menu menu" +
            "       LEFT OUTER JOIN UserMenu um ON um.menuNo = menu.menuNo AND um.userId = :loginId" +
            "       INNER JOIN Menu m ON m.menuNo = menu.upperMenuNo" +
            " WHERE 1=1" +
            "   AND (CASE WHEN menu.upperMenuNo IS NULL THEN '0' ELSE menu.upperMenuNo END) <> '0'" +
            " ORDER BY CASE WHEN LENGTH(um.menuNo) > 0 THEN '1' ELSE '0' END DESC" +
            "          ,menu.menuNo ASC ")
    List<Object[]> getUserSettings(@Param("loginId") String loginId);
}
