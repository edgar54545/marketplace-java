package com.marketplace.products.dtos;

import com.marketplace.products.domain.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ProductResponse {
    private String id;
    private LocalDateTime createdDate;
    private String ownerUsername;
    private String name;
    private Category category;
    private BigDecimal price;
    private List<URL> pictures;
    private String description;
    private Set<String> tags;
}
