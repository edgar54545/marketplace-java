package com.marketplace.images.controller;

import com.marketplace.images.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@RestController
@RequestMapping("/storage/")
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3Service;

    @PostMapping(value = "uploadImage")
    public ResponseEntity<URL> uploadImage(@RequestPart("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(s3Service.uploadImage(multipartFile));
    }

    @PostMapping(value = "uploadImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<URL>> uploadImages(@RequestPart(value = "files") List<MultipartFile> multipartFiles) {
        List<URL> savedFilesUrls = multipartFiles.stream()
                .map(s3Service::uploadImage)
                .collect(toUnmodifiableList());

        return ResponseEntity.ok(savedFilesUrls);
    }

    @PostMapping("deleteImage")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(@RequestBody DeleteRequest deleteRequest) {
        s3Service.deleteImage(deleteRequest.getKey());
    }

    @PostMapping("deleteImages")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFiles(@RequestBody DeleteRequest deleteRequest) {
        s3Service.deleteImage(deleteRequest.getKeys());
    }

}
