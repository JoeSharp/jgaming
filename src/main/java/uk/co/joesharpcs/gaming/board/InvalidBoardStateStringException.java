package uk.co.joesharpcs.gaming.board;

import uk.co.joesharpcs.gaming.go.exceptions.GoException;

public class InvalidBoardStateStringException extends RuntimeException {
    public InvalidBoardStateStringException(String msg) {
        super(msg);
    }
}
