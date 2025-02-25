package com.ridoh.Order_Management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ridoh.Order_Management.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) for Order information.
 * This class is used to transfer order-related data between different layers of the application.
 * It includes fields for storing order details such as total price, order items, and creation timestamp.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    /**
     * Unique identifier for the order.
     */
    private Long id;

    /**
     * Total price of the order.
     */
    private BigDecimal totalPrice;

    /**
     * List of items associated with the order.
     */
    private List<OrderItem> orderItemList;

    /**
     * Timestamp indicating when the order was created.
     */
    private LocalDateTime createdAt;
}
