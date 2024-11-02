package com.example.identity_service.service;

import com.example.identity_service.dto.request.AuthenticationRequest;
import com.example.identity_service.dto.response.AuthenticationResponse;
import com.example.identity_service.model.User;

public interface IAuthenticationService
{
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
