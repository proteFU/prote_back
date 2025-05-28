package org.example.prote.domain.song.dto;

public record LrcRequestDto(
        String songUrl,
        String lyrics
) {
}
