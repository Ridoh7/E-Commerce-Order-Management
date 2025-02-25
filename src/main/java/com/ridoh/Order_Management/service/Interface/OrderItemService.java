package com.ridoh.Order_Management.service.Interface;

import com.ridoh.Order_Management.dto.OrderRequest;
import com.ridoh.Order_Management.dto.Response;
import com.ridoh.Order_Management.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface OrderItemService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, String status);
    Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);
}