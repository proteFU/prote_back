package org.example.prote.domain.song.controller;

import lombok.RequiredArgsConstructor;
import org.example.prote.domain.song.dto.SongAddRequestDto;
import org.example.prote.domain.song.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
