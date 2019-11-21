package com.netreaders.exception;

import com.netreaders.models.ResponseMessage;
import com.netreaders.utils.ResponseMessagePrepearer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * The global exception handler
 *
 * @author Andrii Kobyliuyk
 */
@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(DataBaseSQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleSQLException(DataBaseSQLException ex) {
        ResponseMessage responseMessage = new ResponseMessage<>();
        ResponseMessagePrepearer.prepareMessage(responseMessage, ex.getMessage());
        return responseMessage;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ResponseMessage responseMessage = new ResponseMessage<>();
        ResponseMessagePrepearer.prepareMessage(responseMessage, ex.getMessage());
        return responseMessage;
    }

    // Other list of exceptions
    // ...
}
