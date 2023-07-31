package uk.co.joesharpcs.gaming.go;

import java.util.function.BiConsumer;

public interface BoardLocation {
    /**
     * Given a location on a board, passes the location of all valid connected locations.
     * @param boardSize The size of the board
     * @param row The row we are assessing
     * @param col The column we are assessing
     * @param receiver Receives the connected locations
     */
    static void getConnected(int boardSize, int row, int col, BiConsumer<Integer, Integer> receiver) {
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
