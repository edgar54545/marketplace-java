package com.marketplace.images.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import com.marketplace.images.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@RestController
@RequestMapping("/images/")
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3Service;

    @PostMapping(value = "uploadImage")
    public ResponseEntity<URL> uploadFile(@RequestPart("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(s3Service.uploadFile(multipartFile));
    }

    @PostMapping("uploadImages")
    public ResponseEntity<List<URL>> uploadFiles(@RequestPart(value = "files") List<MultipartFile> multipartFiles) {
        List<URL> savedFilesUrls = multipartFiles.stream()
                .map(s3Service::uploadFile)
                .collect(toUnmodifiableList());

        return ResponseEntity.ok(savedFilesUrls);
    }

    @PostMapping("deleteImage")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(@RequestBody DeleteRequest key) {
        s3Service.deleteFile(key.getKey());
    }

    @PostMapping("deleteImages")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFiles(@RequestBody DeleteRequest keys) {
        s3Service.deleteFiles(keys.getKeys());
    }

}
