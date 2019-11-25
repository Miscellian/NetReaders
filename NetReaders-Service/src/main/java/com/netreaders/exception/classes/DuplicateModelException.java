package com.netreaders.exception.classes;

public class DuplicateModelException extends RuntimeException {

    public DuplicateModelException() {
        super();
    }

    public DuplicateModelException(String message) {
        super(message);
    }

    public DuplicateModelException(Throwable cause) {
        super(cause);
    }

    public DuplicateModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
