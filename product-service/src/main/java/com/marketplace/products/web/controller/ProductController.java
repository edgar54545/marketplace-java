package com.marketplace.products.web.controller;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.services.ProductService;
import com.marketplace.products.web.model.SearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
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
    public ResponseEntity addProduct(@RequestBody @Valid Product product, UriComponentsBuilder uriBuilder) {
        Product createdProduct = productService.add(product);
        URI location = uriBuilder.path("api/v1/products/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "{id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @RequestBody @Valid Product product) {
        productService.update(id, product);
    }

    @DeleteMapping(value = "{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        productService.delete(id);
    }

    @GetMapping(value = "{productId}")
    public ResponseEntity<Product> product(@PathVariable String productId) {
        return ResponseEntity.ok(productService.productById(productId));
    }

    @GetMapping(value = "{ownerUserName}")
    public ResponseEntity<List<Product>> productByOwnerId(@PathVariable String ownerUserName,
                                                          @RequestParam Integer pageNumber) {
        return ResponseEntity.ok(productService.productsByOwnerUserName(ownerUserName, pageNumber));
    }

    @GetMapping(value = "{category}")
    public ResponseEntity<List<Product>> productById(@PathVariable @NotNull Category category,
                                                     @RequestParam Integer pageNumber) {
        return ResponseEntity.ok(productService.productsByCategory(category, pageNumber));
    }

    @GetMapping(value = "search")
    public ResponseEntity<List<Product>> productsBySearch(SearchParams searchParams, Integer pageNumber) {

        return ResponseEntity.ok(productService.productsBySearchProperties(searchParams, pageNumber));
    }
}
