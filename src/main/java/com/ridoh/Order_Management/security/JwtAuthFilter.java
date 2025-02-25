package com.ridoh.Order_Management.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter that intercepts every HTTP request to validate JWT tokens.
 * <p>
 * This filter extends {@link OncePerRequestFilter}, ensuring that each request is filtered only once.
 * It extracts the JWT token from the request, validates it, and sets the authentication context.
 * </p>
 *
 * <p>Usage:</p>
 * <pre>
 * - Extracts JWT token from the request header
 * - Validates the token using {@link JwtUtils}
 * - Loads user details from the database via {@link CustomUserDetailsService}
 * - Sets authentication in {@link SecurityContextHolder} if the token is valid
 * </pre>
 *
 * @author Ridoh
 * @see JwtUtils
 * @see CustomUserDetailsService
 * @see OncePerRequestFilter
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Filters incoming HTTP requests to check for a valid JWT token.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain to continue processing the request
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token != null) {
            String username = jwtUtils.getUsernameFromToken(token);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if (StringUtils.hasText(username) && jwtUtils.isTokenValid(token, userDetails)) {
                log.info("VALID JWT FOR {}", username);

                // Create an authentication token and set it in the security context
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header.
     *
     * @param request the HTTP request
     * @return the extracted JWT token, or null if not present
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && StringUtils.startsWithIgnoreCase(token, "Bearer")) {
            return token.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
}
