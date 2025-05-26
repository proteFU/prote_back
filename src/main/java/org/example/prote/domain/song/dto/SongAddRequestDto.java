package org.example.prote.domain.song.dto;

public record SongAddRequestDto(
        String title,
        String artist,
        String playTime,
        String albumCover,
        String songUrl,
        String lrcLyrics
) {
}
