package org.example.prote.domain.emotion.domain.repository;

import org.example.prote.domain.emotion.domain.Emotion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmotionRepository extends MongoRepository<Emotion, String> {
    boolean existsByUserId(Long userId);

    Emotion findByUserId(Long userId);
}
