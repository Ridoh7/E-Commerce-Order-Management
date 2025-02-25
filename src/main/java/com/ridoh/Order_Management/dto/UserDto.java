package com.ridoh.Order_Management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for User entity.
 * Represents user-related information used for communication between different layers of the application.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    /**
     * Unique identifier for the user.
     */
    private Long id;

    /**
     * Full name of the user.
     */
    private String name;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * Contact phone number of the user.
     */
    private String phoneNumber;

    /**
     * Password of the user (should be handled securely and not exposed in responses).
     */
    private String password;

    /**
     * Role of the user within the system (e.g., ADMIN, CUSTOMER).
     */
    private String role;

    /**
     * List of order items associated with the user.
     */
    private List<OrderItemDto> orderItemList;

    /**
     * Address information associated with the user.
     */
    private AddressDto address;
}
