package com.ratracejoe.jgaming.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CreatedByDuplicateException.class)
  protected ResponseEntity<String> handleDuplication(CreatedByDuplicateException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getLocalizedMessage());
  }

  @ExceptionHandler(GameNotFoundException.class)
  protected ResponseEntity<String> handleNotFound(GameNotFoundException ex) {
    return ResponseEntity.notFound().build();
  }
}
