package org.example.prote.domain.user.domain.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.prote.domain.user.dto.UserLogInRequestDto;
import org.example.prote.domain.user.dto.UserResponseDto;
import org.example.prote.domain.user.dto.UserSignUpRequestDto;
import org.example.prote.domain.user.exception.UserNotFoundException;
import org.example.prote.domain.user.service.UserService;
import org.example.prote.global.security.auth.AuthDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public void singUp(@RequestBody UserSignUpRequestDto request, HttpServletResponse response) {
        userService.signUp(request, response);
    }

    @PostMapping("/logout")
    public void logOut(@AuthenticationPrincipal AuthDetails request, HttpServletResponse response) {
        userService.logOut(request, response);
    }

    @PostMapping("/login")
    public void login(@RequestBody UserLogInRequestDto request, HttpServletResponse response) {

        userService.logIn(request, response);
    }

    @GetMapping("/profile")
    public UserResponseDto getCurrentUser(@AuthenticationPrincipal AuthDetails request) {
        if (request == null){
            throw UserNotFoundException.EXCEPTION;
        } else {
            return userService.getUser(request);
        }
    }
}