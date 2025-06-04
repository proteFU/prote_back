package org.example.prote.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.prote.domain.user.type.Role;
import org.example.prote.global.config.properties.JwtProperties;
import org.example.prote.global.security.auth.AuthDetailsService;
import org.example.prote.global.security.jwt.exception.ExpiredJwtTokenException;
import org.example.prote.global.security.jwt.exception.InvalidJwtTokenException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final AuthDetailsService authDetailsService;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String ACCESS_KEY = "access_token";
    private static final String REFRESH_KEY = "refresh_token";

    @Transactional
    public void createTokens(String email, HttpServletResponse response) {
        addCookies(createAccessToken(email), createRefreshToken(email), response);
    }

    private void addCookies(String accessToken, String refreshToken, HttpServletResponse response) {
        createCookie(response, ACCESS_KEY, accessToken, (int) (jwtProperties.getAccessTime() / 1000));
        createCookie(response, REFRESH_KEY, refreshToken, (int) (jwtProperties.getRefreshTime() / 1000));
    }

    private void createCookie(HttpServletResponse response, String name, String value, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(maxAge)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public String createAccessToken(String email) {
        return createToken(email, ACCESS_KEY, jwtProperties.getAccessTime());
    }

    public String createRefreshToken(String email) {
        String token = createToken(email, REFRESH_KEY, jwtProperties.getRefreshTime());
        redisTemplate.opsForValue().set(
                email,
                token,
                jwtProperties.getRefreshTime(),
                TimeUnit.SECONDS
        );
        return token;
    }

    private String createToken(String email, String type, Long time) {
        Date now = new Date();
        SecretKey key = Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(jwtProperties.getSecretKey())
        );

        return Jwts.builder()
                .signWith(key)
                .header()
                    .add("typ", "JWT")
                    .and()
                .claim("sub", email)
                .claim("role", Role.ROLE_USER.name())
                .claim("type", type)
                .claim("iat", now)
                .claim("exp", new Date(now.getTime() + time))
                .compact();
    }

    public void deleteTokens(HttpServletResponse response) { addCookies("", "", response); }

    public String resolveToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("access_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getEmail(String token) {
        return getTokenBody(token).getSubject();
    }

    private Claims getTokenBody(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(
                    Base64.getDecoder().decode(jwtProperties.getSecretKey())
            );

            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw ExpiredJwtTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidJwtTokenException.EXCEPTION;
        }
    }
}