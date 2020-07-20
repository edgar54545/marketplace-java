package com.marketplace.products.web.model;

import com.marketplace.products.domain.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SearchRequest {
    private BigDecimal startPrice;
    private BigDecimal finalPrice;
    private Category category;
    private List<String> tags;
}
