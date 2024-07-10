package com.bock.forum_hub.infra.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bock.forum_hub.infra.exceptions.customs.ValidationException;
import com.bock.forum_hub.util.RestErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private RestErrorResponse response;

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<RestErrorResponse> handleValidationException(ValidationException exception) {
        return response.badRequest(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<FieldError> errors = exception.getFieldErrors();
        List<ErrorResponseDTO> errorResponseList = errors.stream()
                .map(ErrorResponseDTO::new)
                .toList();
        return response.badRequest("Houve um erro com os atributos do corpo da requisição.", errorResponseList);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<RestErrorResponse> handleJWTVerification(JWTVerificationException exception) {
        return response.badRequest(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestErrorResponse> handleIllegalArgument(IllegalArgumentException exception) {
        return response.badRequest(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RestErrorResponse> handleRuntimeException(RuntimeException exception) {
        return response.internalServerError(exception.getMessage());
    }
}
