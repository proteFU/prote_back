package org.example.prote.domain.emotion.presentation.dto;

import java.util.List;

public record EmotionAddRequestDto(
        List<String> emotionList
) {
}
