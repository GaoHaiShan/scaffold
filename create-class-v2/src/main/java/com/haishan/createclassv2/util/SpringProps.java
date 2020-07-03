package com.haishan.createclassv2.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring")
public class SpringProps {
    private Map<String,String> application;
    private Map<String,String> redis;
    private Map<String,String> datasource;

    public Map<String, String> getApplication() {
        return application;
    }

    public void setApplication(Map<String, String> application) {
        this.application = application;
    }

    public Map<String, String> getRedis() {
        return redis;
    }

    public void setRedis(Map<String, String> redis) {
        this.redis = redis;
    }

    public Map<String, String> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, String> datasource) {
        this.datasource = datasource;
    }
}
