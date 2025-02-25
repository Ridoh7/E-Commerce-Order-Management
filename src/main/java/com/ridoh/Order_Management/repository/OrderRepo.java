package com.ridoh.Order_Management.repository;

import com.ridoh.Order_Management.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
