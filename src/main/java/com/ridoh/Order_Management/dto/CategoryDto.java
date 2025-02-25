package com.ridoh.Order_Management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Category information.
 * This class is used to transfer category-related data between different layers of the application.
 * It includes fields for storing category details such as name and associated products.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    /**
     * Unique identifier for the category.
     */
    private Long id;

    /**
     * Name of the category.
     */
    private String name;

    /**
     * List of products associated with this category.
     */
    private List<ProductDto> productList;
}