package com.ridoh.Order_Management.repository;

import com.ridoh.Order_Management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 * <p>
 * This interface extends JpaRepository, providing CRUD operations
 * and custom query methods for handling User records in the database.
 * </p>
 *
 * Example usage:
 * <pre>
 *     boolean exists = userRepo.existsByEmail("test@example.com");
 *     Optional<User> user = userRepo.findByEmail("test@example.com");
 * </pre>
 *
 * @see JpaRepository
 */
public interface UserRepo extends JpaRepository<User, Long> {

    /**
     * Checks if a user with the given email exists in the database.
     *
     * @param email the email to check
     * @return true if a user with the given email exists, otherwise false
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by their email address.
     *
     * @param email the email of the user
     * @return an Optional containing the User if found, otherwise empty
     */
    Optional<User> findByEmail(String email);
}
