package com.ridoh.Order_Management.repository;

import com.ridoh.Order_Management.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for managing OrderItem entities.
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for
 * OrderItem entities, such as saving, updating, deleting, and retrieving
 * order items from the database.
 * </p>
 *
 * <p>
 * Additionally, it implements JpaSpecificationExecutor, allowing the execution
 * of dynamic queries using the Specification API.
 * </p>
 *
 * Example usage:
 * <pre>
 *     OrderItem orderItem = new OrderItem();
 *     orderItem.setQuantity(2);
 *     orderItem.setPrice(5000.00);
 *     orderItemRepo.save(orderItem);
 * </pre>
 *
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 */
public interface OrderItemRepo extends JpaRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {
}
