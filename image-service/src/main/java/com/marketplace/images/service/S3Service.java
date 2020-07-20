package com.marketplace.images.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.marketplace.images.config.S3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(s3Config.getBucketName(), fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public URL uploadFile(MultipartFile multipartFile) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            uploadFileTos3bucket(fileName, file);
            file.delete();
            return new URL(s3Config.getEndpointUrl() + "/" + s3Config.getBucketName() + "/" + fileName);
        } catch (Exception e) {
            log.error("Error occured during saving image: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteFile(String key) {
        s3client.deleteObject(s3Config.getBucketName(), key);
    }

    public void deleteFiles(List<String> keys) {
        s3client.deleteObjects(new DeleteObjectsRequest(s3Config.getBucketName())
                .withKeys(keys.stream()
                        .map(key -> new DeleteObjectsRequest.KeyVersion(key, null))
                        .collect(Collectors.toList()))
        );
    }
}
