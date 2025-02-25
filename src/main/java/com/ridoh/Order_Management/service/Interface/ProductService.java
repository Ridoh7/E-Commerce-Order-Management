package com.ridoh.Order_Management.service.Interface;

import com.ridoh.Order_Management.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * Service interface for managing products within the order management system.
 */
public interface ProductService {

    /**
     * Creates a new product under a specified category.
     *
     * @param categoryId the ID of the category to which the product belongs
     * @param image the image file for the product
     * @param name the name of the product
     * @param description the description of the product
     * @param price the price of the product
     * @return a response indicating the success or failure of the product creation
     */
    Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price);

    /**
     * Updates an existing product with new details.
     *
     * @param productId the ID of the product to be updated
     * @param categoryId the ID of the updated category
     * @param image the updated image file for the product
     * @param name the updated name of the product
     * @param description the updated description of the product
     * @param price the updated price of the product
     * @return a response indicating the success or failure of the product update
     */
    Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price);

    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to be deleted
     * @return a response indicating the success or failure of the deletion
     */
    Response deleteProduct(Long productId);

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product to be retrieved
     * @return a response containing the product details
     */
    Response getProductById(Long productId);

    /**
     * Retrieves a list of all available products.
     *
     * @return a response containing the list of all products
     */
    Response getAllProducts();

    /**
     * Retrieves all products belonging to a specific category.
     *
     * @param categoryId the ID of the category for filtering products
     * @return a response containing the list of products in the specified category
     */
    Response getProductsByCategory(Long categoryId);

    /**
     * Searches for products based on a search keyword.
     *
     * @param searchValue the search keyword for filtering products
     * @return a response containing the matching products
     */
    Response searchProduct(String searchValue);
}
