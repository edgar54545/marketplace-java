package com.marketplace.products.services;

import com.marketplace.products.config.ImageServiceRibbonConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RibbonClient(name = "image-service", configuration = ImageServiceRibbonConfig.class)
public class ImageServiceImpl implements ImageService {
    private static final String IMAGE_SERVICE_SAVE_ENDPOINT = "http://IMAGE-SERVICE/storage/uploadImages";
    private static final String FILES_KEY = "files";

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    @Autowired
    public ImageServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    }

    @Override
    public List<URL> saveImages(List<MultipartFile> files) {
        if (CollectionUtils.isEmpty(files)) {
            log.debug("No Images Found");
            return Collections.emptyList();
        }

        MultiValueMap<String, Object> body = createBody(files);
        HttpEntity<MultiValueMap<String, Object>> httpEntity =
                new HttpEntity<>(body, headers);

        URL[] imageUrls = restTemplate.exchange(IMAGE_SERVICE_SAVE_ENDPOINT, HttpMethod.POST,
                httpEntity, URL[].class).getBody();

        return imageUrls != null ? Arrays.asList(imageUrls) : Collections.emptyList();
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
