package com.ridoh.Order_Management.specification;

import com.ridoh.Order_Management.entity.OrderItem;
import com.ridoh.Order_Management.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * This class provides specifications for filtering OrderItem entities
 * based on various criteria such as status, creation date range, and item ID.
 */
public class OrderItemSpecification {

    /**
     * Creates a Specification to filter OrderItem by status.
     *
     * @param status the status of the order item
     * @return a Specification for filtering by status, or null if status is not provided
     */
    public static Specification<OrderItem> hasStatus(OrderStatus status) {
        return (root, query, criteriaBuilder) ->
                status != null ? criteriaBuilder.equal(root.get("status"), status) : null;
    }

    /**
     * Creates a Specification to filter OrderItem entities by a date range.
     *
     * @param startDate the start date for filtering
     * @param endDate   the end date for filtering
     * @return a Specification for filtering by creation date range
     */
    public static Specification<OrderItem> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            } else if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            } else {
                return null;
            }
        };
    }

    /**
     * Creates a Specification to filter OrderItem by item ID.
     *
     * @param itemId the ID of the order item
     * @return a Specification for filtering by item ID, or null if itemId is not provided
     */
    public static Specification<OrderItem> hasItemId(Long itemId) {
        return (root, query, criteriaBuilder) ->
                itemId != null ? criteriaBuilder.equal(root.get("id"), itemId) : null;
    }
}
