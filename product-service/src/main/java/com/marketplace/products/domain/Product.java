package com.marketplace.products.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime lastModifiedDate;

    @Indexed
    @NotNull(message = "Invalid username")
    @Size(min = 4, max = 25, message = "username length must be longer than 4 and shorter than 25 characters")
    private String ownerUserName;

    @Indexed
    @NotBlank(message = "Invalid name")
    private String name;

    @NotNull
    private Category category;

    @NotNull(message = "Invalid price")
    @Min(value = 0, message = "Invalid price")
    private BigDecimal price;
    private List<URL> pictures;
    private String description;
    private Status status;
    private Set<String> tags;
}
