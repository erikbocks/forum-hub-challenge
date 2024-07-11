package com.bock.forum_hub.infra.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bock.forum_hub.infra.exceptions.customs.ValidationException;
import com.bock.forum_hub.util.RestErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        if (exception.getCause() instanceof NumberFormatException) {
            Class<?> type = exception.getRequiredType();
            if (type != null) {
                List<ErrorResponseDTO> errors = new ArrayList<>();

                String requiredType = type.getSimpleName();
                String parameter = exception.getName();
                errors.add(new ErrorResponseDTO(parameter, String.format("Parâmetro deve ser do tipo %s.", requiredType)));
                return response.badRequest("Houve um erro com os dados da requisição.", errors);
            }
        }
        return response.badRequest("Houve um erro com os dados da requisição.");
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<RestErrorResponse> handleMethodValidationException(HandlerMethodValidationException exception) {

        List<ParameterValidationResult> validationErrors = exception.getAllValidationResults();
        List<ErrorResponseDTO> errors = new ArrayList<>();

        validationErrors.forEach(ve -> {
            var resolvables = ve.getResolvableErrors();

            resolvables.forEach(e -> {
                Object[] arguments = e.getArguments();
                if(arguments != null && arguments.length > 0) {
                    var source = (DefaultMessageSourceResolvable) e.getArguments()[0];
                    String field = source.getDefaultMessage();
                    String message = e.getDefaultMessage();
                    errors.add(new ErrorResponseDTO(field, message));
                }
            });
        });

        return response.badRequest("Houve um erro ao validar os campos.", errors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RestErrorResponse> handleRuntimeException(RuntimeException exception) {
        return response.internalServerError(exception.getMessage());
    }
}
