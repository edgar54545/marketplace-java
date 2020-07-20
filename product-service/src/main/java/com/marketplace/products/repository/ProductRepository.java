package com.marketplace.products.repository;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.web.model.SearchRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {
    Product add(Product product);

    Product productById(String productId);

    Product update(String id, Product product);

    void delete(String id);

    Product productByName(String name, String ownerId);

    List<Product> productsByCategory(Category category, Pageable pageable);

    List<Product> productsByOwnerUserName(String ownerId, Pageable pageable);

    List<Product> productBySearch(SearchRequest searchRequest, Pageable pageRequest);
}
