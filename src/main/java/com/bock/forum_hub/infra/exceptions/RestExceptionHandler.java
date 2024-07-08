package com.bock.forum_hub.infra.exceptions;

import com.bock.forum_hub.infra.exceptions.customs.ValidationException;
import com.bock.forum_hub.util.RestErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private RestErrorResponse response;

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<RestErrorResponse> handleValidationException(ValidationException exception) {
        return response.badRequest(exception.getMessage());
    }
}
