package org.example.prote.domain.song.exception;

import org.example.prote.global.config.error.exception.BusinessException;
import org.example.prote.global.config.error.exception.ErrorCode;

public class SongAlreadyExistsException extends BusinessException {
    public static final BusinessException EXCEPTION =
            new SongAlreadyExistsException();

    private SongAlreadyExistsException() {super(ErrorCode.SONG_ALREADY_EXISTS);}
}
