package com.ridoh.Order_Management.service.Interface;

import com.ridoh.Order_Management.dto.AddressDto;
import com.ridoh.Order_Management.dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);
}
