package com.marketplace.products.web.model;

import com.marketplace.products.domain.Category;
import com.marketplace.products.domain.Status;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class ProductDto {
    @NotNull(message = "Invalid username")
    @Size(min = 4, max = 25, message = "username length must be longer than 4 and shorter than 25 characters")
    private String ownerUserName;

    @NotBlank(message = "Invalid name")
    private String name;

    @NotNull
    private Category category;

    @NotNull(message = "Invalid price")
    @Min(value = 0, message = "Invalid price")
    private BigDecimal price;

    private List<MultipartFile> pictures;
    private String description;
    private Status status;
    private Set<String> tags;
}
