package com.marketplace.products.services;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.web.model.SearchParams;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);

    Product getProductById(String productId);

    Product getProductByName(String name, String ownerId);

    List<Product> getProductsByCategory(Category category, Integer pageNumber);

    List<Product> getProductsByOwnerUserName(String ownerUserName, Integer pageNumber);

    List<Product> getProductsBySearchProperties(SearchParams searchParams, Integer pageNumber);
}
