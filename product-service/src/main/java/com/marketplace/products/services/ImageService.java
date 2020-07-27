package com.marketplace.products.services;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

public interface ImageService {
    List<URL> saveImages(List<MultipartFile> files);
}
