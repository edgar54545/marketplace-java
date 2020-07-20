package com.marketplace.images.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "amazon-s3")
@Getter
@Setter
public class S3Config {
    private String endpointUrl;
    private String accessKey;
    private String bucketName;
    private String secret;
}
