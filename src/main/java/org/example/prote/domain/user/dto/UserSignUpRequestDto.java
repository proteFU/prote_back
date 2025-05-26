package org.example.prote.domain.user.dto;

public record UserSignUpRequestDto(
        String username,
        String password,
        String email,
        String profileImageUrl
) {}
