package com.example.identity_service.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreationRequest
{
    @NotEmpty(message = "Username is required babe love love")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotEmpty(message = "Password is required honey love love")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;


    private String firstName;
    private String lastName;
    private LocalDate dob;
}
