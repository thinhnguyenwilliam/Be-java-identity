package com.example.identity_service.repository;

import com.example.identity_service.model.User;
import com.example.identity_service.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom
{
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
