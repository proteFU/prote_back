package org.example.prote.domain.user.presentation.dto;

public record UserLogInRequestDto(
        String password,
        String email
) {}
