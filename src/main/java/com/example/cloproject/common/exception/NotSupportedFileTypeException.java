package com.example.cloproject.common.exception;

public class NotSupportedFileTypeException extends RuntimeException {
    public NotSupportedFileTypeException() {
        super();
    }

    public NotSupportedFileTypeException(String message) {
        super(message);
    }

    public NotSupportedFileTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportedFileTypeException(Throwable cause) {
        super(cause);
    }

    protected NotSupportedFileTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
