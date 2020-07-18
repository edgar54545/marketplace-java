package com.marketplace.products.services;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.web.model.SearchParams;

import java.util.List;

public interface ProductService {

    Product add(Product product);

    Product productById(String productId);

    Product update(String id, Product product);

    Product getProductByName(String name, String ownerId);

    List<Product> productsByCategory(Category category, Integer pageNumber);

    List<Product> productsByOwnerUserName(String ownerUserName, Integer pageNumber);

    List<Product> productsBySearchProperties(SearchParams searchParams, Integer pageNumber);

    void delete(String id);
}
