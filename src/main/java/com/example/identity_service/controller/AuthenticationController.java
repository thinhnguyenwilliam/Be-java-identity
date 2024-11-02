package com.example.identity_service.controller;


import com.example.identity_service.components.JwtUtil;
import com.example.identity_service.dto.request.AuthenticationRequest;
import com.example.identity_service.dto.request.IntrospectRequest;
import com.example.identity_service.dto.response.ApiResponse;
import com.example.identity_service.dto.response.AuthenticationResponse;
import com.example.identity_service.dto.response.IntrospectResponse;
import com.example.identity_service.model.User;
import com.example.identity_service.service.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController
{
    IAuthenticationService authenticationService;
    JwtUtil jwtUtil;

    @PostMapping("/token")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest authenticationRequest)
    {
        AuthenticationResponse authResponse = authenticationService.authenticate(authenticationRequest);

        ApiResponse<AuthenticationResponse> response = new ApiResponse<>(
                "success",
                "Login successful",
                authResponse
        );
        return ResponseEntity.ok(response);
    }


    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> introspectToken(@RequestBody IntrospectRequest introspectRequest)
    {
        boolean isValid = jwtUtil.verifyToken(introspectRequest.getToken());
        IntrospectResponse introspectResponse = new IntrospectResponse(isValid);

        String message;
        if (isValid)
            message = "Token introspection completed successfully";
        else
            message = "Token is invalid or expired";


        ApiResponse<IntrospectResponse> response = new ApiResponse<>(
                isValid ? "success" : "failure",
                message,
                introspectResponse
        );

        return ResponseEntity.ok(response);
    }
}
