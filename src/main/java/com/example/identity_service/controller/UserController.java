package com.example.identity_service.controller;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.model.User;
import com.example.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final UserService userService;


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
}
