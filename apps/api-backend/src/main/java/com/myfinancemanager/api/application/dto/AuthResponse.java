package com.myfinancemanager.api.application.dto;

/**
 * DTO for authentication responses.
 */
public record AuthResponse(
    String accessToken,
    String refreshToken,
    String message
) {}