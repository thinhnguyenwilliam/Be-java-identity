package com.example.identity_service.service.impl;

import com.example.identity_service.components.JwtUtil;
import com.example.identity_service.dto.request.AuthenticationRequest;
import com.example.identity_service.dto.response.AuthenticationResponse;
import com.example.identity_service.enums.ErrorCode;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.model.User;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.service.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationServiceImpl implements IAuthenticationService
{
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {
        // Find the user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_ALREADY_EXISTS));

        // Check if the provided password matches the stored hashed password
        if (passwordEncoder.matches(request.getPassword(), user.getPassword()))
        {
            // Generate a JWT token
            String token = jwtUtil.generateToken(user);

            // Return the response with token and authentication status
            return AuthenticationResponse.builder()
                    .token(token)
                    .authenticated(true)
                    .build();
        }
        else
        {
            // If the password does not match, throw an exception or return an unauthenticated response
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
    }
}
