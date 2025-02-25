package com.ridoh.Order_Management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class representing an Order in the order management system.
 * This class maps to the "orders" table in the database.
 */
@Entity
@Data
@Table(name="orders")
public class Order {

    /**
     * Unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Total price of the order.
     */
    private BigDecimal totalPrice;

    /**
     * List of order items associated with this order.
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList;

    /**
     * Timestamp indicating when the order was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // PAYMENT (To be implemented in the future)
}