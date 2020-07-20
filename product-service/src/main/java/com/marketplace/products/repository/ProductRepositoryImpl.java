package com.marketplace.products.repository;

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
        query.addCriteria(Criteria.where("_id").is(productId));

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
        query.addCriteria(Criteria.where("_id").is(id));

        mongoTemplate.remove(query, Product.class);
    }

    @Override
    public Product productByName(String name, String ownerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name)
                .and("ownerId").is(ownerId));

        return mongoTemplate.findOne(query, Product.class);
    }

    @Override
    public List<Product> productsByCategory(Category category, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category))
                .with(pageable);

        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public List<Product> productsByOwnerUserName(String ownerUserName, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ownerUserName").is(ownerUserName))
                .with(pageable);

        return mongoTemplate.find(query, Product.class);
    }

    @Override
    public List<Product> productBySearch(SearchRequest searchRequest, Pageable pageRequest) {
        Criteria searchCriteria = Criteria.where("price").gt(searchRequest.getStartPrice())
                .lte(searchRequest.getFinalPrice());

        if (searchRequest.getCategory() != null) {
            searchCriteria.and("category").is(searchRequest.getCategory());
        }

        if (!CollectionUtils.isEmpty(searchRequest.getTags())) {
            searchCriteria.and("tags").in(searchRequest.getTags());
        }

        Query query = new Query();
        query.addCriteria(searchCriteria).with(pageRequest);

        return mongoTemplate.find(query, Product.class);
    }
}
