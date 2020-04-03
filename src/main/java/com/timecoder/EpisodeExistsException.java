package com.timecoder;

public class EpisodeExistsException extends RuntimeException {

    public EpisodeExistsException(String message) {
        super(message);
    }

    public EpisodeExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EpisodeExistsException(Throwable cause) {
        super(cause);
    }
}
