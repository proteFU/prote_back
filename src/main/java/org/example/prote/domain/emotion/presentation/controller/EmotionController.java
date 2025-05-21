package org.example.prote.domain.emotion.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.prote.domain.emotion.presentation.dto.EmotionAddRequestDto;
import org.example.prote.domain.emotion.presentation.dto.EmotionResponseDto;
import org.example.prote.domain.emotion.serivce.EmotionService;
import org.example.prote.global.security.auth.AuthDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("emotions")
public class EmotionController {
    private final EmotionService emotionService;

    @PostMapping
    public void addEmotion(@AuthenticationPrincipal AuthDetails user, @RequestBody EmotionAddRequestDto requestDto) {
        emotionService.addEmotion(user, requestDto);
    }

    @GetMapping
    public EmotionResponseDto findEmotion(@AuthenticationPrincipal AuthDetails user) {
        return emotionService.findEmotion(user);
    }
}
