package com.ridoh.Order_Management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for handling login requests.
 * This class is used to encapsulate the user-provided credentials during authentication.
 */
@Data
public class LoginRequest {

    /**
     * User's email address, required for authentication.
     */
    @NotBlank(message = "Email is required")
    private String email;

    /**
     * User's password, required for authentication.
     */
    @NotBlank(message = "Password is required")
    private String password;
}
