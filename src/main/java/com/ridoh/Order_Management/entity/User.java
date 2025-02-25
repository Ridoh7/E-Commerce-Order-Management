package com.ridoh.Order_Management.entity;

import com.ridoh.Order_Management.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class representing a user in the order management system.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the user. This field is required.
     */
    @NotBlank(message = "Name is required")
    private String name;

    /**
     * Email address of the user. This field is unique and required.
     */
    @Column(unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    /**
     * Password for authentication. This field is required.
     */
    @NotBlank(message = "Password is required")
    private String password;

    /**
     * Phone number of the user. This field is required.
     */
    @Column(name = "phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    /**
     * Role of the user in the system, represented as an enum.
     */
    @Enumerated(EnumType.STRING)
    private UserRole role;

    /**
     * List of order items associated with the user.
     * Establishes a one-to-many relationship with the OrderItem entity.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;

    /**
     * Address associated with the user.
     * Establishes a one-to-one relationship with the Address entity.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    /**
     * Timestamp indicating when the user was created.
     * Defaults to the current date and time.
     */
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
}
