package com.example.identity_service.dto.request;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;// You can set the default access level

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor
@AllArgsConstructor
@Builder // Enables the builder pattern for this class
@FieldDefaults(level = PRIVATE)
public class AuthenticationRequest
{
    String username;
    String password;
}
