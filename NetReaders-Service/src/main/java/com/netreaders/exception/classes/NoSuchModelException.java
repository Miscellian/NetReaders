package com.netreaders.exception.classes;

public class NoSuchModelException extends RuntimeException {

    public NoSuchModelException() {
        super();
    }

    public NoSuchModelException(String message) {
        super(message);
    }

    public NoSuchModelException(Throwable cause) {
        super(cause);
    }

    public NoSuchModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
