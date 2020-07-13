package com.marketplace.products.repository;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.web.model.SearchParams;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {
    Product addProduct(Product product);

    Product getProductById(String productId);

    Product getProductByName(String name, String ownerId);

    List<Product> getProductsByCategory(Category category, Pageable pageable);

    List<Product> getProductsByOwnerUserName(String ownerId, Pageable pageable);

    List<Product> getProductBySearch(SearchParams searchParams, Pageable pageRequest);
}
