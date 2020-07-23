package com.marketplace.products.config;

import com.marketplace.products.domain.Product;
import com.marketplace.products.web.model.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MappersConfig {

    @Bean
    public ModelMapper productRequestToProductMapper() {
        ModelMapper modelMapper = configuredMapper();
        modelMapper.typeMap(ProductRequest.class, Product.class)
                .addMappings(mapper -> {
                    mapper.skip(Product::setId);
                    mapper.skip(Product::setCreatedDate);
                    mapper.skip(Product::setLastModifiedDate);
                    mapper.skip(Product::setStatus);
                    mapper.skip(Product::setPictures);
                });

        return modelMapper;
    }

    private ModelMapper configuredMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        return modelMapper;
    }
}
