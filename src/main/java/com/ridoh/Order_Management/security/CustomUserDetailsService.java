package com.ridoh.Order_Management.security;

import com.ridoh.Order_Management.entity.User;
import com.ridoh.Order_Management.exception.NotFoundException;
import com.ridoh.Order_Management.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of {@link UserDetailsService} to load user details from the database.
 * <p>
 * This service is responsible for retrieving user information by their email
 * and returning an instance of {@link AuthUser}, which implements {@link UserDetails}.
 * </p>
 *
 * <p>Usage:</p>
 * <pre>
 *     UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
 * </pre>
 *
 * @author Ridoh
 * @see UserDetailsService
 * @see AuthUser
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    /**
     * Loads a user from the database by their email.
     *
     * @param username the email of the user to be retrieved
     * @return an instance of {@link AuthUser} containing user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("User/ Email not found"));

        return AuthUser.builder()
                .user(user)
                .build();
    }
}
