package com.netreaders.exception;

// Create unchecked custom exception for SQLException
public class DataBaseSQLException extends RuntimeException {

    public DataBaseSQLException(String message) {
        super(message);
    }
}
