package com.myfinancemanager.api.application.usecase;

import com.myfinancemanager.api.domain.model.User;
import com.myfinancemanager.api.domain.port.in.AuthenticateUserUseCase;
import com.myfinancemanager.api.domain.port.out.UserRepositoryPort;
import com.myfinancemanager.api.infrastructure.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the authentication use case.
 * Orchestrates user authentication with JWT token generation.
 */
@Service
public class AuthenticateUserServiceImpl implements AuthenticateUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticateUserServiceImpl(
            UserRepositoryPort userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthenticationResult authenticate(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isEmpty()) {
            return new AuthenticationResult(null, null, null, false, "Invalid credentials");
        }

        User user = userOptional.get();
        
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            return new AuthenticationResult(null, null, null, false, "Invalid credentials");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return new AuthenticationResult(user, accessToken, refreshToken, true, null);
    }

    @Override
    public AuthenticationResult refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return new AuthenticationResult(null, null, null, false, "Invalid refresh token");
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
        if (userId == null) {
            return new AuthenticationResult(null, null, null, false, "Could not extract user ID");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return new AuthenticationResult(null, null, null, false, "User not found");
        }

        User user = userOptional.get();
        String newAccessToken = jwtTokenProvider.generateAccessToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

        return new AuthenticationResult(user, newAccessToken, newRefreshToken, true, null);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public Long getUserIdFromToken(String token) {
        return jwtTokenProvider.getUserIdFromToken(token);
    }
}