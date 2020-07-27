package com.marketplace.products.repository;

import com.marketplace.products.constants.Constants;
import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Product;
import com.marketplace.products.web.model.SearchRequest;
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
    public Product add(Product product) {
        return mongoTemplate.save(product);
    }

    @Override
    public Product productById(String productId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.ID).is(productId));

        return mongoTemplate.findOne(query, Product.class);
    }

    @Override
    public Product update(String id, Product product) {
        product.setId(id);
        return mongoTemplate.save(product);
    }

    @Override
    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.ID).is(id));

        mongoTemplate.remove(query, Product.class);
    }

    @Override
    public Product productByName(String name, String ownerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.NAME).is(name)
                .and(Constants.OWNER_ID).is(ownerId));

        return mongoTemplate.findOne(query, Product.class);
    }

    @Override
    public List<Product> productsByCategory(Category category, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.CATEGORY).is(category))
                .with(pageable);

        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public List<Product> productsByOwnerUserName(String ownerUserName, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.OWNER_USERNAME).is(ownerUserName))
                .with(pageable);

        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public List<Product> productsBySearch(SearchRequest searchRequest, Pageable pageRequest) {
        Criteria searchCriteria = Criteria.where(Constants.PRICE).gt(searchRequest.getStartPrice())
                .lte(searchRequest.getFinalPrice());

        if (searchRequest.getCategory() != null) {
            searchCriteria.and(Constants.CATEGORY).is(searchRequest.getCategory());
        }

        if (!CollectionUtils.isEmpty(searchRequest.getTags())) {
            searchCriteria.and(Constants.TAGS).in(searchRequest.getTags());
        }

        Query query = new Query();
        query.addCriteria(searchCriteria).with(pageRequest);

        return mongoTemplate.find(query, Product.class);
    }
}
