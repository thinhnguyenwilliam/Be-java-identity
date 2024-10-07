package com.example.identity_service.service.impl;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.exception.UserNotFoundException;
import com.example.identity_service.model.User;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService
{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;



    @Override
    public User createUser(UserCreationRequest request) {
        // Map the UserCreationRequest DTO to the User entity
        User user = modelMapper.map(request, User.class);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User updateUser(UUID id, UserUpdateRequest request) {
        // Find user by ID, or throw exception if not found
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Update fields that are present in the request
        if (request.getPassword() != null) {
            existingUser.setPassword(request.getPassword());
        }
        if (request.getFirstName() != null) {
            existingUser.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            existingUser.setLastName(request.getLastName());
        }
        if (request.getDob() != null) {
            existingUser.setDob(request.getDob());
        }

        // Save and return updated user
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUsers(List<UUID> ids)
    {
        for (UUID id : ids)
        {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id));
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
