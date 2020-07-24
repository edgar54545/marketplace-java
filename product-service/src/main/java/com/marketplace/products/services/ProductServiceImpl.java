package com.marketplace.products.services;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.domain.Status;
import com.marketplace.products.repository.ProductRepository;
import com.marketplace.products.web.errors_handle.NotFoundException;
import com.marketplace.products.web.model.ProductRequest;
import com.marketplace.products.web.model.SearchRequest;
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

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer PAGE_SIZE = 10;
    private static final BigDecimal START_PRICE = BigDecimal.ZERO;
    private static final BigDecimal FINAL_PRICE = BigDecimal.valueOf(Integer.MAX_VALUE);

    private final ProductRepository productRepository;
    private final ModelMapper productRequestToProductMapper;
    private final ImageService imageService;

    @Override
    public String add(ProductRequest productRequest, List<MultipartFile> multipartFiles) {
        Product product = productRequestToProductMapper.map(productRequest, Product.class);
        product.setCreatedDate(LocalDateTime.now());
        product.setStatus(Status.PENDING);
        product.setPictures(imageService.saveImages(multipartFiles));

        return productRepository.add(product).getId();
    }

    @Override
    public Product productById(String productId) {
        Optional<Product> productOpt = Optional.ofNullable(productRepository.productById(productId));

        return productOpt.orElseThrow(() -> new NotFoundException(productId));
    }

    @Override
    public Product update(String id, ProductRequest productRequest, List<MultipartFile> multipartFiles) {
        Product product = productRequestToProductMapper.map(productRequest, Product.class);
        product.setLastModifiedDate(LocalDateTime.now());
        product.setPictures(imageService.saveImages(multipartFiles));

        return productRepository.update(id, product);
    }

    @Override
    public void delete(String id) {
        productRepository.delete(id);
    }

    @Override
    public Product getProductByName(String name, String ownerId) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(ownerId);

        Optional<Product> productOpt = Optional.ofNullable(productRepository.productByName(name, ownerId));

        return productOpt.orElseThrow(() -> new NotFoundException(name));
    }

    @Override
    public List<Product> productsByCategory(Category category, Integer pageNumber) {
        Objects.requireNonNull(category);

        return productRepository.productsByCategory(category, pageRequest(pageNumber));
    }

    @Override
    public List<Product> productsByOwnerUserName(String ownerUserName, Integer pageNumber) {
        return productRepository.productsByOwnerUserName(ownerUserName, pageRequest(pageNumber));
    }

    @Override
    public List<Product> productsBySearchProperties(SearchRequest searchRequest, Integer pageNumber) {
        if (searchRequest.getStartPrice() == null) {
            searchRequest.setStartPrice(START_PRICE);
        }

        if (searchRequest.getFinalPrice() == null) {
            searchRequest.setFinalPrice(FINAL_PRICE);
        }

        return productRepository.productBySearch(searchRequest, pageRequest(pageNumber));
    }

    private Pageable pageRequest(Integer pageNumber) {
        return pageNumber == null ? PageRequest.of(DEFAULT_PAGE_NUMBER, PAGE_SIZE) :
                PageRequest.of(pageNumber, PAGE_SIZE);
    }
}
