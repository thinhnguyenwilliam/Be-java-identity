package com.example.identity_service.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserNotFoundException extends RuntimeException
{
    private final UUID id; // Store the ID of the user not found, this.id belongs this class

    public UserNotFoundException(UUID id)
    {
        super(String.format("User with ID '%s' not found.", id));
        this.id = id;
    }

}
