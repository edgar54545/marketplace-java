package com.marketplace.products.repository;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.web.model.SearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public Product addProduct(Product product) {
        return mongoTemplate.save(product);
    }

    @Override
    public Product getProductById(String productId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(productId));

        return mongoTemplate.findOne(query, Product.class);
    }

    @Override
    public Product getProductByName(String name, String ownerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name)
                .and("ownerId").is(ownerId));

        return mongoTemplate.findOne(query, Product.class);
    }

    @Override
    public List<Product> getProductsByCategory(Category category, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category))
                .with(pageable);

        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public List<Product> getProductsByOwnerUserName(String ownerUserName, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ownerUserName").is(ownerUserName))
                .with(pageable);

        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public List<Product> getProductBySearch(SearchParams searchParams, Pageable pageRequest) {
        Criteria searchCriteria = Criteria.where("price").gt(searchParams.getStartPrice())
                .lte(searchParams.getFinalPrice());

        if(searchParams.getCategory() != null) {
            searchCriteria.and("category").is(searchParams.getCategory());
        }

        if(!CollectionUtils.isEmpty(searchParams.getTags())) {
            searchCriteria.and("tags").in(searchParams.getTags());
        }

        Query query = new Query();
        query.addCriteria(searchCriteria).with(pageRequest);

        return mongoTemplate.find(query, Product.class);
    }
}
