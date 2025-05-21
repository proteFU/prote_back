package org.example.prote.domain.user.presentation.dto;

public record UserResponseDto(
        String username,
        String email,
        String profileImageUrl
) {}
