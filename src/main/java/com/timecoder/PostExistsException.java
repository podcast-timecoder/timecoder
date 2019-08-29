package com.timecoder;

public class PostExistsException extends RuntimeException {

    public PostExistsException(String message) {
        super(message);
    }

    public PostExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostExistsException(Throwable cause) {
        super(cause);
    }
}
