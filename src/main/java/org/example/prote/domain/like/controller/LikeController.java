package org.example.prote.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.prote.domain.like.dto.LikeAddRequestDto;
import org.example.prote.domain.like.dto.LikeListResponseDto;
import org.example.prote.domain.like.service.LikeService;
import org.example.prote.global.security.auth.AuthDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("songs/likes")
@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> like(@RequestBody LikeAddRequestDto requestDto, @AuthenticationPrincipal AuthDetails authDetails) {
        likeService.like(requestDto, authDetails);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<LikeListResponseDto> getLikes(@AuthenticationPrincipal AuthDetails authDetails) {
        LikeListResponseDto responseDto = likeService.getLikeList(authDetails);

        return ResponseEntity.ok(responseDto);
    }
}
