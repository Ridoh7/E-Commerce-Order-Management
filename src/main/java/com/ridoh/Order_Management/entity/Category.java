package com.ridoh.Order_Management.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class representing a product category in the order management system.
 * This class maps to the "categories" table in the database.
 */
@Data
@Entity
@Table(name = "categories")
public class Category {

    /**
     * Unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the category, which must be unique.
     */
    @Column(unique = true)
    private String name;

    /**
     * List of products associated with this category.
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> productList;

    /**
     * Timestamp indicating when the category record was created.
     */
    @Column(name= "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
