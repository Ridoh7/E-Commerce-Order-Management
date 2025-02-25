package com.ridoh.Order_Management.exception;

/**
 * Exception thrown when a user provides invalid credentials during authentication.
 * This is a custom runtime exception that extends {@link RuntimeException}.
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructs a new InvalidCredentialsException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
