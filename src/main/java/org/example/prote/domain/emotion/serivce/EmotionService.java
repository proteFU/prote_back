package org.example.prote.domain.emotion.serivce;

import lombok.RequiredArgsConstructor;
import org.example.prote.domain.emotion.domain.Emotion;
import org.example.prote.domain.emotion.domain.repository.EmotionRepository;
import org.example.prote.domain.emotion.presentation.dto.EmotionAddRequestDto;
import org.example.prote.domain.emotion.presentation.dto.EmotionResponseDto;
import org.example.prote.global.security.auth.AuthDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmotionService {
    private final EmotionRepository emotionRepository;

    public void addEmotion(AuthDetails request, EmotionAddRequestDto requestDto) {
        Long userId = request.getUserId();

        Emotion emotion = emotionRepository.findByUserId(userId);

        if (emotion != null) {
            Map<String, Integer> emoMap = emotion.getEmotions();

            for (String emo : requestDto.emotionList()) {
                emoMap.put(emo, emoMap.getOrDefault(emo, 0) + 1);
            }

            emotionRepository.save(emotion);
        } else {
            Map<String, Integer> newMap = new HashMap<>();

            for (String e : requestDto.emotionList()) {
                newMap.put(e, 1);
            }

            Emotion newEmo = Emotion.builder()
                    .userId(userId)
                    .emotions(newMap)
                    .build();

            emotionRepository.save(newEmo);
        }
    }

    public EmotionResponseDto findEmotion(AuthDetails request) {
        Emotion emotion = emotionRepository.findByUserId(request.getUserId());
        return new EmotionResponseDto(emotion.getEmotions());
    }
}
