package com.example.identity_service.service;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.exception.UserNotFoundException;
import com.example.identity_service.model.User;

import com.example.identity_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public User createUser(UserCreationRequest request)
    {
        // Map the UserCreationRequest DTO to the User entity
        User user = modelMapper.map(request, User.class);
        return userRepository.save(user);
    }



    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    // Find User by ID
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
