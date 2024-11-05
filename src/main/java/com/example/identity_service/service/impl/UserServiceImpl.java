package com.example.identity_service.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.identity_service.config.mappper.UserMapper;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.enums.ErrorCode;
import com.example.identity_service.enums.Role;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.model.User;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserServiceImpl implements IUserService
{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    UserRepository userRepository;
    UserMapper userMapper;
    BCryptPasswordEncoder passwordEncoder;




    @Override
    public UserResponse createUser(UserCreationRequest request)
    {
        // test @builder
        UserCreationRequest request1 = UserCreationRequest.builder()
                .username("john_doe_Thing")
                .password("password123")
                .firstName("John")
                .dob(LocalDate.of(2000, 1, 1)) // Make sure the date is valid
                .build();

        System.out.println(request1);
        // end test @builder

        if (userRepository.existsByUsername(request.getUsername()))
        {
            //throw new RuntimeException when don't know what error
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }



        User user = userMapper.userCreationRequestToUser(request);
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Initialize and set roles
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER); // Assuming the default role is "USER"
        user.setRoles(roles);

        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")  // Only ADMIN role can access this method
    public List<UserResponse> getAllUsers()
    {
        logger.info("Fetching all users");  // Log an informational message at the start of the method

        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());

        logger.info("Number of users fetched: {}", users.size());  // Log a debug message with the size of the result
        return users;
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')") // Checks after method execution
    public UserResponse getUserById(UUID id)
    {
        logger.info("Fetching user by ID");
        return userMapper.userToUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }




    @Override
    public UserResponse updateUser(UUID id, UserUpdateRequest request) {
        // Find user by ID, or throw exception if not found
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Use MapStruct to update fields
        userMapper.updateUserFromRequest(existingUser, request);

        // Save and return updated user
        return userMapper.userToUserResponse(userRepository.save(existingUser));
    }

    @Override
    public UserResponse getMyInfo()
    {
        // Get the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.USER_NOT_AUTHENTICATED);
        }

        // Retrieve and log the roles
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // Retrieve the username of the authenticated user
        String username = authentication.getName();
        logger.info("Fetching information for logged-in user: {}", username);
        logger.info("Roles for user {}: {}", username, roles);

        // Fetch the user entity from the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Map the user entity to UserResponse DTO
        return userMapper.userToUserResponse(user);
    }

    @Override
    public void deleteUsers(List<UUID> ids)
    {
        for (UUID id : ids)
        {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            userRepository.delete(existingUser);
        }
    }

//    @Override
//    public void deleteUser(UUID id)
//    {
//        // Find user by ID or throw an exception if not found
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException(id));
//
//        // Delete the user from the repository
//        userRepository.delete(existingUser);
//    }
}
