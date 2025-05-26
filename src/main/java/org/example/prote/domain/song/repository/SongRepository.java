package org.example.prote.domain.song.repository;

import org.example.prote.domain.song.domain.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends MongoRepository<Song, String> {
    Optional<Song> findOneBySongUrl(String songUrl);

    boolean existsSongBySongUrl(String songUrl);
}
