package org.example.prote.domain.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.index.Indexed;

@NoArgsConstructor
@Entity
public class RefreshToken {
    @Id
    private String email;

    @Indexed
    private String refreshToken;

    public RefreshToken(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }
}
