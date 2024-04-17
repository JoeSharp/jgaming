package com.ratracejoe.jgaming.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(GameNotFoundException.class)
  protected ResponseEntity<String> handleNotFound(GameNotFoundException ex) {
    log.error("Game Not Found", ex);
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(InvalidGameParameters.class)
  protected ResponseEntity<String> handleInvalidParams(InvalidGameParameters ex) {
    log.error("Invalid parameters", ex);
    return ResponseEntity.badRequest().build();
  }
}
