package com.example.identity_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;// You can set the default access level

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor
@AllArgsConstructor
@Builder // Enables the builder pattern for this class
@FieldDefaults(level = PRIVATE)
public class UserResponse
{
    UUID id;
    String username;
    // Removed password from the response for security reasons
    String firstName;
    String lastName;
    String fullName;
    LocalDate dob;

    // Calculate fullName dynamically
    public String getFullName()
    {
        return String.format("%s %s", firstName, lastName);
    }
}
