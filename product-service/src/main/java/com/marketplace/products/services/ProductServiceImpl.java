package com.marketplace.products.services;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.domain.Status;
import com.marketplace.products.dtos.ProductRequest;
import com.marketplace.products.dtos.ProductResponse;
import com.marketplace.products.dtos.SearchRequest;
import com.marketplace.products.repository.ProductRepository;
import com.marketplace.products.web.errors_handle.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer PAGE_SIZE = 10;
    private static final BigDecimal START_PRICE = BigDecimal.ZERO;
    private static final BigDecimal FINAL_PRICE = BigDecimal.valueOf(Integer.MAX_VALUE);

    private final ProductRepository productRepository;
    private final ModelMapper productRequestToProductMapper;
    private final ModelMapper productToProductResponseMapper;
    private final ImageServiceImpl imageService;

    @Override
    public String add(ProductRequest productRequest, List<MultipartFile> multipartFiles) {
        Product product = productRequestToProductMapper.map(productRequest, Product.class);
        product.setCreatedDate(LocalDateTime.now());
        product.setStatus(Status.PENDING);
        product.setPictures(imageService.saveImages(multipartFiles));

        return productRepository.add(product).getId();
    }

    @Override
    public ProductResponse productById(String productId) {
        Product product = Optional.ofNullable(productRepository.productById(productId))
                .orElseThrow(() -> new NotFoundException(productId));

        return productToProductResponseMapper.map(product, ProductResponse.class);
    }

    @Override
    public ProductResponse update(String id, ProductRequest productRequest, List<MultipartFile> multipartFiles) {
        Product product = productRequestToProductMapper.map(productRequest, Product.class);
        product.setLastModifiedDate(LocalDateTime.now());
        product.setPictures(imageService.saveImages(multipartFiles));
        Product updatedProduct = productRepository.update(id, product);

        return productToProductResponseMapper.map(updatedProduct, ProductResponse.class);
    }

    @Override
    public void delete(String id) {
        productRepository.delete(id);
    }

    @Override
    public ProductResponse getProductByName(String name, String ownerId) {
        Product product = Optional.ofNullable(productRepository.productByName(name, ownerId))
                .orElseThrow(() -> new NotFoundException(name));

        return productToProductResponseMapper.map(product, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> productsByCategory(Category category, Integer pageNumber) {
        Objects.requireNonNull(category);

        return productRepository.productsByCategory(category, pageRequest(pageNumber)).stream()
                .map(product -> productToProductResponseMapper.map(product, ProductResponse.class))
                .collect(toUnmodifiableList());
    }

    @Override
    public List<ProductResponse> productsByOwnerUserName(String ownerUserName, Integer pageNumber) {
        return productRepository.productsByOwnerUserName(ownerUserName, pageRequest(pageNumber)).stream()
                .map(product -> productToProductResponseMapper.map(product, ProductResponse.class))
                .collect(toUnmodifiableList());
    }

    @Override
    public List<ProductResponse> productsBySearchProperties(SearchRequest searchRequest, Integer pageNumber) {
        if (searchRequest.getStartPrice() == null) {
            searchRequest.setStartPrice(START_PRICE);
        }

        if (searchRequest.getFinalPrice() == null) {
            searchRequest.setFinalPrice(FINAL_PRICE);
        }

        return productRepository.productsBySearch(searchRequest, pageRequest(pageNumber)).stream()
                .map(product -> productToProductResponseMapper.map(product, ProductResponse.class))
                .collect(toUnmodifiableList());
    }

    private Pageable pageRequest(Integer pageNumber) {
        return pageNumber == null ? PageRequest.of(DEFAULT_PAGE_NUMBER, PAGE_SIZE) :
                PageRequest.of(pageNumber, PAGE_SIZE);
    }
}
