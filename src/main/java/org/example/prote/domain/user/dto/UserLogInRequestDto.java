package org.example.prote.domain.user.dto;

public record UserLogInRequestDto(
        String password,
        String email
) {}
