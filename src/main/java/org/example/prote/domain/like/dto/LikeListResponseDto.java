package org.example.prote.domain.like.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LikeListResponseDto {
    List<LikeSongDto> songs;
}
