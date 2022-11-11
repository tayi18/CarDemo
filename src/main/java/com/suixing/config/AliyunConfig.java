package com.suixing.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "aliyun")
public class AliyunConfig {
    private String endpoint = "";
    private String accessKeyId = "";
    private String accessKeySecret = "";
    private String bucketName = "";
    private String urlPrefix = "";

    @Bean
    public OSS oSSClient() {
        OSS oss = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return oss;
    }
}
