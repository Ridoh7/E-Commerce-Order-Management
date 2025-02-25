package com.ridoh.Order_Management.service.Interface;

import com.ridoh.Order_Management.dto.LoginRequest;
import com.ridoh.Order_Management.dto.Response;
import com.ridoh.Order_Management.dto.UserDto;
import com.ridoh.Order_Management.entity.User;
import jakarta.mail.MessagingException;

public interface UserService {
    Response registerUser (UserDto registrationRequest) throws MessagingException;
    Response loginUser (LoginRequest loginRequest);
    Response getAllUsers();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();

}
