package org.example.prote.domain.song.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "songs")
public class Song {
    @Id
    private String id;

    private String title;

    private String artist;

    private String playTime;

    private String albumCover;

    private String songUrl;

    private String lrcLyrics;
}
