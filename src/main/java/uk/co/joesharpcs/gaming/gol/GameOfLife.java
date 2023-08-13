package uk.co.joesharpcs.gaming.gol;

import uk.co.joesharpcs.gaming.board.GenericBoard;

public class GameOfLife {
    public static final String DEAD_CELL = ".";
    public static final String ALIVE_CELL = "0";

    public static final int DEFAULT_DIMENSION = 100;

    private final GenericBoard<Boolean> board;

    public GameOfLife() {
        this(DEFAULT_DIMENSION);
    }

    public GameOfLife(int dimension) {
        this(new GenericBoard<>(dimension, false));
    }

    public GameOfLife(GenericBoard<Boolean> board) {
        this.board = board;
    }

    public boolean isAlive(int row, int col) {
        return this.board.get(row, col);
    }

    public static GameOfLife fromString(final String value) {
        final GenericBoard<Boolean> board =
                GenericBoard.fromString(value, ALIVE_CELL::equals);

        return new GameOfLife(board);
    }
}
