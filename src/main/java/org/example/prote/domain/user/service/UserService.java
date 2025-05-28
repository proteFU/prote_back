package org.example.prote.domain.user.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.prote.domain.user.domain.User;
import org.example.prote.domain.user.dto.UserLogInRequestDto;
import org.example.prote.domain.user.dto.UserResponseDto;
import org.example.prote.domain.user.dto.UserSignUpRequestDto;
import org.example.prote.domain.user.repository.UserRepository;
import org.example.prote.domain.user.type.Role;
import org.example.prote.global.security.auth.AuthDetails;
import org.example.prote.global.security.jwt.JwtTokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public ResponseEntity<Void> signUp(UserSignUpRequestDto request, HttpServletResponse response){
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .profile_image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuqRPBOeet4nYclhDLrDZwF2w2kBObHgLVdg&s")
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> logOut(AuthDetails request, HttpServletResponse response) {
        redisTemplate.delete(request.getUsername());
        jwtTokenProvider.deleteTokens(response);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity<Void> logIn(UserLogInRequestDto request, HttpServletResponse response) {
        Optional<User> user = userRepository.findByEmail(request.email());

        if(user.isPresent()) {
            if (passwordEncoder.matches(request.password(), user.get().getPassword())) {
                redisTemplate.delete(request.email());
                jwtTokenProvider.createTokens(user.get().getEmail(), response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<UserResponseDto> getUser(AuthDetails request) {
        Optional<User> user = userRepository.findByEmail(request.getUsername());

        if(user.isPresent()) {
            UserResponseDto userResponseDto = new UserResponseDto(
                    user.get().getUsername(),
                    user.get().getEmail(),
                    user.get().getProfile_image()
            );

            return ResponseEntity.ok(userResponseDto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
