package org.example.prote.domain.song.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.prote.domain.song.dto.SongAddRequestDto;
import org.example.prote.domain.song.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
@RestController
@RequestMapping("songs")
public class SongController {
    private final SongService songService;

    @PostMapping
    public ResponseEntity<Void> addSong(@RequestBody SongAddRequestDto requestDto) {
        songService.save(requestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/lrc")
    public LrcResponse convertToLrc(@RequestBody LrcRequest req) {
        String[] lines = req.lyrics.split("\\r?\\n");
        StringBuilder sb = new StringBuilder();

        int intervalSec = 5; // 5초 간격으로 타임스탬프 생성

        for (int i = 0; i < lines.length; i++) {
            int totalSeconds = i * intervalSec;
            int min = totalSeconds / 60;
            int sec = totalSeconds % 60;
            String timeTag = String.format("[%02d:%02d.00]", min, sec);
            sb.append(timeTag).append(lines[i].trim()).append("\n");
        }

        return new LrcResponse(sb.toString());
    }
}
