package uk.co.joesharpcs.gaming.board;

import java.util.function.BiConsumer;

public record BoardLocation(int row, int col) {

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BoardLocation that = (BoardLocation) o;
    return row == that.row && col == that.col;
  }

  public static BoardLocation fromString(String raw) {
    String[] parts = raw.split(",");

    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid board location: " + raw);
    }

    int row = Integer.parseInt(parts[0], 10);
    int col = Integer.parseInt(parts[1], 10);

    return new BoardLocation(row, col);
  }

  /**
   * Given a location on a board, passes the location of all valid connected locations.
   *
   * @param boardSize The size of the board
   * @param row The row we are assessing
   * @param col The column we are assessing
   * @param receiver Receives the connected locations
   */
  public static void getAdjacent(
      int boardSize, int row, int col, BiConsumer<Integer, Integer> receiver) {
    getAdjacent(boardSize, row, col, false, receiver);
  }

  public static void getAdjacentWithDiagonals(
      int boardSize, int row, int col, BiConsumer<Integer, Integer> receiver) {
    getAdjacent(boardSize, row, col, true, receiver);
  }

  private static void getAdjacent(
      int boardSize,
      int row,
      int col,
      boolean includeDiagonals,
      BiConsumer<Integer, Integer> receiver) {
    int maxBoardIndex = boardSize - 1;

    if (col > 0) { // left
      receiver.accept(row, col - 1);
    }
    if (col < maxBoardIndex) { // right
      receiver.accept(row, col + 1);
    }
    if (row > 0) { // up
      receiver.accept(row - 1, col);
    }
    if (row < maxBoardIndex) { // down
      receiver.accept(row + 1, col);
    }

    if (includeDiagonals) {
      // Top left
      if ((col > 0) && (row > 0)) {
        receiver.accept(row - 1, col - 1);
      }

      // Top Right
      if ((row < maxBoardIndex) && (col > 0)) {
        receiver.accept(row + 1, col - 1);
      }

      // Bottom Left
      if ((row > 0) && (col < maxBoardIndex)) {
        receiver.accept(row - 1, col + 1);
      }

      // Bottom Right
      if ((row < maxBoardIndex) && (col < maxBoardIndex)) {
        receiver.accept(row + 1, col + 1);
      }
    }
  }
}
