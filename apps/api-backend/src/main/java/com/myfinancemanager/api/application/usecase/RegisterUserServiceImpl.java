package com.myfinancemanager.api.application.usecase;

import com.myfinancemanager.api.domain.model.User;
import com.myfinancemanager.api.domain.port.in.RegisterUserUseCase;
import com.myfinancemanager.api.domain.port.out.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Implementation of the user registration use case.
 * Handles user registration with password hashing and validation.
 */
@Service
public class RegisterUserServiceImpl implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserServiceImpl(
            UserRepositoryPort userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegistrationResult register(String email, String password, String firstName, String lastName) {
        // Validate email format
        if (email == null || email.isBlank()) {
            return new RegistrationResult(null, false, "Email is required");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            return new RegistrationResult(null, false, "Email already registered");
        }

        // Validate password
        if (password == null || password.length() < 8) {
            return new RegistrationResult(null, false, "Password must be at least 8 characters");
        }

        // Hash password
        String hashedPassword = passwordEncoder.encode(password);

        // Create user
        User user = User.builder()
                .email(email)
                .passwordHash(hashedPassword)
                .firstName(firstName)
                .lastName(lastName)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        User savedUser = userRepository.save(user);

        return new RegistrationResult(savedUser, true, null);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}