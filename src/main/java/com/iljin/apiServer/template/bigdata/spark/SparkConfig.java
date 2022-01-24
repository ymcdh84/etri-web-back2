package com.iljin.apiServer.template.bigdata.spark;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SparkConfig {

    @Value("${spark.host}")
    private String sparkHost;

    @Value("${spark.port}")
    private String sparkPort;

    public String getSparkHost() {
        return sparkHost;
    }

    public String getSparkPort() {
        return sparkPort;
    }

    public WebClient getClient() {
        return WebClient.create("http://" + sparkHost + ":" + sparkPort);
    }
}
