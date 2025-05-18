package org.example.prote.global.security.jwt.exception;

import org.example.prote.global.config.error.exception.BusinessException;
import org.example.prote.global.config.error.exception.ErrorCode;

public class ExpiredJwtTokenException extends BusinessException {
  public static final BusinessException EXCEPTION =
          new ExpiredJwtTokenException();

  private ExpiredJwtTokenException() { super(ErrorCode.EXPIRED_JWT); }
}