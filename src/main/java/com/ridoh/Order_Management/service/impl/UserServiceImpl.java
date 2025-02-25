package com.ridoh.Order_Management.service.impl;

import com.ridoh.Order_Management.dto.LoginRequest;
import com.ridoh.Order_Management.dto.Response;
import com.ridoh.Order_Management.dto.UserDto;
import com.ridoh.Order_Management.entity.User;
import com.ridoh.Order_Management.enums.UserRole;
import com.ridoh.Order_Management.exception.InvalidCredentialsException;
import com.ridoh.Order_Management.exception.NotFoundException;
import com.ridoh.Order_Management.mapper.EntityDtoMapper;
import com.ridoh.Order_Management.repository.UserRepo;
import com.ridoh.Order_Management.security.JwtUtils;
import com.ridoh.Order_Management.service.Interface.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;
    private final EmailService emailService;

    @Override
    public Response registerUser(UserDto registrationRequest) throws MessagingException {
        UserRole role = UserRole.USER;

        if (registrationRequest.getRole() != null && registrationRequest.getRole().equalsIgnoreCase("admin")){
            role =UserRole.ADMIN;
        }
        if (userRepo.existsByEmail(registrationRequest.getEmail())) {
            throw new RuntimeException(registrationRequest.getEmail() + "Already Exists");
        }
        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .build();

        User savedUser= userRepo.save(user);
        // Send welcome email
        try {
            String subject = "Welcome to Ridoh's E-Commerce Platform!";
            String body = "<h2>Dear " + registrationRequest.getName() + ",</h2>"
                    + "<p>Thank you for registering with us. We're excited to have you on board!</p>"
                    + "<P>Kindly use the following email: " +registrationRequest.getEmail() +
                    " and password: " +registrationRequest.getPassword() + " to login.</p>"
                    + "<p>Start exploring our amazing products and services now.</p>"
                    + "<p>Best regards,<br><b>Ridoh's E-Commerce Team</b></p>";

            emailService.sendEmail(registrationRequest.getEmail(), subject, body);
        } catch (MessagingException e) {
            // Log the error instead of breaking the registration process
            System.err.println("Failed to send welcome email: " + e.getMessage());
        }

        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);
        return Response.builder()
                .status(200)
                .message("User Successfully Added")
                .user(userDto)
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user =userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new NotFoundException("Email not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password does not match");
        }
        String token = jwtUtils.generateToken(user);
        return Response.builder()
                .status(200)
                .message("User successfully logged in")
                .token(token)
                .expirationTime("3 months")
                .role(user.getRole().name())
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos=users.stream()
                .map(entityDtoMapper::mapUserToDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .message("Users fetched successfully")
                .userList(userDtos)
                .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("user email is :" + email);

        return userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found"));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {
        User user= getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderHistory(user);

        return Response.builder()
                .status(200)
                .user(userDto)
                .build();
    }
}
