package io.picos.sailfish.gateway.exception;

public class AuthorizationHeaderRequiredException extends RuntimeException {
    public AuthorizationHeaderRequiredException() {
    }

    public AuthorizationHeaderRequiredException(String message) {
        super(message);
    }

    public AuthorizationHeaderRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationHeaderRequiredException(Throwable cause) {
        super(cause);
    }

    public AuthorizationHeaderRequiredException(String message, Throwable cause, boolean enableSuppression,
                                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
