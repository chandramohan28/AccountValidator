package com.jpmc.AccountValidator.handler;

import com.jpmc.AccountValidator.exception.BusinessException;
import com.jpmc.AccountValidator.exception.TechnicalException;
import com.jpmc.AccountValidator.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({TechnicalException.class})
    public final ResponseEntity<ErrorMessage> handle(final TechnicalException ex, final HttpServletRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getStatusCode(),
                new Date(),
                ex.getMessage(),
                request.getRequestURL().toString());

        return new ResponseEntity<ErrorMessage>(message, null, ex.getStatusCode());
    }

    @ExceptionHandler({BusinessException.class})
    public final ResponseEntity<ErrorMessage> handle(final BusinessException ex, final HttpServletRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getStatusCode(),
                new Date(),
                ex.getMessage(),
                request.getRequestURL().toString());

        return new ResponseEntity<ErrorMessage>(message, null, ex.getStatusCode());
    }

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ErrorMessage> handle(final Exception ex, final HttpServletRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getRequestURL().toString());

        return new ResponseEntity<ErrorMessage>(message, null, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
