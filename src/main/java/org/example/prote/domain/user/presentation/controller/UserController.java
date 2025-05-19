package org.example.prote.domain.user.presentation.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.prote.domain.user.exception.UserNotFoundException;
import org.example.prote.domain.user.presentation.dto.UserLogInRequestDto;
import org.example.prote.domain.user.presentation.dto.UserResponseDto;
import org.example.prote.domain.user.presentation.dto.UserSignUpRequestDto;
import org.example.prote.domain.user.service.UserService;
import org.example.prote.global.security.oauth.CustomOAuth2User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<Void> singUp(@RequestBody UserSignUpRequestDto request, HttpServletResponse response) {
        userService.signUp(request, response);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/log-out")
    public ResponseEntity<Void> logOut(@AuthenticationPrincipal CustomOAuth2User customOAuth2User, HttpServletResponse response) {
        userService.logOut(customOAuth2User, response);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(UserLogInRequestDto request, HttpServletResponse response) {
        userService.logIn(request, response);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public UserResponseDto getCurrentUser(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        if (customOAuth2User == null){
            throw UserNotFoundException.EXCEPTION;
        } else {
            return userService.getUser(customOAuth2User);
        }
    }
}