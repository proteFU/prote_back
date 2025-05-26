package org.example.prote.domain.like.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LikeSongDto(
        String title,
        String artist,
        String albumCover,
        LocalDateTime createdAt
) {
}
