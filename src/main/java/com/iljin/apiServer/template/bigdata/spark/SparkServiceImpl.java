package com.iljin.apiServer.template.bigdata.spark;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class SparkServiceImpl implements SparkService {

    private static final Logger logger = LoggerFactory.getLogger(SparkServiceImpl.class);

    @Autowired
    private SparkConfig sparkConfig;

    @Override
    public Boolean excuteStartTopic(String site_id, String topic_id, String offset, String submission){

        /*
         * @param customerId - 고객사 코드(아이디) -> Elasticsearch index
         * @param requestBody(reqBody) - 질의문(조회조건) -> used Elasticsearch Search API
         * */
        String reqBody = "";
        reqBody += "" +
                "{\n" +
                "    \"appResource\": \"file:/usr/jar/spark.jar\",\n" +
                "    \"sparkProperties\": {\n" +
                "        \"spark.master\": \"spark://master:7077\",\n" +
                "        \"spark.core.max\": \"3\",\n" +
                "        \"spark.num.executor\": \"3\",\n" +
                "        \"spark.executors.cores\": \"1\",\n" +
                "        \"spark.executor.memory\": \"1GB\",\n" +
                "        \"spark.eventLog.enabled\": \"false\",\n" +
                "        \"spark.app.name\": \"Spark REST API - PI\",\n" +
                "        \"spark.jars\": \"file:/usr/jar/spark.jar\",\n" +
                "        \"spark.driver.supervise\": \"false\"\n" +
                "    },\n" +
                "    \"clientSparkVersion\": \"2.4.8\",\n" +
                "    \"mainClass\": \"com.iljin.speed.MachineExcelDataLayer\",\n" +
                "    \"action\": \"CreateSubmissionRequest\",\n" +
                "    \"environmentVariables\": {        \n" +
                "    },\n" +
                "    \"appArgs\": [\n" +
                "        \"" + site_id + "\", \n" +
                "        \"" + topic_id + "\", \n" +
                "        \"" + offset + "\"        \n" +
                "    ]\n" +
                "}";

        Map<String, Object> map = new HashMap<>();

        try {
            map = new ObjectMapper().readValue(reqBody, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String uri = "/v1/submissions/create";

        /**
         * Preparing a Request
         * Set a request body
         * */
        WebClient.RequestHeadersSpec<?> request = sparkConfig.getClient()
                .post()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("charset", "UTF-8")
                .syncBody(map);

        Map<String, Object> response = request.exchange()
                .block()
                .bodyToFlux(Map.class)
                .blockFirst();

        return (Boolean)response.get("success");
    }

    @Override
    public Boolean insertTestMongoDb(){

        String reqBody = "";
        reqBody += "" +
                "{\n" +
                "    \"appResource\": \"file:/usr/jar/spark.jar\",\n" +
                "    \"sparkProperties\": {\n" +
                "        \"spark.master\": \"spark://master:7077\",\n" +
                "        \"spark.core.max\": \"3\",\n" +
                "        \"spark.num.executor\": \"3\",\n" +
                "        \"spark.executors.cores\": \"1\",\n" +
                "        \"spark.executor.memory\": \"1GB\",\n" +
                "        \"spark.eventLog.enabled\": \"false\",\n" +
                "        \"spark.app.name\": \"Spark REST API - PI\",\n" +
                "        \"spark.jars\": \"file:/usr/jar/spark.jar\",\n" +
                "        \"spark.driver.supervise\": \"false\"\n" +
                "    },\n" +
                "    \"clientSparkVersion\": \"2.4.8\",\n" +
                "    \"mainClass\": \"com.iljin.test.insertTestMongoDb\",\n" +
                "    \"action\": \"CreateSubmissionRequest\",\n" +
                "    \"environmentVariables\": {        \n" +
                "    },\n" +
                "    \"appArgs\": [\n" +
                "    ]\n" +
                "}";

        Map<String, Object> map = new HashMap<>();

        try {
            map = new ObjectMapper().readValue(reqBody, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String uri = "/v1/submissions/create";

        /**
         * Preparing a Request
         * Set a request body
         * */
        WebClient.RequestHeadersSpec<?> request = sparkConfig.getClient()
                .post()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("charset", "UTF-8")
                .syncBody(map);

        Map<String, Object> response = request.exchange()
                .block()
                .bodyToFlux(Map.class)
                .blockFirst();

        return (Boolean)response.get("success");
    }

}