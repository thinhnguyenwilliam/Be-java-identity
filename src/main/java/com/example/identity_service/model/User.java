package com.example.identity_service.model;

import com.example.identity_service.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id; // Use String type replace UUID type to see clarify id in mysql workbench

    @Column(nullable = false, unique = true)  // Unique constraint
    String username;


    String password;// Store the encoded password
    String firstName;
    String lastName;
    LocalDate dob;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<Role> roles;  // e.g., ["ROLE_USER", "ROLE_ADMIN"]
}
