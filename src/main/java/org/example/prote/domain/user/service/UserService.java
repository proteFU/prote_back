package org.example.prote.domain.user.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.prote.domain.user.domain.User;
import org.example.prote.domain.user.domain.repository.UserRepository;
import org.example.prote.domain.user.exception.InvalidPasswordException;
import org.example.prote.domain.user.exception.UserAlreadyExistsException;
import org.example.prote.domain.user.exception.UserNotFoundException;
import org.example.prote.domain.user.presentation.dto.UserLogInRequestDto;
import org.example.prote.domain.user.presentation.dto.UserResponseDto;
import org.example.prote.domain.user.presentation.dto.UserSignUpRequestDto;
import org.example.prote.global.security.oauth.CustomOAuth2User;
import org.example.prote.global.util.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public void signUp(UserSignUpRequestDto request, HttpServletResponse response){
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw UserAlreadyExistsException.EXCEPTION;
        }

        User user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .profile_image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuqRPBOeet4nYclhDLrDZwF2w2kBObHgLVdg&s")
                .build();

        userRepository.save(user);

        jwtUtil.addCookies(user.getEmail(), response);
    }

    public void logOut(CustomOAuth2User customOAuth2User, HttpServletResponse response) {
        jwtUtil.deleteCookies(response);

        redisTemplate.delete(customOAuth2User.getName());
    }

    public void logIn(UserLogInRequestDto request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if(passwordEncoder.matches(request.password(), user.getPassword())){
            jwtUtil.addCookies(user.getEmail(), response);
        } else {
            throw InvalidPasswordException.EXCEPTION;
        }
    }

    public UserResponseDto getUser(CustomOAuth2User customOAuth2User) {
        User user = userRepository.findByEmail(customOAuth2User.getName())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        UserResponseDto userResponseDto = new UserResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getProfile_image()
        );

        return userResponseDto;
    }
}
