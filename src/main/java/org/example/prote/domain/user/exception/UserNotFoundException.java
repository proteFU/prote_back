package org.example.prote.domain.user.exception;

import org.example.prote.global.config.error.exception.BusinessException;
import org.example.prote.global.config.error.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public static final BusinessException EXCEPTION =
          new UserNotFoundException();

    private UserNotFoundException() {super(ErrorCode.USER_NOT_FOUND);}
}
