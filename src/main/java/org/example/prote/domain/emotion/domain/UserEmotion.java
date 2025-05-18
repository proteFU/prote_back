package org.example.prote.domain.emotion.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.prote.domain.user.domain.User;

@NoArgsConstructor
@Table(name = "user_emotions")
@Entity
public class UserEmotion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id", nullable = false)
    private Emotion emotion;

    @Column(nullable = false)
    private int search_count = 0;

    public UserEmotion(User user, Emotion emotion, int searchCount) {
        this.user = user;
        this.emotion = emotion;
        this.search_count = searchCount;
    }

    public void increaseSearchCount() {
        this.search_count++;
    }
}
