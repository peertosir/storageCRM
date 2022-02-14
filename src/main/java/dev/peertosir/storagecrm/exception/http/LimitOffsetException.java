package dev.peertosir.storagecrm.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LimitOffsetException extends RuntimeException {
    public LimitOffsetException(String message) {
        super(message);
    }

    public static LimitOffsetException invalidOffset() {
        return new LimitOffsetException("Offset should be >= 0");
    }

    public static LimitOffsetException invalidLimit() {
        return new LimitOffsetException("Limit should be > 0");
    }
}
