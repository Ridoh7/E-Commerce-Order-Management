package com.ridoh.Order_Management.exception;

import com.ridoh.Order_Management.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for handling various exceptions across the application.
 * This class provides centralized exception handling using Spring's {@code @ControllerAdvice}.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all general exceptions that are not explicitly handled by other methods.
     *
     * @param ex      The exception that occurred.
     * @param request The web request context.
     * @return A ResponseEntity containing an error response with HTTP status 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleAllException(Exception ex, WebRequest request) {
        Response errorResponse = Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles cases where a requested resource is not found.
     *
     * @param ex      The NotFoundException thrown when a resource is missing.
     * @param request The web request context.
     * @return A ResponseEntity containing an error response with HTTP status 404 (Not Found).
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleNotFoundException(NotFoundException ex, WebRequest request) {
        Response errorResponse = Response.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles cases where a user provides invalid credentials.
     *
     * @param ex      The InvalidCredentialsException thrown when authentication fails.
     * @param request The web request context.
     * @return A ResponseEntity containing an error response with HTTP status 400 (Bad Request).
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Response> handleInvalidCredentialsException(InvalidCredentialsException ex, WebRequest request) {
        Response errorResponse = Response.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
