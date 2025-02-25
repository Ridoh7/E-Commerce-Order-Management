package com.ridoh.Order_Management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * General Response DTO for API responses.
 * This class is used to encapsulate response details such as status, message, timestamp, and various data objects.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    /**
     * HTTP status code of the response.
     */
    private int status;

    /**
     * Message describing the response status.
     */
    private String message;

    /**
     * Timestamp of when the response was generated.
     */
    private final LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Authentication token if applicable.
     */
    private String token;

    /**
     * Role associated with the user.
     */
    private String role;

    /**
     * Token expiration time.
     */
    private String expirationTime;

    /**
     * Total number of pages for paginated responses.
     */
    private int totalPage;

    /**
     * Total number of elements for paginated responses.
     */
    private long totalElement;

    /**
     * Generic data field to hold various response objects (e.g., Address, User, Category, etc.).
     */
    private Object data;
}
