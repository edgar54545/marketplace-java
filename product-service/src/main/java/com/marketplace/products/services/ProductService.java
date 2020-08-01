package com.marketplace.products.services;

import com.marketplace.products.domain.Category;
import com.marketplace.products.dtos.ProductRequest;
import com.marketplace.products.dtos.ProductResponse;
import com.marketplace.products.dtos.SearchRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    String add(ProductRequest productRequest, List<MultipartFile> multipartFile);

    ProductResponse productById(String productId);

    ProductResponse update(String id, ProductRequest productRequest, List<MultipartFile> multipartFiles);

    ProductResponse getProductByName(String name, String ownerId);

    List<ProductResponse> productsByCategory(Category category, Integer pageNumber);

    List<ProductResponse> productsByOwnerUserName(String ownerUserName, Integer pageNumber);

    List<ProductResponse> productsBySearchProperties(SearchRequest searchRequest, Integer pageNumber);

    void delete(String id);
}
