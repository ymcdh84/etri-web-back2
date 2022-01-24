package com.iljin.apiServer.template.bigdata.site;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BigdataSiteRepository extends JpaRepository<BigdataSite, String> {
    @Query(value = "" +
            "SELECT bs.siteId" +
            "       ,bs.siteNm" +
            "       ,bs.siteDesc" +
            "  FROM BigdataSite bs" +
            " WHERE 1=1" +
            "   AND (bs.siteId LIKE CONCAT('%', ifnull(:siteId,''), '%')" +
            "        OR bs.siteNm LIKE CONCAT('%', ifnull(:siteId,''), '%'))" +
            " ORDER BY bs.siteId ASC")
    List<Object[]> getSiteList(@Param("siteId") String siteId);
}
