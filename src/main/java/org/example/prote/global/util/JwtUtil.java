package org.example.prote.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.prote.global.config.properties.JwtProperties;
import org.example.prote.global.security.jwt.JwtTokenProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class JwtUtil {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final RedisTemplate<String, String> redisTemplate;

    public void addCookies(String email, HttpServletResponse response) {
        String accessToken = jwtTokenProvider.createAccessToken(email);
        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        Cookie accessCookie = createCookie("accessToken", accessToken, (int) jwtProperties.getAccessTime());
        Cookie refreshCookie = createCookie("refreshToken", refreshToken, (int) jwtProperties.getRefreshTime());

        redisTemplate.opsForValue().set(
                email,
                refreshToken,
                jwtProperties.getRefreshTime(),
                TimeUnit.SECONDS
        );

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }

    public Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    public void deleteCookies(HttpServletResponse response) {
        Cookie accessCookie = createCookie("accessToken", "", 0);
        Cookie refreshCookie = createCookie("refreshToken", "", 0);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }
}
