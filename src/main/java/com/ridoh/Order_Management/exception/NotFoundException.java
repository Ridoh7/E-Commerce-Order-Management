package com.ridoh.Order_Management.exception;

/**
 * Exception thrown when a requested resource is not found.
 * This is a custom runtime exception that extends {@link RuntimeException}.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
