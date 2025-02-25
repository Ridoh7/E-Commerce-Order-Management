package com.ridoh.Order_Management.service.Interface;


import com.ridoh.Order_Management.dto.CategoryDto;
import com.ridoh.Order_Management.dto.Response;

public interface CategoryService {

    Response createCategory(CategoryDto categoryRequest);
    Response updateCategory(Long categoryId, CategoryDto categoryRequest);
    Response getAllCategories();
    Response getCategoryById(Long categoryId);
    Response deleteCategory(Long categoryId);
}
