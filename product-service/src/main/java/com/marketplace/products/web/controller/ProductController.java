package com.marketplace.products.web.controller;

import com.marketplace.products.domain.Product;
import com.marketplace.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "product/add")
    public ResponseEntity addProduct(@RequestBody Product product) {
        Product product1 = productService.addProduct(product);

        return ResponseEntity.created(URI.create("localhost:8081/" + product1.getId())).build();
    }

    @GetMapping(value = "product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable String productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }
}
