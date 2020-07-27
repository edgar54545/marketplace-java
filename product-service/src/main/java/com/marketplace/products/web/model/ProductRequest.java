package com.marketplace.products.web.model;

import com.marketplace.products.constants.Constants;
import com.marketplace.products.domain.Category;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductRequest {
    @NotNull(message = Constants.INVALID_USERNAME)
    @Size(min = 4, max = 25, message = Constants.INVALID_USERNAME_SIZE)
    private String ownerUserName;

    @NotBlank(message = Constants.INVALID_NAME)
    private String name;

    @NotNull(message = Constants.INVALID_CATEGORY)
    private Category category;

    @NotNull(message = Constants.INVALID_PRICE)
    @Min(value = 0, message = Constants.INVALID_PRICE)
    private BigDecimal price;
    private String description;
    private Set<String> tags;
}
