package com.example.identity_service.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(UUID id)
    {
        super(String.format("User not found with ID: %s sad man cry", id));
    }
}
