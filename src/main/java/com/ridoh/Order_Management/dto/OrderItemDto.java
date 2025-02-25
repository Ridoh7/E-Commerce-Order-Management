package com.ridoh.Order_Management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Order Item information.
 * This class is used to transfer order item-related data between different layers of the application.
 * It includes fields for storing order item details such as quantity, price, status, user, product, and creation timestamp.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    /**
     * Unique identifier for the order item.
     */
    private Long id;

    /**
     * Quantity of the product in the order.
     */
    private int quantity;

    /**
     * Price of the order item.
     */
    private BigDecimal price;

    /**
     * Status of the order item.
     */
    private String status;

    /**
     * User associated with this order item.
     */
    private UserDto user;

    /**
     * Product associated with this order item.
     */
    private ProductDto product;

    /**
     * Timestamp indicating when the order item was created.
     */
    private LocalDateTime createdAt;
}
