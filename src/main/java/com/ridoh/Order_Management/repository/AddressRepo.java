package com.ridoh.Order_Management.repository;

import com.ridoh.Order_Management.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Address entities.
 * <p>
 * This interface extends JpaRepository, providing built-in CRUD operations
 * for the Address entity, including methods for saving, deleting, and
 * retrieving address records from the database.
 * </p>
 *
 * <p>
 * The generic parameters specify the entity type (Address) and
 * the primary key type (Long).
 * </p>
 *
 * Example usage:
 * <pre>
 *     Address address = new Address();
 *     addressRepo.save(address);
 * </pre>
 */
public interface AddressRepo extends JpaRepository<Address, Long> {
}
