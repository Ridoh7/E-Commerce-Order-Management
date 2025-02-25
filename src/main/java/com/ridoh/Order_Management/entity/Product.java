package com.ridoh.Order_Management.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a product in the order management system.
 */
@Data
@Entity
@Table(name = "products")
public class Product {

    /**
     * Unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * A brief description of the product.
     */
    private String description;

    /**
     * URL to the product image.
     */
    private String imageUrl;

    /**
     * The price of the product.
     */
    private BigDecimal price;

    /**
     * The category to which this product belongs.
     * Establishes a many-to-one relationship with the Category entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Timestamp indicating when the product was added to the system.
     * Defaults to the current date and time.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
