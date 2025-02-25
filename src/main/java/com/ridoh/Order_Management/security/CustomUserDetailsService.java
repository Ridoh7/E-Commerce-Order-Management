package com.ridoh.Order_Management.security;

import com.ridoh.Order_Management.dto.UserDto;
import com.ridoh.Order_Management.entity.User;
import com.ridoh.Order_Management.exception.NotFoundException;
import com.ridoh.Order_Management.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepo.findByEmail(username)
                .orElseThrow(()-> new NotFoundException("User/ Email not found"));

        return AuthUser.builder()
                .user(user)
                .build();
    }
}
