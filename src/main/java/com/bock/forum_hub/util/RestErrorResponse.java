package com.bock.forum_hub.util;

import com.bock.forum_hub.infra.exceptions.ErrorResponseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class RestErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private List<String> messages;
    private List<ErrorResponseDTO> errors;

    public RestErrorResponse() {
        this.timestamp = LocalDateTime.now();
        this.status = HttpStatus.BAD_REQUEST.value();
        this.messages = new ArrayList<>();
        this.errors = new ArrayList<>();
    }

    public void setMessage(String message) {
        getMessages().add(message);
    }

    public ResponseEntity<RestErrorResponse> badRequest(String message) {
        RestErrorResponse response = new RestErrorResponse();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        response.setMessage(message);
        response.setStatus(status.value());

        return ResponseEntity.status(status.value()).body(response);
    }

    public ResponseEntity<RestErrorResponse> badRequest(String message, List<ErrorResponseDTO> errors) {
        RestErrorResponse response = new RestErrorResponse();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        response.setMessage(message);
        response.setStatus(status.value());
        response.setErrors(errors);

        return ResponseEntity.status(status.value()).body(response);
    }

    public ResponseEntity<RestErrorResponse> internalServerError(String message) {
        RestErrorResponse response = new RestErrorResponse();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        response.setMessage(message);
        response.setStatus(status.value());

        return ResponseEntity.status(status).body(response);
    }
}
