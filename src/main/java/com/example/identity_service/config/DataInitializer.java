package com.example.identity_service.config;


import com.example.identity_service.enums.Role;
import com.example.identity_service.model.User;
import com.example.identity_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ApplicationRunner initAdminUser()
    {
        return args -> {
            // Check if an admin user already exists
            if (!userRepository.existsByUsername("admin")) {
                // Create roles for the admin user
                Set<Role> roles = new HashSet<>();
                roles.add(Role.ROLE_ADMIN);

                // Initialize the admin user
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin123")); // Set a default password
                adminUser.setRoles(roles);

                // Save the admin user to the database
                userRepository.save(adminUser);

                logger.info("Admin user created with username: 'admin'. Please change the password immediately.");
            }
        };
    }
}
