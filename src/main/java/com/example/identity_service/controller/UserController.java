package com.example.identity_service.controller;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.model.User;
import com.example.identity_service.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
@Transactional
@RequiredArgsConstructor
public class UserController
{
    private final IUserService userService;


    @PostMapping
    public User createUser(@Valid @RequestBody UserCreationRequest request)
    {
        return userService.createUser(request);
    }


    @GetMapping
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id)
    {
        return userService.getUserById(id);
    }


    @PutMapping("/{id}")
    public User updateUser(
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
