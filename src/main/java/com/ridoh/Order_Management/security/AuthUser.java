package com.ridoh.Order_Management.security;

import com.ridoh.Order_Management.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of Spring Security's {@link UserDetails} interface.
 * <p>
 * This class wraps the {@link User} entity and provides authentication and authorization details
 * required by Spring Security.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     AuthUser authUser = AuthUser.builder().user(user).build();
 *     Collection<? extends GrantedAuthority> authorities = authUser.getAuthorities();
 * </pre>
 *
 * @author Ridoh
 * @see UserDetails
 */
@Data
@Builder
public class AuthUser implements UserDetails {

    /**
     * The authenticated user entity.
     */
    private User user;

    /**
     * Returns the list of granted authorities (roles) for the user.
     *
     * @return a collection containing the user's role as a {@link SimpleGrantedAuthority}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    /**
     * Returns the user's password for authentication.
     *
     * @return the user's hashed password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used for authentication, which is the user's email.
     *
     * @return the user's email
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Indicates whether the user's account has expired.
     * This implementation always returns {@code true}.
     *
     * @return {@code true}, indicating the account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * This implementation always returns {@code true}.
     *
     * @return {@code true}, indicating the account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * This implementation always returns {@code true}.
     *
     * @return {@code true}, indicating the credentials are not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * This implementation always returns {@code true}.
     *
     * @return {@code true}, indicating the account is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
