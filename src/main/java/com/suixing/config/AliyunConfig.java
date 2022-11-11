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
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String accessKeyId = "LTAI5tJcxc4sabqZa5wtunZD";
    private String accessKeySecret = "njPeJWZtErSlvvkn1OK00caiINUivz";
    private String bucketName = "zhaoyulove";
    private String urlPrefix = "http://zhaoyulove.oss-cn-hangzhou.aliyuncs.com/";

    @Bean
    public OSS oSSClient() {
        OSS oss = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return oss;
    }
}