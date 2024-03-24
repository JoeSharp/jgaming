package uk.co.joesharpcs.gaming.board;

public class InvalidBoardStateStringException extends RuntimeException {
  public InvalidBoardStateStringException(String msg) {
    super(msg);
  }
}
