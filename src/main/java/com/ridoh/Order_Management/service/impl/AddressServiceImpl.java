package com.ridoh.Order_Management.service.impl;

import com.ridoh.Order_Management.dto.AddressDto;
import com.ridoh.Order_Management.dto.Response;
import com.ridoh.Order_Management.entity.Address;
import com.ridoh.Order_Management.entity.User;
import com.ridoh.Order_Management.repository.AddressRepo;
import com.ridoh.Order_Management.service.Interface.AddressService;
import com.ridoh.Order_Management.service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AddressService interface.
 * This class provides methods to create and update user addresses.
 */
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final UserService userService;

    /**
     * Saves or updates the address for the currently logged-in user.
     *
     * @param addressDto The DTO containing address details.
     * @return Response object indicating the success of the operation.
     */
    @Override
    public Response saveAndUpdateAddress(AddressDto addressDto) {
        // Retrieve the logged-in user
        User user = userService.getLoginUser();
        Address address = user.getAddress();

        // Check if the user has an existing address
        boolean isNewAddress = (address == null);

        if (isNewAddress) {
            address = new Address();
            address.setUser(user);
            user.setAddress(address); // Prevent null reference by setting user's address
        }

        // Update address fields
        updateAddressFields(address, addressDto);

        // Save address to the repository
        addressRepo.save(address);

        // Return appropriate response message
        String message = isNewAddress ? "Address successfully created" : "Address successfully updated";
        return Response.builder()
                .status(200)
                .message(message)
                .build();
    }

    /**
     * Updates address fields only if they are provided in the DTO.
     * This prevents overwriting existing fields with null values.
     *
     * @param address    The existing address entity.
     * @param addressDto The DTO containing new address details.
     */
    private void updateAddressFields(Address address, AddressDto addressDto) {
        if (addressDto.getStreet() != null) address.setStreet(addressDto.getStreet());
        if (addressDto.getCity() != null) address.setCity(addressDto.getCity());
        if (addressDto.getState() != null) address.setState(addressDto.getState());
        if (addressDto.getZipCode() != null) address.setZipCode(addressDto.getZipCode());
        if (addressDto.getCountry() != null) address.setCountry(addressDto.getCountry());
    }
}
