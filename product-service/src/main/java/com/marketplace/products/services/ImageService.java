package com.marketplace.products.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ImageService {
    private static final String IMAGE_SERVICE_SAVE_ENDPOINT = "uploadImages";
    private static final String FILES_KEY = "files";

    private final RestTemplate restTemplate;
    private final String imageServicePath;
    private final HttpHeaders headers;

    public ImageService(RestTemplateBuilder restTemplateBuilder,
                        @Value("${image-service.path}") String imageServicePath,
                        ObjectMapper objectMapper) {
        this.imageServicePath = imageServicePath;

        headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        restTemplate = restTemplateBuilder
                .additionalMessageConverters(createMappingJacksonHttpMessageConverter(
                        configureObjectMapper(objectMapper)))
                .build();
    }

    public List<URL> saveImages(List<MultipartFile> files) {
        if (CollectionUtils.isEmpty(files)) {
            log.debug("No Images Found");
            return Collections.emptyList();
        }

        MultiValueMap<String, Object> body = createBody(files);

        HttpEntity<MultiValueMap<String, Object>> httpEntity =
                new HttpEntity<>(body, headers);

        URL[] imageUrls = restTemplate.postForObject(URI.create(imageServicePath + IMAGE_SERVICE_SAVE_ENDPOINT), httpEntity,
                URL[].class);

        return imageUrls != null ? Arrays.asList(imageUrls) : Collections.emptyList();
    }

    private ObjectMapper configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    private MappingJackson2HttpMessageConverter createMappingJacksonHttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    private MultiValueMap<String, Object> createBody(List<MultipartFile> files) {
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        files.forEach(file -> {
            try {
                body.add(FILES_KEY, new ByteArrayResource(file.getBytes()) {

                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename();
                    }

                    @Override
                    public long contentLength() {
                        return file.getSize();
                    }
                });
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        });
        return body;
    }

}
