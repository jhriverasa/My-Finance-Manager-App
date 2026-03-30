package com.myfinancemanager.api.domain.port.in;

import com.myfinancemanager.api.domain.model.User;

/**
 * Input port for user registration use cases.
 * Defines the contract for user registration operations.
 */
public interface RegisterUserUseCase {

    /**
     * Registers a new user in the system.
     * 
     * @param email user's email (must be unique)
     * @param password user's password (plain text, will be hashed)
     * @param firstName user's first name
     * @param lastName user's last name
     * @return RegistrationResult containing user if successful
     */
    RegistrationResult register(String email, String password, String firstName, String lastName);

    /**
     * Checks if an email is already registered.
     * 
     * @param email email to check
     * @return true if email exists, false otherwise
     */
    boolean emailExists(String email);

    /**
     * Result object for registration operations.
     */
    record RegistrationResult(
        User user,
        boolean success,
        String errorMessage
    ) {}
}