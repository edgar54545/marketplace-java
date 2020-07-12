package com.marketplace.products.repository;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> getProductByName(String name);

    Page<Product> getProductsByCategory(Category category, Pageable pageable);

    Page<Product> getProductsByOwnerId(String ownerId, Pageable pageable);
}
