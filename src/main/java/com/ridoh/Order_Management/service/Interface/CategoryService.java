package com.ridoh.Order_Management.service.Interface;

import com.ridoh.Order_Management.dto.CategoryDto;
import com.ridoh.Order_Management.dto.Response;

/**
 * Service interface for managing product categories.
 */
public interface CategoryService {

    /**
     * Creates a new product category.
     *
     * @param categoryRequest the category data transfer object containing category details
     * @return a response indicating the success or failure of the operation
     */
    Response createCategory(CategoryDto categoryRequest);

    /**
     * Updates an existing product category.
     *
     * @param categoryId the ID of the category to be updated
     * @param categoryRequest the category data transfer object containing updated category details
     * @return a response indicating the success or failure of the update operation
     */
    Response updateCategory(Long categoryId, CategoryDto categoryRequest);

    /**
     * Retrieves a list of all product categories.
     *
     * @return a response containing the list of all categories
     */
    Response getAllCategories();

    /**
     * Retrieves details of a specific category by its ID.
     *
     * @param categoryId the ID of the category to be retrieved
     * @return a response containing the category details
     */
    Response getCategoryById(Long categoryId);

    /**
     * Deletes a product category by its ID.
     *
     * @param categoryId the ID of the category to be deleted
     * @return a response indicating the success or failure of the deletion operation
     */
    Response deleteCategory(Long categoryId);
}
