package com.ridoh.Order_Management.controller;

import com.ridoh.Order_Management.dto.LoginRequest;
import com.ridoh.Order_Management.dto.Response;
import com.ridoh.Order_Management.dto.UserDto;
import com.ridoh.Order_Management.service.Interface.UserService;
import com.ridoh.Order_Management.service.impl.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserDto registrationRequest) throws MessagingException {
    //    System.out.println(registrationRequest);
        Response response = userService.registerUser(registrationRequest);

        //return ResponseEntity.ok(userService.registerUser(registrationRequest));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}
