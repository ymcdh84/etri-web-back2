package com.iljin.apiServer.template.system.menu;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MenuRepositoryCustomImpl implements MenuRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MenuDto> getMenuList(MenuDto menuDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("" +
                "SELECT MN.COMP_CD," +
                "       MN.MENU_NO," +
                "       MN.UPPER_MENU_NO," +
                "       MN.MENU_NM," +
                "       MN.PROGRAM_FILE_NM," +
                "       MN.MENU_ORDR," +
                "       MN.RELATE_IMAGE_NM," +
                "       MN.RELATE_IMAGE_PATH," +
                "       MN.MENU_DC" +
                "  FROM A_MENU MN" +
                " WHERE MN.MENU_NM LIKE CONCAT('%', IFNULL(:menuNm, ''), '%')" +
                " ORDER BY MN.MENU_NO ASC");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("menuNm", menuDto.getMenuNm());

        return new JpaResultMapper().list(query, MenuDto.class);
    }
}
