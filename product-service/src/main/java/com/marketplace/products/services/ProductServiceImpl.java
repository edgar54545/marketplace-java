package com.marketplace.products.services;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.domain.Status;
import com.marketplace.products.repository.ProductRepository;
import com.marketplace.products.web.errors_handle.EntityNotFoundException;
import com.marketplace.products.web.model.SearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Override
    public Product add(Product product) {
        product.setCreatedDate(LocalDateTime.now());
        product.setStatus(Status.PENDING);

        return productRepository.add(product);
    }

    @Override
    public Product productById(String productId) {
        Optional<Product> productOpt = Optional.ofNullable(productRepository.productById(productId));

        return productOpt.orElseThrow(() -> new EntityNotFoundException(productId));
    }

    @Override
    public Product update(String id, Product product) {
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

        return productOpt.orElseThrow(() -> new EntityNotFoundException(name));
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
    public List<Product> productsBySearchProperties(SearchParams searchParams, Integer pageNumber) {
        if(searchParams.getStartPrice() == null) {
            searchParams.setStartPrice(START_PRICE);
        }

        if (searchParams.getFinalPrice() == null) {
            searchParams.setFinalPrice(FINAL_PRICE);
        }

        return productRepository.productBySearch(searchParams, pageRequest(pageNumber));
    }

    private Pageable pageRequest(Integer pageNumber) {
        return pageNumber == null ? PageRequest.of(DEFAULT_PAGE_NUMBER, PAGE_SIZE) :
                PageRequest.of(pageNumber, PAGE_SIZE);
    }
}
