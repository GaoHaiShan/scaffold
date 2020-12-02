package com.haishan.util;import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "upload")
public class FileProps {

    // 上传文件存放目录
    private Map<String,String> file;

    public Map<String, String> getFile() {
        return file;
    }

    public void setFile(Map<String, String> file) {
        this.file = file;
    }
}