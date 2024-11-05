package com.example.identity_service.controller;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.ApiResponse;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.model.User;
import com.example.identity_service.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
@Transactional
@RequiredArgsConstructor
public class UserController
{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);  // Logger instance
    private final IUserService userService;


    @GetMapping("/me")
    public ApiResponse<UserResponse> getMyInfo() {
        UserResponse userResponse = userService.getMyInfo();
        return new ApiResponse<>(
                "success",
                "Fetched user info successfully",
                userResponse
        );
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreationRequest request)
    {
        return new ApiResponse<>(
                "success",
                "User created successfully",
                userService.createUser(request)
        );
    }




    @GetMapping
    public List<UserResponse> getAllUsers()
    {
        // Get the currently authenticated user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication != null ? authentication.getName() : null;


        /// Retrieve roles (authorities) from the authentication object
        assert authentication != null; // Ensure authentication is not null
        String rolesString = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", ")); // Join roles into a single string

        // Log the currently logged-in user
        log.info("Currently logged in user: {}", currentUsername); // Using placeholder for better performance
        log.info("User roles: {}", rolesString); // Log roles


        return userService.getAllUsers();
    }


    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable UUID id)
    {
        return userService.getUserById(id);
    }





    @PatchMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest request)
    {
        return userService.updateUser(id, request);
    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable UUID id)
//    {
//        // Call the service to delete the user
//        userService.deleteUser(id);
//
//        // Return a 204 No Content status to indicate successful deletion
//        return ResponseEntity.noContent().build();
//    }


    @DeleteMapping("/{ids}")
    public ResponseEntity<Void> deleteUsers(@PathVariable List<UUID> ids)
    {
        userService.deleteUsers(ids);
        return ResponseEntity.noContent().build();
    }


}
