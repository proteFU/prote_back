package org.example.prote.global.security.oauth.exception;

import org.example.prote.global.config.error.exception.BusinessException;
import org.example.prote.global.config.error.exception.ErrorCode;

public class ProviderNotSupported extends BusinessException {
    public static final BusinessException EXCEPTION =
            new ProviderNotSupported();

    private ProviderNotSupported() {super(ErrorCode.PROVIDER_NOT_SUPPORTED);}
}
