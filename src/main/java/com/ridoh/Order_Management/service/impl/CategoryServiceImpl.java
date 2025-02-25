package com.ridoh.Order_Management.service.impl;

import com.ridoh.Order_Management.dto.CategoryDto;
import com.ridoh.Order_Management.dto.Response;
import com.ridoh.Order_Management.entity.Category;
import com.ridoh.Order_Management.exception.NotFoundException;
import com.ridoh.Order_Management.mapper.EntityDtoMapper;
import com.ridoh.Order_Management.repository.CategoryRepo;
import com.ridoh.Order_Management.service.Interface.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the CategoryService interface.
 * This class provides methods to manage product categories.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;

    /**
     * Creates a new category if it does not already exist.
     *
     * @param categoryRequest DTO containing category details.
     * @return Response indicating success or failure.
     */
    @Override
    public Response createCategory(CategoryDto categoryRequest) {
        String categoryName = categoryRequest.getName().trim().toLowerCase();

        // Check if category already exists
        if (categoryRepo.findByNameIgnoreCase(categoryName).isPresent()) {
            return Response.builder()
                    .status(400)
                    .message("Category with this name already exists")
                    .build();
        }

        Category category = new Category();
        category.setName(categoryRequest.getName().trim());
        categoryRepo.save(category);

        return Response.builder()
                .status(201)
                .message("Category created successfully")
                .build();
    }

    /**
     * Updates an existing category if it exists.
     *
     * @param categoryId      ID of the category to update.
     * @param categoryRequest DTO containing updated category details.
     * @return Response indicating success or failure.
     */
    @Override
    public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        String newName = categoryRequest.getName().trim().toLowerCase();

        // Check if category name is already used by another category
        Optional<Category> existingCategory = categoryRepo.findByNameIgnoreCase(newName);
        if (existingCategory.isPresent() && !existingCategory.get().getId().equals(categoryId)) {
            return Response.builder()
                    .status(400)
                    .message("Category with this name already exists")
                    .build();
        }

        category.setName(categoryRequest.getName().trim());
        categoryRepo.save(category);

        return Response.builder()
                .status(200)
                .message("Category updated successfully")
                .build();
    }

    /**
     * Retrieves all categories.
     *
     * @return Response containing a list of all categories.
     */
    @Override
    public Response getAllCategories() {
        List<CategoryDto> categoryDtoList = categoryRepo.findAll().stream()
                .map(entityDtoMapper::mapCategoryToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .data(categoryDtoList)
                .build();
    }

    /**
     * Retrieves a specific category by its ID.
     *
     * @param categoryId ID of the category to retrieve.
     * @return Response containing category details.
     */
    @Override
    public Response getCategoryById(Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        CategoryDto categoryDto = entityDtoMapper.mapCategoryToDtoBasic(category);
        return Response.builder()
                .status(200)
                .data(categoryDto)
                .build();
    }

    /**
     * Deletes a category by its ID.
     *
     * @param categoryId ID of the category to delete.
     * @return Response indicating success or failure.
     */
    @Override
    public Response deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        categoryRepo.delete(category);

        return Response.builder()
                .status(200)
                .message("Category deleted successfully")
                .build();
    }
}
