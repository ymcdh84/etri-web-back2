package com.iljin.apiServer.template.bigdata.connect;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BigdataConnectRepository extends JpaRepository<BigdataConnect, BigdataConnectKey> {
    @Query(value = "" +
            "SELECT bc.siteId" +
            "       ,bc.connectId" +
            "       ,bc.connectNm" +
            "       ,bc.connectTypeCd" +
            "       ,bc.connectStatCd" +
            "       ,bc.programNm" +
            "       ,bc.ip" +
            "       ,bc.port" +
            "       ,bc.loginId" +
            "       ,bc.password" +
            "       ,bc.startDateTime" +
            "       ,bc.endDateTime" +
            "  FROM BigdataConnect bc" +
            " WHERE 1=1" +
            "   AND bc.siteId LIKE CONCAT('%', ifnull(:siteId,''), '%')" +
            "   AND (bc.connectId LIKE CONCAT('%', ifnull(:connectId,''), '%')" +
            "        OR bc.connectNm LIKE CONCAT('%', ifnull(:connectId,''), '%'))" +
            "   AND bc.connectTypeCd = :connectTypeCd" +
            " ORDER BY bc.siteId,bc.connectId")
    List<Object[]> getConnectList(@Param("siteId") String siteId, @Param("connectId") String connectId, @Param("connectTypeCd") String connectTypeCd);

    List<BigdataConnect> findBySiteId(String siteId);
}
