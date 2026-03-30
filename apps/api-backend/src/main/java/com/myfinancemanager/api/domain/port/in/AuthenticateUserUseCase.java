package com.myfinancemanager.api.domain.port.in;

import com.myfinancemanager.api.domain.model.User;

/**
 * Input port for user authentication use cases.
 * Defines the contract for authentication operations.
 */
public interface AuthenticateUserUseCase {

    /**
     * Authenticates a user with email and password.
     * 
     * @param email user's email
     * @param password user's password (plain text)
     * @return AuthenticationResult containing user and token if successful
     */
    AuthenticationResult authenticate(String email, String password);

    /**
     * Refreshes an authentication token.
     * 
     * @param refreshToken the refresh token
     * @return new AuthenticationResult with refreshed token
     */
    AuthenticationResult refreshToken(String refreshToken);

    /**
     * Validates if a token is valid.
     * 
     * @param token the JWT token to validate
     * @return true if token is valid, false otherwise
     */
    boolean validateToken(String token);

    /**
     * Extracts user ID from a valid token.
     * 
     * @param token the JWT token
     * @return user ID if token is valid, null otherwise
     */
    Long getUserIdFromToken(String token);

    /**
     * Result object for authentication operations.
     */
    record AuthenticationResult(
        User user,
        String accessToken,
        String refreshToken,
        boolean success,
        String errorMessage
    ) {}
}