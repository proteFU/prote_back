package org.example.prote.domain.song.exception;

import org.example.prote.global.config.error.exception.BusinessException;
import org.example.prote.global.config.error.exception.ErrorCode;

public class SongNotFoundExceptio extends BusinessException {
    public static final BusinessException EXCEPTION =
            new SongNotFoundExceptio();

    private SongNotFoundExceptio() {super(ErrorCode.USER_NOT_FOUND);}
}
