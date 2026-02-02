package com.bnppf.developmentbooks.infrastructure.api.advice;

import com.bnppf.developmentbooks.infrastructure.api.validation.ValidationErrors;
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
                ValidationErrors.REQUEST_VALIDATION_DETAIL
        );
        problem.setTitle(ValidationErrors.REQUEST_VALIDATION_TITLE);
        problem.setProperty(ValidationErrors.REQUEST_VALIDATION_TIME_PROPERTY, Instant.now());

        exception.getBindingResult().getFieldErrors().forEach(error ->
                problem.setProperty(ValidationErrors.REQUEST_VALIDATION_FIELD_ERROR_PREFIX + error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(problem);
    }
}