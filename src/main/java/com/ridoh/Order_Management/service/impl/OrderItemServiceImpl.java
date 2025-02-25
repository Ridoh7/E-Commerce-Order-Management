package com.ridoh.Order_Management.service.impl;

import com.ridoh.Order_Management.dto.OrderItemDto;
import com.ridoh.Order_Management.dto.OrderRequest;
import com.ridoh.Order_Management.dto.Response;
import com.ridoh.Order_Management.entity.Order;
import com.ridoh.Order_Management.entity.OrderItem;
import com.ridoh.Order_Management.entity.Product;
import com.ridoh.Order_Management.entity.User;
import com.ridoh.Order_Management.enums.OrderStatus;
import com.ridoh.Order_Management.exception.NotFoundException;
import com.ridoh.Order_Management.mapper.EntityDtoMapper;
import com.ridoh.Order_Management.repository.OrderItemRepo;
import com.ridoh.Order_Management.repository.OrderRepo;
import com.ridoh.Order_Management.repository.ProductRepo;
import com.ridoh.Order_Management.service.Interface.OrderItemService;
import com.ridoh.Order_Management.service.Interface.UserService;
import com.ridoh.Order_Management.specification.OrderItemSpecification;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link OrderItemService} interface.
 * Handles order item management, including order placement, status updates, and filtering.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final UserService userService;
    private final EntityDtoMapper entityDtoMapper;
    private final EmailService emailService;

    /**
     * Places a new order based on the given {@link OrderRequest}.
     * Validates products, calculates total price, and persists the order.
     * Sends a confirmation email to the user upon successful placement.
     *
     * @param orderRequest The order request containing items to be purchased.
     * @return A {@link Response} indicating the result of the operation.
     */
    @Override
    public Response placeOrder(OrderRequest orderRequest) {
        User user = userService.getLoginUser();

        // Map order request items to order entities
        List<OrderItem> orderItems = orderRequest.getItems().stream().map(orderItemRequest -> {
            Product product = productRepo.findById((long) orderItemRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product Not Found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity())));
            orderItem.setStatus(OrderStatus.PENDING);
            orderItem.setUser(user);
            return orderItem;
        }).collect(Collectors.toList());

        // Calculate the total price
        BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
                ? orderRequest.getTotalPrice()
                : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order entity
        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);

        // Set the order reference in each order item
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        // Send order confirmation email
        try {
            String subject = "Order Confirmation - #" + order.getId();
            StringBuilder body = new StringBuilder();
            body.append("<h2>Hi ").append(user.getName()).append(",</h2>");
            body.append("<p>Your order <b>#").append(order.getId()).append("</b> has been successfully placed.</p>");
            body.append("<p><strong>Order Summary:</strong></p><ul>");

            for (OrderItem item : orderItems) {
                body.append("<li>").append(item.getProduct().getName())
                        .append(" - ").append(item.getQuantity())
                        .append(" x ").append(item.getPrice()).append("</li>");
            }

            body.append("</ul>");
            body.append("<p>Total Price: <b>").append(totalPrice).append("</b></p>");
            body.append("<p>Thank you for shopping with us!</p>");

            emailService.sendEmail(user.getEmail(), subject, body.toString());
        } catch (MessagingException e) {
            log.error("Failed to send order confirmation email", e);
        }

        orderRepo.save(order);

        return Response.builder()
                .status(200)
                .message("Order was successfully placed")
                .build();
    }

    /**
     * Updates the status of an existing order item.
     *
     * @param orderItemId The ID of the order item to be updated.
     * @param status      The new status of the order item.
     * @return A {@link Response} indicating the result of the operation.
     * @throws NotFoundException if the order item does not exist.
     */
    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {
        OrderItem orderItem = orderItemRepo.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order Item not found"));

        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepo.save(orderItem);

        return Response.builder()
                .status(200)
                .message("Order status updated successfully")
                .build();
    }

    /**
     * Filters order items based on status, date range, and item ID.
     *
     * @param status    The status of the order items to filter.
     * @param startDate The start date for filtering order items.
     * @param endDate   The end date for filtering order items.
     * @param itemId    The specific item ID to filter (optional).
     * @param pageable  Pagination details for the query.
     * @return A paginated {@link Response} containing the filtered order items.
     * @throws NotFoundException if no matching order items are found.
     */
    @Override
    public Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {
        Specification<OrderItem> spec = Specification.where(OrderItemSpecification.hasStatus(status))
                .and(OrderItemSpecification.createdBetween(startDate, endDate))
                .and(OrderItemSpecification.hasItemId(itemId));

        Page<OrderItem> orderItemPage = orderItemRepo.findAll(spec, pageable);

        if (orderItemPage.isEmpty()) {
            throw new NotFoundException("No Order Found");
        }

        List<OrderItemDto> orderItemDtos = orderItemPage.getContent().stream()
                .map(entityDtoMapper::mapOrderItemToDtoPlusProductAndUser)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .data(orderItemDtos)
                .totalPage(orderItemPage.getTotalPages())
                .totalElement(orderItemPage.getTotalElements())
                .build();
    }
}
