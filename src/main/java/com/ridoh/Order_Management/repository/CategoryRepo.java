package com.ridoh.Order_Management.repository;

import com.ridoh.Order_Management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for managing Category entities.
 * <p>
 * This interface extends JpaRepository, providing built-in CRUD operations
 * for the Category entity, such as saving, deleting, and retrieving categories
 * from the database.
 * </p>
 *
 * <p>
 * The generic parameters specify the entity type (Category) and
 * the primary key type (Long).
 * </p>
 *
 * Example usage:
 * <pre>
 *     Category category = new Category();
 *     category.setName("Electronics");
 *     categoryRepo.save(category);
 * </pre>
 */
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findByNameIgnoreCase(String name); // Added method
}
