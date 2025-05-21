package org.example.prote.domain.user.presentation.dto;

public record UserSignUpRequestDto(
        String username,
        String password,
        String email,
        String profileImageUrl
) {}
