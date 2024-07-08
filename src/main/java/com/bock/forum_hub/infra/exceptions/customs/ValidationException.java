package com.bock.forum_hub.infra.exceptions.customs;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
