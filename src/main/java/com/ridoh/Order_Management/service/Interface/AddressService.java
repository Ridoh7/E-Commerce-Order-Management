package com.ridoh.Order_Management.service.Interface;

import com.ridoh.Order_Management.dto.AddressDto;
import com.ridoh.Order_Management.dto.Response;

/**
 * Service interface for managing user addresses.
 */
public interface AddressService {

    /**
     * Saves or updates a user's address.
     *
     * @param addressDto the address data transfer object containing address details
     * @return a response indicating the success or failure of the operation
     */
    Response saveAndUpdateAddress(AddressDto addressDto);
}
