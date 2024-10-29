package com.example.identity_service.service.impl;

import com.example.identity_service.config.mappper.UserMapper;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.enums.ErrorCode;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.model.User;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;




    @Override
    public User createUser(UserCreationRequest request)
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
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }




    @Override
    public User updateUser(UUID id, UserUpdateRequest request) {
        // Find user by ID, or throw exception if not found
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Use MapStruct to update fields
        userMapper.updateUserFromRequest(existingUser, request);

        // Save and return updated user
        return userRepository.save(existingUser);
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
