package org.example.prote.domain.emotion.dto;

import java.util.Map;

public record EmotionResponseDto (
        Map<String, Integer> emotionList
) {
}
