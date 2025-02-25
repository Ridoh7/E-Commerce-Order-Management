package com.ridoh.Order_Management.entity;

import com.ridoh.Order_Management.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents an item in an order.
 * Each order item is linked to a specific product and user,
 * and it belongs to a particular order.
 */
@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    /**
     * Unique identifier for the order item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Quantity of the product ordered.
     */
    private int quantity;

    /**
     * Price of the product at the time of order.
     */
    private BigDecimal price;

    /**
     * Status of the order item (e.g., PENDING, SHIPPED, DELIVERED, CANCELLED).
     */
    private OrderStatus status;

    /**
     * The user who placed the order.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The product associated with this order item.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * The order that this item belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * The timestamp when this order item was created.
     */
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
}
