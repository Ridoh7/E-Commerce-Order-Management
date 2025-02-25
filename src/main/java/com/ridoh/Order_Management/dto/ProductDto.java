package com.ridoh.Order_Management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for Product information.
 * This class is used to transfer product-related data between different layers of the application.
 * It includes fields for storing product details such as name, description, price, image URL, and category.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    /**
     * Unique identifier for the product.
     */
    private Long id;

    /**
     * Name of the product.
     */
    private String name;

    /**
     * Description of the product.
     */
    private String description;

    /**
     * Price of the product.
     */
    private BigDecimal price;

    /**
     * URL of the product image.
     */
    private String imageUrl;

    /**
     * Category to which the product belongs.
     */
    private CategoryDto category;
}
