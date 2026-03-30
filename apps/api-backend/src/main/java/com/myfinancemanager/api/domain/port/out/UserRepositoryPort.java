package com.myfinancemanager.api.domain.port.out;

import com.myfinancemanager.api.domain.model.User;

import java.util.Optional;

/**
 * Output port for user persistence operations.
 * Defines the contract for user data access.
 */
public interface UserRepositoryPort {

    /**
     * Saves a user to the database.
     * 
     * @param user the user to save
     * @return the saved user with generated ID
     */
    User save(User user);

    /**
     * Finds a user by ID.
     * 
     * @param id the user's ID
     * @return Optional containing the user if found
     */
    Optional<User> findById(Long id);

    /**
     * Finds a user by email.
     * 
     * @param email the user's email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user exists by email.
     * 
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Deletes a user by ID.
     * 
     * @param id the user's ID
     */
    void deleteById(Long id);
}