package com.netreaders.exception;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.NoSuchModelException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * The global exception handler
 *
 * @author Andrii Kobyliuyk
 */
@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(DataBaseSQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseMessage handleSQLException(HttpServletRequest request, DataBaseSQLException ex) {
        return new ResponseMessage(request.getRequestURI(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseMessage handleArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        return new ResponseMessage(request.getRequestURI(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(NoSuchModelException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseMessage handleNoSuchModelException(HttpServletRequest request, NoSuchModelException ex) {
        return new ResponseMessage(request.getRequestURI(), ex.getLocalizedMessage());
    }

    @AllArgsConstructor
    private static class ResponseMessage {
        private String code;
        private String errorMessage;
    }
}
