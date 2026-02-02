package com.bnppf.developmentbooks.infrastructure.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationErrors(MethodArgumentNotValidException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed for request parameters"
        );
        problem.setTitle("Invalid Request");
        problem.setProperty("timestamp", Instant.now());

        exception.getBindingResult().getFieldErrors().forEach(error ->
                problem.setProperty("error_" + error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(problem);
    }
}