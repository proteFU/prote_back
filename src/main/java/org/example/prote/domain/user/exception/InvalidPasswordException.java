package org.example.prote.domain.user.exception;

import org.example.prote.global.config.error.exception.BusinessException;
import org.example.prote.global.config.error.exception.ErrorCode;

public class InvalidPasswordException extends BusinessException {
    public static final BusinessException EXCEPTION =
            new InvalidPasswordException();

    private InvalidPasswordException() {super(ErrorCode.INVALID_PASSWORD);}
}
