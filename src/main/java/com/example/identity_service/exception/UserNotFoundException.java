package com.example.identity_service.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(UUID id)
    {
        super("User not found with ID sad man cry: " + id);
    }
}
