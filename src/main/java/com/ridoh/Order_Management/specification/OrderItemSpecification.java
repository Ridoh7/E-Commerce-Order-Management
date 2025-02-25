package com.ridoh.Order_Management.specification;

import com.ridoh.Order_Management.entity.OrderItem;
import com.ridoh.Order_Management.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderItemSpecification {
    public static Specification<OrderItem>hasStatus(OrderStatus status){
        return ((root, query, criteriaBuilder) ->
                status != null? criteriaBuilder.equal(root.get("status"),status):null);
    }

    /** Generating Specification to filter OrderItem by Start and End Date*/
    public static Specification<OrderItem> createdBetween(LocalDateTime startDate, LocalDateTime endDate){
        return ((root, query, criteriaBuilder) ->
        {
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            } else if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            }else {
                return null;
            }
        });
    }
    /** Generating Specification to filter OrderItem by Id*/

    public static Specification<OrderItem> hasItemId(Long itemId){
        return ((root, query, criteriaBuilder) ->
                itemId != null? criteriaBuilder.equal(root.get("id"), itemId) : null);
    }
}
