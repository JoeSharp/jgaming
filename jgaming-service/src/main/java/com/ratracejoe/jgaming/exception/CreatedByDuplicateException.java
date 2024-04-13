package com.ratracejoe.jgaming.exception;

import com.ratracejoe.jgaming.model.GameType;

public class CreatedByDuplicateException extends Exception {
  public CreatedByDuplicateException(GameType gameType, String createdBy) {
    super(String.format("A game of type %s has already been created by %s", gameType, createdBy));
  }
}
