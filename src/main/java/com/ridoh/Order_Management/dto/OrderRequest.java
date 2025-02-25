package com.ridoh.Order_Management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ridoh.Order_Management.entity.Payment;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object (DTO) for handling order requests.
 * This class is used to encapsulate order details when a new order is being placed.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {

    /**
     * Total price of the order.
     */
    private BigDecimal totalPrice;

    /**
     * List of items included in the order.
     */
    private List<OrderItemRequest> items;

    /**
     * Payment information associated with the order.
     */
    private Payment paymentInfo;
}
