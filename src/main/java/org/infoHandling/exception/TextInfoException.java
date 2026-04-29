package org.infoHandling.exception;

public class TextInfoException extends RuntimeException {

    public TextInfoException(String message) {
        super(message);
    }

    public TextInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}