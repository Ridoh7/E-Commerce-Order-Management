package com.ridoh.Order_Management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing an Address in the order management system.
 * This class maps to the "address" table in the database.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    /**
     * Unique identifier for the address.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Street address.
     */
    private String street;

    /**
     * City of the address.
     */
    private String city;

    /**
     * State or region of the address.
     */
    private String state;

    /**
     * ZIP or postal code.
     */
    private String zipCode;

    /**
     * Country of the address.
     */
    private String country;

    /**
     * Associated user for the address.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Timestamp indicating when the address record was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
