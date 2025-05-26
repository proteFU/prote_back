package org.example.prote.domain.emotion.dto;

import java.util.List;

public record EmotionAddRequestDto(
        List<String> emotionList
) {
}
