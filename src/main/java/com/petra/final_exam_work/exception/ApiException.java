package com.petra.final_exam_work.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final Map<String, String> errors;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.errors = null;
    }

    public ApiException( String message, Map<String, String> errors, HttpStatus status){
        super(message);
        this.status = status;
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
