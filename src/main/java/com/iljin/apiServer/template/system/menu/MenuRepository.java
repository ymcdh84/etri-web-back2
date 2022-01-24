package com.iljin.apiServer.template.system.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.support.QueryMixin.Role.FROM;
import static org.hibernate.loader.Loader.SELECT;

@Repository
public interface MenuRepository extends JpaRepository<Menu, MenuKey> {
    @Query(value = "" +
            "SELECT menu.menuNo AS menuNo" +
            "       ,menu.menuNm AS menuNm" +
            "       ,menu.programFileNm AS programFileNm" +
            "       ,menu.upperMenuNo AS upperMenuNo" +
            "       ,menu.menuOrder AS menuOrder" +
            "  FROM Menu menu" +
            "       INNER JOIN MenuAuth mr ON mr.menuNo = menu.menuNo" +
            " WHERE mr.roleCd = :roleCd")
    List<Object[]> getMenuListByAuthority(@Param("roleCd") String roleCd);

    @Query(value = "" +
            "SELECT menu.compCd" +
            "       ,menu.menuNo" +
            "       ,menu.upperMenuNo" +
            "       ,menu.menuNm" +
            "       ,menu.programFileNm" +
            "       ,menu.menuOrder" +
            "       ,menu.relateImageNm" +
            "       ,menu.relateImagePath" +
            "       ,menu.menuDc" +
            "  FROM Menu menu" +
            " WHERE menu.menuNm LIKE '%' || ( CASE WHEN :menuNm IS NULL THEN '' ELSE :menuNm END ) || '%'" +
            " ORDER BY menu.menuNo ASC")
    List<Object[]> getMenuListByMenuNm(@Param("menuNm") String menuNm);
}
