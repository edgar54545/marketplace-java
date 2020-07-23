package com.marketplace.products.services;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.web.model.ProductRequest;
import com.marketplace.products.web.model.SearchRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    String add(ProductRequest productRequest, List<MultipartFile> multipartFile);

    Product productById(String productId);

    Product update(String id, ProductRequest productRequest, List<MultipartFile> multipartFiles);

    Product getProductByName(String name, String ownerId);

    List<Product> productsByCategory(Category category, Integer pageNumber);

    List<Product> productsByOwnerUserName(String ownerUserName, Integer pageNumber);

    List<Product> productsBySearchProperties(SearchRequest searchRequest, Integer pageNumber);

    void delete(String id);
}
