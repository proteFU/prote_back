package org.example.prote.domain.user.exception;

import org.example.prote.global.config.error.exception.BusinessException;
import org.example.prote.global.config.error.exception.ErrorCode;

public class UserAlreadyExistsException extends BusinessException {
    public static final BusinessException EXCEPTION =
            new UserAlreadyExistsException();

    private UserAlreadyExistsException() {super(ErrorCode.USER_ALREADY_EXISTS);}
}
