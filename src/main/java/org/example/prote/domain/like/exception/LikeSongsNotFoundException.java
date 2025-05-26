package org.example.prote.domain.like.exception;

import org.example.prote.global.config.error.exception.BusinessException;
import org.example.prote.global.config.error.exception.ErrorCode;

public class LikeSongsNotFoundException extends BusinessException {
  public static final BusinessException EXCEPTION =
          new LikeSongsNotFoundException();

  private LikeSongsNotFoundException() {super(ErrorCode.LIKE_SONGS_NOT_FOUND);}
}
