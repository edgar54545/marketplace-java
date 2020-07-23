package com.marketplace.products.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class MultipartResolverConfig {
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver() {
            @Override
            public boolean isMultipart(HttpServletRequest request) {
                String method = request.getMethod().toLowerCase();
                //By default, only POST is allowed. Since this is an 'update' we should accept PUT.
                if (!Arrays.asList("put", "post").contains(method)) {
                    return false;
                }
                String contentType = request.getContentType();
                return (contentType != null &&contentType.toLowerCase().startsWith("multipart/"));
            }
        };
    }
}
