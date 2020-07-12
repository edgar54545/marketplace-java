package com.marketplace.products.services;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//TODO handle Exceptions properly, create SearchDTO and Implement with MongoTemplate
public class ProductServiceImpl implements ProductService {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer PAGE_SIZE = 10;

    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Product addProduct(Product product) {
        product.setCreatedDate(new Date());
        return mongoTemplate.save(product);
    }

    @Override
    public Product getProductById(String productId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(productId));
        Optional<Product> productOpt = Optional.ofNullable(mongoTemplate.findOne(query, Product.class));

        return productOpt.orElseThrow(RuntimeException::new);
    }

    public Product getProductByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Optional<Product> productOpt = Optional.ofNullable(mongoTemplate.findOne(query, Product.class));

        return productOpt.orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Product> getProductsByCategory(Category category, Integer pageNumber) {
        Objects.requireNonNull(category);
        Integer pageNumberTemp = pageNumber == null ? DEFAULT_PAGE_NUMBER : pageNumber;

        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category))
                .with(PageRequest.of(pageNumberTemp, PAGE_SIZE));

        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public List<Product> getProductsByOwnerId(String ownerId, Integer pageNumber) {
        Objects.requireNonNull(ownerId);
        Integer pageNumberTemp = pageNumber == null ? DEFAULT_PAGE_NUMBER : pageNumber;

        return productRepository.getProductsByOwnerId(ownerId, PageRequest.of(pageNumberTemp, PAGE_SIZE))
                .getContent();
    }

}
