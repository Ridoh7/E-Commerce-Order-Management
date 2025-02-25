package com.ridoh.Order_Management.service.Interface;

import com.ridoh.Order_Management.dto.OrderRequest;
import com.ridoh.Order_Management.dto.Response;
import com.ridoh.Order_Management.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Service interface for managing order items within the order management system.
 */
public interface OrderItemService {

    /**
     * Places a new order based on the provided order request.
     *
     * @param orderRequest the order request containing order details
     * @return a response indicating the success or failure of the order placement
     */
    Response placeOrder(OrderRequest orderRequest);

    /**
     * Updates the status of a specific order item.
     *
     * @param orderItemId the ID of the order item to be updated
     * @param status the new status to be assigned to the order item
     * @return a response indicating the success or failure of the update operation
     */
    Response updateOrderItemStatus(Long orderItemId, String status);

    /**
     * Filters order items based on the provided criteria, such as order status, date range, and item ID.
     *
     * @param status the status of the order items to filter (optional)
     * @param startDate the start date for filtering order items (optional)
     * @param endDate the end date for filtering order items (optional)
     * @param itemId the ID of a specific item to filter (optional)
     * @param pageable pagination and sorting details for the result set
     * @return a response containing the filtered order items
     */
    Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);
}
