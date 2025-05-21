package org.example.prote.domain.emotion.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user_emotions")
public class Emotion {
    @Id
    private String id;

    private Long userId;

    private Map<String, Integer> emotions;
}
