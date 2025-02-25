package com.ridoh.Order_Management.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for handling order item requests.
 * This class is used to encapsulate the product ID and quantity when creating an order item.
 */
@Data
public class OrderItemRequest {

    /**
     * The unique identifier of the product being ordered.
     */
    private int productId;

    /**
     * The quantity of the product being ordered.
     */
    private int quantity;
}
