package com.example.identity_service.service;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService
{
    User createUser(UserCreationRequest request);
    List<User> getAllUsers();
    UserResponse getUserById(UUID id);
    UserResponse updateUser(UUID id, UserUpdateRequest request);
    //void deleteUser(UUID id);
    void deleteUsers(List<UUID> ids);
}
