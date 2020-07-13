package com.marketplace.products.web.controller;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.services.ProductService;
import com.marketplace.products.web.model.SearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/products/")
@Validated
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "add")
    public ResponseEntity addProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);

        return ResponseEntity.created(URI.create("localhost:8081/" + createdProduct.getId())).build();
    }

    @GetMapping(value = "{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable String productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping(value = "{ownerUserName}")
    public ResponseEntity<List<Product>> getProductByOwnerId(@PathVariable  @NotBlank String ownerUserName,
                                                             @RequestParam Integer pageNumber) {
        return ResponseEntity.ok(productService.getProductsByOwnerUserName(ownerUserName, pageNumber));
    }

    @GetMapping(value = "{category}")
    public ResponseEntity<List<Product>> getProductById(@PathVariable @NotNull Category category,
                                                        @RequestParam Integer pageNumber) {
        return ResponseEntity.ok(productService.getProductsByCategory(category, pageNumber));
    }

    @GetMapping(value = "search")
    public ResponseEntity<List<Product>> getProductsBySearch(SearchParams searchParams, Integer pageNumber) {

        return ResponseEntity.ok(productService.getProductsBySearchProperties(searchParams, pageNumber));
    }
}
