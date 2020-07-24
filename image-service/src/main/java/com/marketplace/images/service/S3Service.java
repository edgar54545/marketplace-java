package com.marketplace.images.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.marketplace.images.config.S3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Config s3Config;
    private AmazonS3 s3client;

    @PostConstruct
    private void initS3Client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(s3Config.getAccessKey(), s3Config.getSecret());
        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, MultipartFile file) throws IOException {
        s3client.putObject(new PutObjectRequest(s3Config.getBucketName(), fileName, file.getInputStream(),
                new ObjectMetadata())
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public URL uploadImage(MultipartFile file) {
        try {
            String fileName = generateFileName(file);
            uploadFileTos3bucket(fileName, file);
            return new URL(s3Config.getEndpointUrl() + "/" + s3Config.getBucketName() + "/" + fileName);
        } catch (Exception e) {
            log.error("Error occurred during saving image: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteImage(String fileUrl) {
        s3client.deleteObject(s3Config.getBucketName(), nameFromUrl(fileUrl));
    }

    public void deleteImage(List<String> fileUrls) {
        s3client.deleteObjects(new DeleteObjectsRequest(s3Config.getBucketName())
                .withKeys(fileUrls.stream()
                        .map(this::nameFromUrl)
                        .map(key -> new DeleteObjectsRequest.KeyVersion(key, null))
                        .collect(Collectors.toList()))
        );
    }

    private String nameFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }
}
