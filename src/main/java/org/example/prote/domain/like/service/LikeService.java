package org.example.prote.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.example.prote.domain.like.domain.Like;
import org.example.prote.domain.like.dto.LikeAddRequestDto;
import org.example.prote.domain.like.dto.LikeListResponseDto;
import org.example.prote.domain.like.dto.LikeSongDto;
import org.example.prote.domain.like.exception.LikeSongsNotFoundException;
import org.example.prote.domain.like.repository.LikeRepository;
import org.example.prote.domain.song.domain.Song;
import org.example.prote.domain.song.exception.SongNotFoundExceptio;
import org.example.prote.domain.song.repository.SongRepository;
import org.example.prote.global.security.auth.AuthDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final SongRepository songRepository;

    @Transactional
    public void like(LikeAddRequestDto requestDto, AuthDetails authDetails) {
        Optional<Song> optionalSong = songRepository.findOneBySongUrl(requestDto.songUrl());

        if(optionalSong.isPresent()){
            Song song = optionalSong.get();

            Optional<Like> optionalLike = likeRepository
                    .findOneByUserIdAndSongId(authDetails.getUserId(), optionalSong.get().getId());

            if (optionalLike.isPresent()){
                Like like = optionalLike.get();
                likeRepository.delete(like);
            } else {
                    createLike(authDetails.getUserId(), song);
            }
        } else throw SongNotFoundExceptio.EXCEPTION;
    }

    private void createLike(Long userId, Song song) {
        Like like = Like.builder()
                .userId(userId)
                .song(song)
                .createdAt(LocalDateTime.now())
                .build();

        likeRepository.save(like);
    }

    public LikeListResponseDto getLikeList(AuthDetails authDetails) {
        List<Like> likeList = likeRepository.findAllByUserId(authDetails.getUserId());

        if(likeList.isEmpty()){
            throw LikeSongsNotFoundException.EXCEPTION;
        }

        List<LikeSongDto> likeSongDtoList = new ArrayList<>();

        for (Like like : likeList) {
            likeSongDtoList.add(createSongDto(like));
        }

        return new LikeListResponseDto(likeSongDtoList);
    }

    private LikeSongDto createSongDto(Like like) {
        LikeSongDto songDto = LikeSongDto.builder()
                .title(like.getSong().getTitle())
                .artist(like.getSong().getArtist())
                .albumCover(like.getSong().getAlbumCover())
                .createdAt(like.getCreatedAt())
                .build();

        return songDto;
    }
}
