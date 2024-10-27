package com.example.identity_service.repository.custom.impl;


import com.example.identity_service.repository.custom.UserRepositoryCustom;
import jakarta.persistence.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom
{
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);



}
