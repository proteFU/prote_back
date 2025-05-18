package org.example.prote.global.security.oauth;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.prote.domain.user.domain.User;
import org.example.prote.domain.user.domain.repository.UserRepository;
import org.example.prote.domain.user.exception.UserNotFoundException;
import org.example.prote.global.config.properties.JwtProperties;
import org.example.prote.global.security.jwt.JwtTokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtProperties jwtProperties;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException  {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        User user = userRepository.findById(oAuth2User.getUserId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        redisTemplate.opsForValue().set(
                user.getEmail(),
                refreshToken,
                jwtProperties.getRefreshTime(),
                TimeUnit.SECONDS
        );

        Cookie accessCookie = createCookie("accessToken", accessToken,(int) jwtProperties.getAccessTime());

        Cookie refreshCookie = createCookie("refreshToken", refreshToken,(int) jwtProperties.getAccessTime());

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        response.sendRedirect("https://your-client.com/oauth/success");
    }

    private Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }
}
