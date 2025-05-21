package org.example.prote.domain.emotion.presentation.dto;

import java.util.Map;

public record EmotionResponseDto (
        Map<String, Integer> emotionList
) {
}
