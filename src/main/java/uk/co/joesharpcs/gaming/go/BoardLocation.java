package uk.co.joesharpcs.gaming.go;

import java.util.Objects;
import java.util.function.BiConsumer;

public class BoardLocation {
    final int row;
    final int col;

    public BoardLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardLocation that = (BoardLocation) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
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
     * @param boardSize The size of the board
     * @param row The row we are assessing
     * @param col The column we are assessing
     * @param receiver Receives the connected locations
     */
    static void getAdjacent(int boardSize,
                            int row, int col,
                            BiConsumer<Integer, Integer> receiver) {
        if (col > 0) { // left
            receiver.accept(row, col - 1);
        }
        if (col < boardSize - 1) { // right
            receiver.accept(row, col + 1);
        }
        if (row > 0) { // up
            receiver.accept(row - 1, col);
        }
        if (row < boardSize - 1) { // down
            receiver.accept( row + 1, col);
        }
    }

}
