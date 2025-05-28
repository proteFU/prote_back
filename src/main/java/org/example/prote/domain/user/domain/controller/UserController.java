package org.example.prote.domain.user.domain.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.prote.domain.user.dto.UserLogInRequestDto;
import org.example.prote.domain.user.dto.UserResponseDto;
import org.example.prote.domain.user.dto.UserSignUpRequestDto;
import org.example.prote.domain.user.service.UserService;
import org.example.prote.global.security.auth.AuthDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "BearerAuth")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody UserSignUpRequestDto request, HttpServletResponse response) {
        return userService.signUp(request, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(@AuthenticationPrincipal AuthDetails request, HttpServletResponse response) {
        if (request == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return userService.logOut(request, response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserLogInRequestDto request, HttpServletResponse response) {
        if (request == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return userService.logIn(request, response);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getCurrentUser(@AuthenticationPrincipal AuthDetails request) {
        if (request == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return userService.getUser(request);
        }
    }
}