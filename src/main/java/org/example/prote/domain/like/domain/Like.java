package org.example.prote.domain.like.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.prote.domain.song.domain.Song;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Document(collection = "likes")
public class Like {
    @Id
    private String id;

    private Long userId;

    @DBRef
    private Song song;

    @CreatedDate
    private LocalDateTime createdAt;
}
