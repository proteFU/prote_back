package org.example.prote.domain.like.repository;

import org.example.prote.domain.like.domain.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    Optional<Like> findOneByUserIdAndSongId(Long userId, String songId);

    List<Like> findAllByUserId(Long userId);
}
