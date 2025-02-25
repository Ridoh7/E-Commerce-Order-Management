package com.ridoh.Order_Management.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing the payment details for an order.
 */
@Table(name = "payment")
@Entity
@Data
public class Payment {

    /**
     * Unique identifier for the payment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The amount paid for the order.
     */
    private BigDecimal amount;

    /**
     * The payment method used (e.g., Credit Card, PayPal, Bank Transfer).
     */
    private String method;

    /**
     * The status of the payment (e.g., Pending, Completed, Failed).
     */
    private String status;

    /**
     * The order associated with this payment.
     * A one-to-one relationship exists between an order and its payment.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Timestamp indicating when the payment record was created.
     * Defaults to the current date and time.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
