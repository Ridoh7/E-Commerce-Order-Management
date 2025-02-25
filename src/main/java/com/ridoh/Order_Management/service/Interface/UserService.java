package com.ridoh.Order_Management.service.Interface;

import com.ridoh.Order_Management.dto.LoginRequest;
import com.ridoh.Order_Management.dto.Response;
import com.ridoh.Order_Management.dto.UserDto;
import com.ridoh.Order_Management.entity.User;
import jakarta.mail.MessagingException;

/**
 * Service interface for managing user-related operations such as registration, authentication,
 * retrieving user information, and fetching order history.
 */
public interface UserService {

    /**
     * Registers a new user in the system.
     *
     * @param registrationRequest the user details for registration
     * @return a response indicating the success or failure of the registration
     * @throws MessagingException if there is an error sending the registration email
     */
    Response registerUser(UserDto registrationRequest) throws MessagingException;

    /**
     * Authenticates a user and returns a response containing authentication details.
     *
     * @param loginRequest the login credentials (email and password)
     * @return a response containing authentication details such as a token
     */
    Response loginUser(LoginRequest loginRequest);

    /**
     * Retrieves a list of all registered users.
     *
     * @return a response containing the list of all users
     */
    Response getAllUsers();

    /**
     * Retrieves the currently logged-in user.
     *
     * @return the authenticated user entity
     */
    User getLoginUser();

    /**
     * Retrieves the authenticated user's profile and order history.
     *
     * @return a response containing the user's profile and order history
     */
    Response getUserInfoAndOrderHistory();
}
