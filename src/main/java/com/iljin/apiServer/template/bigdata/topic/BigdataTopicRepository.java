package com.iljin.apiServer.template.bigdata.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BigdataTopicRepository extends JpaRepository<BigdataTopic, BigdataTopicKey> {
    @Query(value = "" +
            "SELECT bt.siteId" +
            "       ,bt.connectId" +
            "       ,bt.topicId" +
            "       ,bt.topicDesc" +
            "       ,bt.submissionId" +
            "       ,bt.submissionStatCd" +
            "  FROM BigdataTopic bt" +
            " WHERE 1=1" +
            "   AND (bt.siteId LIKE CONCAT('%', ifnull(:siteId,''), '%'))" +
            "   AND (bt.connectId LIKE CONCAT('%', ifnull(:connectId,''), '%'))" +
            " ORDER BY bt.siteId, bt.connectId, bt.topicId")
    List<Object[]> getTopicList(@Param("siteId") String siteId, @Param("connectId") String connectId);
}
