package org.example.prote.global.config.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    USER_NOT_FOUND(404, "User Not Found"),
    EXPIRED_JWT(401, "Expired Jwt"),
    INVALID_JWT(401, "Invalid Jwt"),
    PROVIDER_NOT_SUPPORTED(400, "Provider Not Supported"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    INVALID_PASSWORD(400, "Invalid Password"),
    USER_ALREADY_EXISTS(409, "User Already Exists"),
    SONG_NOT_FOUND(404, "Song Not Found"),
    LIKE_SONGS_NOT_FOUND(404, "Like Songs Not Found"),
    SONG_ALREADY_EXISTS(409, "Song Already Exists"),;

    private final int status;
    private final String message;
}
