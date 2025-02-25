package com.ridoh.Order_Management.enums;

/**
 * Enum representing the different statuses an order can have in the order management system.
 */
public enum OrderStatus {

    /** The order has been placed but not yet confirmed. */
    PENDING,

    /** The order has been confirmed and is being processed. */
    CONFIRMED,

    /** The order has been shipped to the customer. */
    SHIPPED,

    /** The order has been delivered to the customer successfully. */
    DELIVERED,

    /** The order has been cancelled before shipment. */
    CANCELLED,

    /** The customer has initiated a return request. */
    RETURNING
}
