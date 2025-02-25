package com.ridoh.Order_Management.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity class representing a product review in the order management system.
 */
@Entity
@Data
@Table(name = "reviews")
public class Review {

    /**
     * Unique identifier for the review.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The content of the review provided by the user.
     */
    private String content;

    /**
     * The rating given by the user, ranging from 1 to 10.
     */
    private int rating; // rating from 1 to 10

    /**
     * The product associated with this review.
     * Establishes a many-to-one relationship with the Product entity.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * The user who submitted the review.
     * Establishes a many-to-one relationship with the User entity.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Timestamp indicating when the review was created.
     * Defaults to the current date and time.
     */
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
}
