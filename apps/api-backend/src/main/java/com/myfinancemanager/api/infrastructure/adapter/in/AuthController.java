package com.myfinancemanager.api.infrastructure.adapter.in;

import com.myfinancemanager.api.application.dto.AuthRequest;
import com.myfinancemanager.api.application.dto.AuthResponse;
import com.myfinancemanager.api.application.dto.RegisterRequest;
import com.myfinancemanager.api.domain.port.in.AuthenticateUserUseCase;
import com.myfinancemanager.api.domain.port.in.RegisterUserUseCase;
import com.myfinancemanager.api.infrastructure.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller for authentication endpoints.
 * Handles user registration, login, and token refresh.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(
            AuthenticateUserUseCase authenticateUserUseCase,
            RegisterUserUseCase registerUserUseCase,
            JwtTokenProvider jwtTokenProvider) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * POST /api/auth/register
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
            return ResponseEntity.badRequest().body(
                    new AuthResponse(null, null, result.errorMessage())
            );
        }

        // Generate tokens directly without re-authenticating
        var user = result.user();
        var accessToken = jwtTokenProvider.generateAccessToken(user);
        var refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AuthResponse(
                        accessToken,
                        refreshToken,
                        "User registered successfully"
                )
        );
    }

    /**
     * POST /api/auth/login
     * Authenticates a user and returns tokens.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        var result = authenticateUserUseCase.authenticate(
                request.email(),
                request.password()
        );

        if (!result.success()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
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
     * POST /api/auth/refresh
     * Refreshes an access token using a refresh token.
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.badRequest().body(
                    new AuthResponse(null, null, "Refresh token is required")
            );
        }
        
        var result = authenticateUserUseCase.refreshToken(refreshToken);

        if (!result.success()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
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
     * GET /api/auth/validate
     * Validates if a token is valid.
     */
    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestParam String token) {
        boolean isValid = authenticateUserUseCase.validateToken(token);
        return ResponseEntity.ok(Map.of(
                "valid", isValid,
                "message", isValid ? "Token is valid" : "Token is invalid or expired"
        ));
    }
}