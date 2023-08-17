package uk.co.joesharpcs.gaming.gol;

import uk.co.joesharpcs.gaming.board.BoardLocation;
import uk.co.joesharpcs.gaming.board.GenericBoard;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

    public void iterate() {
        board.save();
        board.iterateBoardLocations().forEach(l -> {
            AtomicInteger neighbours = new AtomicInteger(0);
            BoardLocation.getAdjacentWithDiagonals(
                    board.getNumberRows(),
                    l.row(),
                    l.col(),
                    (r, c) -> {
                        if (board.getPrevious(r, c)) {
                            neighbours.incrementAndGet();
                        }
                    });

            var thisValue = board.get(l.row(), l.col());
            if (thisValue) {
                switch (neighbours.get()) {
                    // Solitude & Overpopulation
                    case 0, 1, 4 -> board.set(l.row(), l.col(), false);
                }
            } else {
                // Birth
                if (neighbours.get() == 3) {
                    board.set(l.row(), l.col(), true);
                }
            }
        });
    }

    public boolean isAlive(int row, int col) {
        return this.board.get(row, col);
    }

    public static GameOfLife fromString(final String value) {
        final GenericBoard<Boolean> board =
                GenericBoard.fromString(value, GameOfLife::cellFromString);

        return new GameOfLife(board);
    }

    private static Boolean cellFromString(String value) {
        return switch (value) {
            case ALIVE_CELL -> true;
            case DEAD_CELL -> false;
            default -> null;
        };
    }
}
