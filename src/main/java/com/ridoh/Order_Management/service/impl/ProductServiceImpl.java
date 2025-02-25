package com.ridoh.Order_Management.service.impl;

import com.ridoh.Order_Management.dto.ProductDto;
import com.ridoh.Order_Management.dto.Response;
import com.ridoh.Order_Management.entity.Category;
import com.ridoh.Order_Management.entity.Product;
import com.ridoh.Order_Management.exception.NotFoundException;
import com.ridoh.Order_Management.mapper.EntityDtoMapper;
import com.ridoh.Order_Management.repository.CategoryRepo;
import com.ridoh.Order_Management.repository.ProductRepo;
import com.ridoh.Order_Management.service.AwsS3Service;
import com.ridoh.Order_Management.service.Interface.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ProductService interface.
 * This service handles product-related operations such as creating, updating,
 * deleting, retrieving, and searching for products.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;
    private final AwsS3Service awsS3Service;

    /**
     * Creates a new product.
     * @param categoryId The ID of the category.
     * @param image The product image file.
     * @param name The name of the product.
     * @param description The description of the product.
     * @param price The price of the product.
     * @return Response indicating success or failure.
     */
    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        String productImageUrl = awsS3Service.saveImageToS3(image);

        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product successfully created")
                .build();
    }

    /**
     * Updates an existing product.
     * @param productId The ID of the product.
     * @param categoryId The ID of the new category (if updating).
     * @param image The updated product image file.
     * @param name The updated product name.
     * @param description The updated product description.
     * @param price The updated product price.
     * @return Response indicating success or failure.
     */
    @Override
    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));

        if (categoryId != null) {
            Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
            product.setCategory(category);
        }

        if (image != null && !image.isEmpty()) {
            String productImageUrl = awsS3Service.saveImageToS3(image);
            product.setImageUrl(productImageUrl);
        }

        if (name != null) product.setName(name);
        if (price != null) product.setPrice(price);
        if (description != null) product.setDescription(description);

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product updated successfully")
                .build();
    }

    /**
     * Deletes a product by its ID.
     * @param productId The ID of the product to delete.
     * @return Response indicating success or failure.
     */
    @Override
    public Response deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        productRepo.delete(product);

        return Response.builder()
                .status(200)
                .message("Product deleted successfully")
                .build();
    }

    /**
     * Retrieves a product by its ID.
     * @param productId The ID of the product.
     * @return Response containing the product details.
     */
    @Override
    public Response getProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);

        return Response.builder()
                .status(200)
                .data(productDto)
                .build();
    }

    /**
     * Retrieves all products.
     * @return Response containing a list of products.
     */
    @Override
    public Response getAllProducts() {
        List<ProductDto> productList = productRepo.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .data(productList)
                .build();
    }

    /**
     * Retrieves products by category ID.
     * @param categoryId The ID of the category.
     * @return Response containing a list of products.
     */
    @Override
    public Response getProductsByCategory(Long categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new NotFoundException("No Products found for this category");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .data(productDtoList)
                .build();
    }

    /**
     * Searches for products by name or description.
     * @param searchValue The keyword to search for.
     * @return Response containing matching products.
     */
    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepo.findByNameContainingOrDescriptionContaining(searchValue, searchValue);

        if (products.isEmpty()) {
            throw new NotFoundException("No Products Found");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .data(productDtoList)
                .build();
    }
}
