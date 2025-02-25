package com.ridoh.Order_Management.repository;

import com.ridoh.Order_Management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Product entities.
 * <p>
 * This interface extends JpaRepository, providing CRUD operations
 * and custom query methods for handling Product records in the database.
 * </p>
 *
 * Example usage:
 * <pre>
 *     List<Product> productsByCategory = productRepo.findByCategoryId(1L);
 *     List<Product> searchResults = productRepo.findByNameContainingOrDescriptionContaining("Laptop", "Gaming");
 * </pre>
 *
 * @see JpaRepository
 */
public interface ProductRepo extends JpaRepository<Product, Long> {

    /**
     * Finds all products belonging to a specific category.
     *
     * @param categoryId the ID of the category
     * @return a list of products associated with the given category ID
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * Searches for products whose name or description contains the specified keywords.
     *
     * @param name        the keyword to search in the product name
     * @param description the keyword to search in the product description
     * @return a list of matching products
     */
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
}
