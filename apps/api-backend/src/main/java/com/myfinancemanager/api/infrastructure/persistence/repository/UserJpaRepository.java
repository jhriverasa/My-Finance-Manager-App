package com.myfinancemanager.api.infrastructure.persistence.repository;

import com.myfinancemanager.api.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for UserEntity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user by email.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Checks if a user exists by email.
     */
    boolean existsByEmail(String email);
}