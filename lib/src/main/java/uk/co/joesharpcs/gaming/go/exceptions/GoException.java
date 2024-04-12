package uk.co.joesharpcs.gaming.go.exceptions;

public class GoException extends RuntimeException {
  public GoException() {
    super();
  }

  public GoException(String msg) {
    super(msg);
  }
}
