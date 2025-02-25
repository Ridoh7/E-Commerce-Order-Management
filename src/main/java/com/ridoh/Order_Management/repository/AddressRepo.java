package com.ridoh.Order_Management.repository;

import com.ridoh.Order_Management.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
