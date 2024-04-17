package uk.co.joesharpcs.gaming.gol;

import java.util.concurrent.atomic.AtomicInteger;
import uk.co.joesharpcs.gaming.board.BoardLocation;
import uk.co.joesharpcs.gaming.board.GridBoard;

public class GameOfLife {
  public static final String DEAD_CELL = ".";
  public static final String ALIVE_CELL = "0";

  public static final int DEFAULT_DIMENSION = 100;

  private final GridBoard<Boolean> board;

  public GameOfLife() {
    this(DEFAULT_DIMENSION);
  }

  public GameOfLife(int dimension) {
    this(new GridBoard<>(dimension, false));
  }

  public GameOfLife(GridBoard<Boolean> board) {
    this.board = board;
  }

  public void iterate() {
    board.save();
    board
        .iterateBoardLocations()
        .forEach(
            l -> {
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

  @Override
  public String toString() {
    return board.toString(GameOfLife::cellToString);
  }

  public String toPrintableString() {
    return board.toPrintableString(GameOfLife::cellToString);
  }

  public static GameOfLife fromString(final String value) {
    final GridBoard<Boolean> board = GridBoard.fromString(value, GameOfLife::cellFromString);

    return new GameOfLife(board);
  }

  private static Boolean cellFromString(String value) {
    return switch (value) {
      case ALIVE_CELL -> true;
      case DEAD_CELL -> false;
      default -> null;
    };
  }

  private static String cellToString(Boolean value) {
    return Boolean.TRUE.equals(value) ? ALIVE_CELL : DEAD_CELL;
  }
}
