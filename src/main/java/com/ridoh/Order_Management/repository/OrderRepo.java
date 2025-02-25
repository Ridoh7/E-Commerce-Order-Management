package com.ridoh.Order_Management.repository;

import com.ridoh.Order_Management.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Order entities.
 * <p>
 * This interface extends JpaRepository, providing built-in CRUD operations
 * for handling Order records in the database.
 * </p>
 *
 * Example usage:
 * <pre>
 *     Order order = new Order();
 *     order.setStatus(OrderStatus.PENDING);
 *     orderRepo.save(order);
 * </pre>
 *
 * @see JpaRepository
 */
public interface OrderRepo extends JpaRepository<Order, Long> {
}
