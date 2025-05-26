package org.example.prote.domain.user.dto;

public record UserResponseDto(
        String username,
        String email,
        String profileImageUrl
) {}
