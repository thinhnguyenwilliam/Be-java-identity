package com.example.identity_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; // Use String type replace UUID type to see clarify id in mysql workbench

    @Column(nullable = false, unique = true)  // Unique constraint
    private String username;


    private String password;// Store the encoded password
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
