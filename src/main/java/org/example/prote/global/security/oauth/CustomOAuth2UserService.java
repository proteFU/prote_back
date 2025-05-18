package org.example.prote.global.security.oauth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.prote.domain.user.domain.User;
import org.example.prote.domain.user.domain.repository.UserRepository;
import org.example.prote.global.security.jwt.JwtTokenProvider;
import org.example.prote.global.security.oauth.exception.ProviderNotSupported;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(request);

        String registrationId = request.getClientRegistration().getRegistrationId();
        Map<String,Object> attributes = oAuth2User.getAttributes();

        String email = extractEmail(registrationId, attributes);

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(email)
                        .build()));

        return new CustomOAuth2User(user.getId(), attributes);
    }

    private String extractEmail(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equals("google")) {
            return attributes.get("email").toString();
        }

        if (registrationId.equals("kakao")) {
            Map<String, Object> response = objectMapper.convertValue(
                    attributes.get("kakao_account"), new TypeReference<>() {
                    }
            );
            return response.get("email").toString();
        }

        if (registrationId.equals("naver")) {
            Map<String, Object> response = objectMapper.convertValue(
                    attributes.get("response"), new TypeReference<>() {
                    }
            );
            return response.get("email").toString();
        }

        throw ProviderNotSupported.EXCEPTION;
    }
}
