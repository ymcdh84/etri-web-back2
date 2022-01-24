package com.iljin.apiServer.template.system.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeHeaderRepository extends JpaRepository<CodeHeader, CodeHeaderKey> {
    @Query(value = "" +
            "SELECT ch.compCd" +
            "       ,ch.groupCd" +
            "       ,ch.groupNm" +
            "       ,ch.useYn" +
            "       ,ch.groupDesc" +
            "  FROM CodeHeader ch" +
            " WHERE 1=1" +
            "   AND ch.compCd = :compCd" +
            "   AND (ch.groupCd LIKE CONCAT('%', :groupCd, '%')" +
            "        OR ch.groupNm LIKE CONCAT('%', :groupCd, '%'))" +
            "   AND ch.useYn LIKE CONCAT('%', :useYn, '%')" +
            " ORDER BY ch.groupCd ASC")
	List<Object[]> getGroupCodeList(@Param("compCd") String compCd, @Param("groupCd") String groupCd, @Param("useYn") String useYn);
}
