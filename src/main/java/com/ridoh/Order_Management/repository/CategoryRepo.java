package com.ridoh.Order_Management.repository;

import com.ridoh.Order_Management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
