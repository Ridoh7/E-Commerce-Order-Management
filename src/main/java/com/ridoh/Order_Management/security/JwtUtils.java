package com.ridoh.Order_Management.security;

import com.ridoh.Order_Management.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * Utility class for generating and validating JWT tokens.
 * <p>
 * This class provides methods to:
 * - Generate JWT tokens
 * - Extract claims from tokens
 * - Validate tokens
 * </p>
 *
 * @author Ridoh
 */
@Service
@Slf4j
public class JwtUtils {

    // Token expiration time (3 months)
    private static final long EXPIRATION_TIME_IN_MILLIS = 1000L * 60L * 60L * 24L * 30L * 3L;

    private SecretKey key;

    @Value("${secreteJwtString}")
    private String secreteJwtString;

    /**
     * Initializes the secret key for signing JWT tokens.
     * This method runs after dependency injection.
     */
    @PostConstruct
    private void init() {
        byte[] keyBytes = secreteJwtString.getBytes(StandardCharsets.UTF_8);
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    /**
     * Generates a JWT token for a given user.
     *
     * @param user the user entity
     * @return a JWT token string
     */
    public String generateToken(User user) {
        return generateToken(user.getEmail());
    }

    /**
     * Generates a JWT token for a given username (email).
     *
     * @param username the username/email of the user
     * @return a JWT token string
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILLIS))
                .signWith(key)
                .compact();
    }

    /**
     * Extracts the username from a given JWT token.
     *
     * @param token the JWT token
     * @return the username (subject) stored in the token
     */
    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extracts specific claims from a JWT token.
     *
     * @param token          the JWT token
     * @param claimsResolver the function to extract specific claims
     * @param <T>            the type of the claim
     * @return the extracted claim value
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        try {
            return claimsResolver.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
        } catch (Exception e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Checks if a JWT token is valid.
     *
     * @param token       the JWT token
     * @param userDetails the user details to compare with the token
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if a JWT token has expired.
     *
     * @param token the JWT token
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        Date expirationDate = extractClaims(token, Claims::getExpiration);
        return expirationDate != null && expirationDate.before(new Date());
    }
}
