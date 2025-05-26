package org.example.prote.domain.user.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.prote.domain.user.domain.User;
import org.example.prote.domain.user.dto.UserLogInRequestDto;
import org.example.prote.domain.user.dto.UserResponseDto;
import org.example.prote.domain.user.dto.UserSignUpRequestDto;
import org.example.prote.domain.user.exception.InvalidPasswordException;
import org.example.prote.domain.user.exception.UserAlreadyExistsException;
import org.example.prote.domain.user.exception.UserNotFoundException;
import org.example.prote.domain.user.repository.UserRepository;
import org.example.prote.global.security.auth.AuthDetails;
import org.example.prote.global.security.jwt.JwtTokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
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

        redisTemplate.delete(request.email());

        jwtTokenProvider.createTokens(request.email(), response);
    }

    public void logOut(AuthDetails request, HttpServletResponse response) {
        redisTemplate.delete(request.getUsername());
        jwtTokenProvider.deleteTokens(response);
    }

    @Transactional
    public void logIn(UserLogInRequestDto request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if(passwordEncoder.matches(request.password(), user.getPassword())){
            redisTemplate.delete(request.email());
            jwtTokenProvider.createTokens(user.getEmail(), response);
        } else {
            throw InvalidPasswordException.EXCEPTION;
        }
    }

    public UserResponseDto getUser(AuthDetails request) {
        User user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        UserResponseDto userResponseDto = new UserResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getProfile_image()
        );

        return userResponseDto;
    }
}
