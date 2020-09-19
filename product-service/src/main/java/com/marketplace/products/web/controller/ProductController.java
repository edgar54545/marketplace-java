package com.marketplace.products.web.controller;

import com.marketplace.products.domain.Category;
import com.marketplace.products.dtos.ProductRequest;
import com.marketplace.products.dtos.ProductResponse;
import com.marketplace.products.dtos.SearchRequest;
import com.marketplace.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity save(@RequestPart(value = "product") @Valid ProductRequest productRequest,
                               @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {

        String createdProductId = productService.add(productRequest, multipartFiles);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("api/v1/products/{id}")
                .buildAndExpand(createdProductId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @RequestPart(value = "product") @Valid ProductRequest productRequest,
                       @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {
        productService.update(id, productRequest, multipartFiles);
    }

    @DeleteMapping(value = "/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        productService.delete(id);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable String id) {
        return ResponseEntity.ok(productService.productById(id));
    }

    @GetMapping(value = "/ownerUsername")
    public ResponseEntity<List<ProductResponse>> productByOwnerUsername(@RequestParam("username") String ownerUserName,
                                                                        @RequestParam Integer pageNumber) {
        return ResponseEntity.ok(productService.productsByOwnerUserName(ownerUserName, pageNumber));
    }

    @GetMapping(value = "/category")
    public ResponseEntity<List<ProductResponse>> productsByCategory(@RequestParam("category") Category category,
                                                                    @RequestParam Integer pageNumber) {
        return ResponseEntity.ok(productService.productsByCategory(category, pageNumber));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<ProductResponse>> productsBySearch(SearchRequest searchRequest, Integer pageNumber) {

        return ResponseEntity.ok(productService.productsBySearchProperties(searchRequest, pageNumber));
    }
}
