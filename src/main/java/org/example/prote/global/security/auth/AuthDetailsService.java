package org.example.prote.global.security.auth;

import lombok.RequiredArgsConstructor;
import org.example.prote.domain.user.exception.UserNotFoundException;
import org.example.prote.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new AuthDetails(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION)
        );
    }
}
