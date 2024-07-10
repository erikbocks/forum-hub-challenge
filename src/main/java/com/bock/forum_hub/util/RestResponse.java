package com.bock.forum_hub.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class RestResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private List<String> messages;
    private Object result;

    public RestResponse() {
        this.timestamp = LocalDateTime.now();
        this.status = HttpStatus.OK.value();
        this.messages = new ArrayList<>();
    }

    public void setMessage(String message) {
        getMessages().add(message);
    }

    public ResponseEntity<RestResponse> created(URI uri, String message, Object object) {
        RestResponse response = new RestResponse();
        HttpStatus status = HttpStatus.CREATED;

        response.setStatus(status.value());
        response.setMessage(message);
        response.setResult(object);

        return ResponseEntity.status(status).location(uri).body(response);
    }

    public ResponseEntity<RestResponse> ok(String message, Object object) {
        RestResponse response = new RestResponse();
        HttpStatus status = HttpStatus.OK;

        response.setStatus(status.value());
        response.setMessage(message);
        response.setResult(object);

        return ResponseEntity.status(status).body(response);
    }
}
