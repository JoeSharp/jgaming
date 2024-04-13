package com.ratracejoe.jgaming.exception;

public class CreatedByDuplicateException extends Exception {
  public CreatedByDuplicateException(String gameType, String createdBy) {
    super(String.format("A game of type %s has already been created by %s", gameType, createdBy));
  }
}
