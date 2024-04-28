package com.ratracejoe.jgaming.exception;

import java.util.UUID;

public class GameNotFoundException extends Exception {
  public GameNotFoundException(UUID id) {
    super(String.format("Game with ID %s not found", id.toString()));
  }

  public GameNotFoundException(String createdBy) {
    super(String.format("Game created by %s not found", createdBy));
  }
}
