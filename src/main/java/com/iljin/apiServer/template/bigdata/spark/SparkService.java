package com.iljin.apiServer.template.bigdata.spark;

public interface SparkService {
    public Boolean insertTestMongoDb();

    public Boolean excuteStartTopic(String site_id, String topic_id, String offset, String submission);

//    public Boolean excuteStopTopic(String site_id, String topic_id, String offset, String submission);
}