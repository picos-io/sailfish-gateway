package io.picos.sailfish.gateway.exception;

public class ApplicationDisabledException extends RuntimeException {
    public ApplicationDisabledException() {
    }

    public ApplicationDisabledException(String message) {
        super(message);
    }

    public ApplicationDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationDisabledException(Throwable cause) {
        super(cause);
    }

    public ApplicationDisabledException(String message, Throwable cause, boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
