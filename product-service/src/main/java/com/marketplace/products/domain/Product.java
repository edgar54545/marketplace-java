package com.marketplace.products.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private Date createdDate;
    private Date lastModifiedDate;

    private String ownerId;
    private String name;
    private Category category;
    private BigDecimal price;
    private List<String> pictures;
    private String description;
}
