package com.bbco.practice.web.excetion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> badRequest(IllegalArgumentException e) {
        log.info("[badRequest] : [{}]", e.toString());
        ErrorResult errorResult = new ErrorResult("bad parameter", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> serverError(Exception e) {
        log.info("[serverError] : [{}]", e.toString());
        ErrorResult errorResult = new ErrorResult("server error", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
