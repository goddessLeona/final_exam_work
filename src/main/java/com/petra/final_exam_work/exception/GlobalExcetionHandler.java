package com.petra.final_exam_work.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;


import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExcetionHandler {

    //401
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "invalid credentials"));
    }

    //403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(Map.of("error", "Access denied"));
    }

    //validation error messages
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex
    ){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    //Api exceptions and response
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse>handleApiException(ApiException ex){

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                ex.getStatus().value()
        );

          return new ResponseEntity<>(error, ex.getStatus());

    }
}
