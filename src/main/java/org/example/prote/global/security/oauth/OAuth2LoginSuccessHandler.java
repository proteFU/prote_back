package org.example.prote.global.security.oauth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.prote.domain.user.domain.User;
import org.example.prote.domain.user.domain.repository.UserRepository;
import org.example.prote.domain.user.exception.UserNotFoundException;
import org.example.prote.global.util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException  {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        User user = userRepository.findById(oAuth2User.getUserId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        jwtUtil.addCookies(user.getEmail(), response);

        response.sendRedirect("https://your-client.com/oauth/success");
    }
}
