package com.myfinancemanager.api.infrastructure.adapter.in;

import com.myfinancemanager.api.application.dto.AuthRequest;
import com.myfinancemanager.api.application.dto.AuthResponse;
import com.myfinancemanager.api.application.dto.RegisterRequest;
import com.myfinancemanager.api.domain.port.in.AuthenticateUserUseCase;
import com.myfinancemanager.api.domain.port.in.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication endpoints.
 * Handles user registration, login, and token refresh.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(
            AuthenticateUserUseCase authenticateUserUseCase,
            RegisterUserUseCase registerUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }

    /**
     * POST /api/v1/auth/register
     * Registers a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        var result = registerUserUseCase.register(
                request.email(),
                request.password(),
                request.firstName(),
                request.lastName()
        );

        if (!result.success()) {
            return ResponseEntity.badRequest().build();
        }

        // Generate tokens for the new user
        var authResult = authenticateUserUseCase.authenticate(
                request.email(),
                request.password()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AuthResponse(
                        authResult.accessToken(),
                        authResult.refreshToken(),
                        "User registered successfully"
                )
        );
    }

    /**
     * POST /api/v1/auth/login
     * Authenticates a user and returns tokens.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        var result = authenticateUserUseCase.authenticate(
                request.email(),
                request.password()
        );

        if (!result.success()) {
            return ResponseEntity.badRequest().body(
                    new AuthResponse(null, null, result.errorMessage())
            );
        }

        return ResponseEntity.ok(new AuthResponse(
                result.accessToken(),
                result.refreshToken(),
                "Login successful"
        ));
    }

    /**
     * POST /api/v1/auth/refresh
     * Refreshes an access token using a refresh token.
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestParam String refreshToken) {
        var result = authenticateUserUseCase.refreshToken(refreshToken);

        if (!result.success()) {
            return ResponseEntity.badRequest().body(
                    new AuthResponse(null, null, result.errorMessage())
            );
        }

        return ResponseEntity.ok(new AuthResponse(
                result.accessToken(),
                result.refreshToken(),
                "Token refreshed successfully"
        ));
    }

    /**
     * GET /api/v1/auth/validate
     * Validates if a token is valid.
     */
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean isValid = authenticateUserUseCase.validateToken(token);
        return ResponseEntity.ok(isValid);
    }
}