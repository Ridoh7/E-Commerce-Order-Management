package com.ridoh.Order_Management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Address information.
 * This class is used to transfer address-related data between different layers of the application.
 * It includes fields for storing address details such as street, city, state, zip code, and country,
 * along with a reference to the associated user and creation timestamp.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    /**
     * Unique identifier for the address.
     */
    private Long id;

    /**
     * Street address.
     */
    private String street;

    /**
     * City name.
     */
    private String city;

    /**
     * State or province name.
     */
    private String state;

    /**
     * Postal or ZIP code.
     */
    private String zipCode;

    /**
     * Country name.
     */
    private String country;

    /**
     * Associated user for this address.
     */
    private UserDto user;

    /**
     * Timestamp indicating when the address record was created.
     */
    private LocalDateTime createdAt;
}