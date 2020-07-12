package com.marketplace.products.services;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);

    Product getProductById(String productId);

    Product getProductByName(String name);

    List<Product> getProductsByCategory(Category category, Integer pageNumber);

    List<Product> getProductsByOwnerId(String ownerId, Integer pageNumber);
}
