package org.example.prote.domain.song.service;

import lombok.RequiredArgsConstructor;
import org.example.prote.domain.song.domain.Song;
import org.example.prote.domain.song.dto.SongAddRequestDto;
import org.example.prote.domain.song.exception.SongAlreadyExistsException;
import org.example.prote.domain.song.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SongService {
    private final SongRepository songRepository;

    @Transactional
    public void save(SongAddRequestDto requestDto) {
        if (songRepository.existsSongBySongUrl(requestDto.songUrl())) {
            throw SongAlreadyExistsException.EXCEPTION;
        }

        Song song = Song.builder()
                .title(requestDto.title())
                .artist(requestDto.artist())
                .playTime(requestDto.playTime())
                .albumCover(requestDto.albumCover())
                .lrcLyrics(requestDto.lrcLyrics())
                .songUrl(requestDto.songUrl())
                .build();

        songRepository.save(song);
    }
}
