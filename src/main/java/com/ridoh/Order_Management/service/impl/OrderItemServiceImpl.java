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


    @Override
    public Response placeOrder(OrderRequest orderRequest) {

        User user = userService.getLoginUser();
        //map order request items to order entities

        List<OrderItem> orderItems = orderRequest.getItems().stream().map(orderItemRequest -> {
            Product product = productRepo.findById((long) orderItemRequest.getProductId())
                    .orElseThrow(()-> new NotFoundException("Product Not Found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()))); //set price according to the quantity
            orderItem.setStatus(OrderStatus.PENDING);
            orderItem.setUser(user);
            return orderItem;

        }).collect(Collectors.toList());

        //calculate the total price
        BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
                ? orderRequest.getTotalPrice()
                : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        //create order entity
        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);

        //set the order reference in each orderitem
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
            e.printStackTrace(); // Handle error properly (logging, notifications, etc.)
        }

        orderRepo.save(order);

        return Response.builder()
                .status(200)
                .message("Order was successfully placed")
                .build();

    }

    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {
        OrderItem orderItem = orderItemRepo.findById(orderItemId)
                .orElseThrow(()-> new NotFoundException("Order Item not found"));

        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepo.save(orderItem);
        return Response.builder()
                .status(200)
                .message("Order status updated successfully")
                .build();
    }

    @Override
    public Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {
        Specification<OrderItem> spec = Specification.where(OrderItemSpecification.hasStatus(status))
                .and(OrderItemSpecification.createdBetween(startDate, endDate))
                .and(OrderItemSpecification.hasItemId(itemId));

        Page<OrderItem> orderItemPage = orderItemRepo.findAll(spec, pageable);

        if (orderItemPage.isEmpty()){
            throw new NotFoundException("No Order Found");
        }
        List<OrderItemDto> orderItemDtos = orderItemPage.getContent().stream()
                .map(entityDtoMapper::mapOrderItemToDtoPlusProductAndUser)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .orderItemList(orderItemDtos)
                .totalPage(orderItemPage.getTotalPages())
                .totalElement(orderItemPage.getTotalElements())
                .build();
    }
}