package io.picos.sailfish.gateway.exception;

public class HasNoPermissionException extends RuntimeException {
    public HasNoPermissionException() {
    }

    public HasNoPermissionException(String message) {
        super(message);
    }

    public HasNoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public HasNoPermissionException(Throwable cause) {
        super(cause);
    }

    public HasNoPermissionException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
