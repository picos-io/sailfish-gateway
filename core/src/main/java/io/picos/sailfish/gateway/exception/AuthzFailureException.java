package io.picos.sailfish.gateway.exception;

public class AuthzFailureException extends RuntimeException {
    public AuthzFailureException() {
    }

    public AuthzFailureException(String message) {
        super(message);
    }

    public AuthzFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthzFailureException(Throwable cause) {
        super(cause);
    }

    public AuthzFailureException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
