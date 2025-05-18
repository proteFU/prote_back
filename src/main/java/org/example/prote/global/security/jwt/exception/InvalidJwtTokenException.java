package org.example.prote.global.security.jwt.exception;

import org.example.prote.global.config.error.exception.BusinessException;
import org.example.prote.global.config.error.exception.ErrorCode;

public class InvalidJwtTokenException extends BusinessException {
  public static final BusinessException EXCEPTION =
          new InvalidJwtTokenException();

  private InvalidJwtTokenException() { super(ErrorCode.INVALID_JWT); }
}
