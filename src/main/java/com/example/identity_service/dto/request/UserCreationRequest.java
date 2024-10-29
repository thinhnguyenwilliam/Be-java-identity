package com.example.identity_service.dto.request;


import com.example.identity_service.annotations.Age;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;// You can set the default access level

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor
@AllArgsConstructor
@Builder // Enables the builder pattern for this class
@FieldDefaults(level = PRIVATE) // Sets the default access level of fields to private
public class UserCreationRequest
{
    @NotEmpty(message = "Username is required babe love love")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    String username;

    @NotEmpty(message = "Password is required honey love love")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;


    String firstName;
    String lastName;


    @NotNull(message = "Date of birth is required")
    @Age(message = "User must be at least 18 years old")
    LocalDate dob;
}
