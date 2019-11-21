package com.netreaders.exception;

// Create unchecked custom exception for SQLException
public class DataBaseSQLException extends RuntimeException {

    public DataBaseSQLException(String message) {
        super(message);
    }

    public DataBaseSQLException(Throwable cause) {
        super(cause);
    }

    public DataBaseSQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
