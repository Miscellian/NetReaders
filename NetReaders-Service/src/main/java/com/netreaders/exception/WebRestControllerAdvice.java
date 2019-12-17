package com.netreaders.exception;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.NoSuchModelException;
import com.netreaders.exception.classes.NotImplementedYetException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * The global exception handler
 *
 * @author Andrii Kobyliuyk
 */
@RestControllerAdvice
public class WebRestControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataBaseSQLException.class)
    public ResponseMessage handleSQLException(HttpServletRequest request, DataBaseSQLException ex) {

        return new ResponseMessage(request.getRequestURI(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handleArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {

        return new ResponseMessage(request.getRequestURI(), ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchModelException.class)
    public ResponseMessage handleNoSuchModelException(HttpServletRequest request, NoSuchModelException ex) {

        return new ResponseMessage(request.getRequestURI(), ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseMessage handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {

        return new ResponseMessage(request.getRequestURI(), ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(NotImplementedYetException.class)
    public ResponseMessage handle(HttpServletRequest request, NotImplementedYetException ex) {

        return new ResponseMessage(request.getRequestURI(), ex.getMessage());
    }

    @AllArgsConstructor
    private static class ResponseMessage {

        private String requestURI;
        private String errorMessage;
    }
}
