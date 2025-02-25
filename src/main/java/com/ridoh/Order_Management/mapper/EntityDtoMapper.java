package com.ridoh.Order_Management.mapper;

import com.ridoh.Order_Management.dto.*;
import com.ridoh.Order_Management.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * This class is responsible for mapping entity objects to their corresponding DTO (Data Transfer Object) representations.
 * It helps in decoupling the domain model from the API layer by converting entities into lightweight DTOs.
 */
@Component
public class EntityDtoMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDto} containing basic user details.
     *
     * @param user The {@link User} entity to be converted.
     * @return A {@link UserDto} representation of the user.
     */
    public UserDto mapUserToDtoBasic(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setName(user.getName());
        return userDto;
    }

    /**
     * Converts an {@link Address} entity to an {@link AddressDto} containing basic address details.
     *
     * @param address The {@link Address} entity to be converted.
     * @return An {@link AddressDto} representation of the address.
     */
    public AddressDto mapAddressToDtoBasic(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());
        return addressDto;
    }

    /**
     * Converts a {@link Category} entity to a {@link CategoryDto} containing basic category details.
     * This includes the list of associated products.
     *
     * @param category The {@link Category} entity to be converted.
     * @return A {@link CategoryDto} representation of the category.
     */
    public CategoryDto mapCategoryToDtoBasic(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        // Include the list of associated products
        if (category.getProductList() != null) {
            categoryDto.setProductList(category.getProductList()
                    .stream()
                    .map(this::mapProductToDtoBasic)
                    .collect(Collectors.toList()));
        }

        return categoryDto;
    }

    /**
     * Converts an {@link OrderItem} entity to an {@link OrderItemDto} containing basic order item details.
     *
     * @param orderItem The {@link OrderItem} entity to be converted.
     * @return An {@link OrderItemDto} representation of the order item.
     */
    public OrderItemDto mapOrderItemToDtoBasic(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getStatus().name());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        return orderItemDto;
    }

    /**
     * Converts a {@link Product} entity to a {@link ProductDto} containing basic product details.
     *
     * @param product The {@link Product} entity to be converted.
     * @return A {@link ProductDto} representation of the product.
     */
    public ProductDto mapProductToDtoBasic(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }

    /**
     * Converts a {@link User} entity to a {@link UserDto}, including address details.
     *
     * @param user The {@link User} entity to be converted.
     * @return A {@link UserDto} representation including address details.
     */
    public UserDto mapUserToDtoPlusAddress(User user) {
        UserDto userDto = mapUserToDtoBasic(user);
        if (user.getAddress() != null) {
            AddressDto addressDto = mapAddressToDtoBasic(user.getAddress());
            userDto.setAddress(addressDto);
        }
        return userDto;
    }

    /**
     * Converts an {@link OrderItem} entity to an {@link OrderItemDto}, including product details.
     *
     * @param orderItem The {@link OrderItem} entity to be converted.
     * @return An {@link OrderItemDto} representation including product details.
     */
    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem) {
        OrderItemDto orderItemDto = mapOrderItemToDtoBasic(orderItem);

        if (orderItem.getProduct() != null) {
            ProductDto productDto = mapProductToDtoBasic(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }

    /**
     * Converts an {@link OrderItem} entity to an {@link OrderItemDto}, including product and user details.
     *
     * @param orderItem The {@link OrderItem} entity to be converted.
     * @return An {@link OrderItemDto} representation including product and user details.
     */
    public OrderItemDto mapOrderItemToDtoPlusProductAndUser(OrderItem orderItem) {
        OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem);

        if (orderItem.getUser() != null) {
            UserDto userDto = mapUserToDtoPlusAddress(orderItem.getUser());
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }

    /**
     * Converts a {@link User} entity to a {@link UserDto}, including address and order history.
     *
     * @param user The {@link User} entity to be converted.
     * @return A {@link UserDto} representation including address and order history.
     */
    public UserDto mapUserToDtoPlusAddressAndOrderHistory(User user) {
        UserDto userDto = mapUserToDtoPlusAddress(user);

        if (user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()) {
            userDto.setOrderItemList(user.getOrderItemList()
                    .stream()
                    .map(this::mapOrderItemToDtoPlusProduct)
                    .collect(Collectors.toList()));
        }
        return userDto;
    }
}
