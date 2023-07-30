package uk.co.joesharpcs.gaming.go;

import uk.co.joesharpcs.gaming.go.exceptions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GoBoard {
    private static final int DEFAULT_SIZE = 19;

    private int size;
    private Player whosTurn;

    private PointValue[][] board;

    GoBoard() {
        this(DEFAULT_SIZE);
    }

    GoBoard(int size, String initialState) throws InvalidBoardStateStringException {
        this(size);
        fromString(initialState);
    }

    GoBoard(int size) {
        this.size = size;
        this.whosTurn = Player.BLACK;
        this.board = new PointValue[this.size][this.size];
        for (int row=0; row<this.size; row++) {
            for (int col=0; col<this.size; col++) {
                this.board[row][col] = PointValue.EMPTY;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Player getWhosTurn() {
        return this.whosTurn;
    }

    public void move(Player player, int row, int col) throws GoException {
        if (!this.whosTurn.equals(player)) {
            throw new WrongPlayerException();
        }

        this.validatePoint(row, col);

        if (!PointValue.EMPTY.equals(this.board[row][col])) {
            throw new AlreadyOccupiedException();
        }

        this.board[row][col] = player.getPointValue();

        this.whosTurn = this.whosTurn.nextPlayer();
    }

    private void validatePoint(int row, int col) throws InvalidPointLocationException {
        if (row < 0 || row >= this.size) throw new InvalidPointLocationException();
        if (col < 0 || col >= this.size) throw new InvalidPointLocationException();
    }

    private void fromString(String asString) throws InvalidBoardStateStringException {
        List<String> rows = Arrays
                .stream(asString.split("\n"))
                .filter(row -> !row.isBlank())
                .map(String::trim)
                .toList();

        if (rows.size() != this.size) {
            throw new InvalidBoardStateStringException("Incorrect number of rows");
        }

        for (int rowIndex = 0; rowIndex < this.size; rowIndex++) {
            List<String> cellValues = Arrays.stream(rows.get(rowIndex).split("")).toList();

            if (cellValues.size() != this.size) {
                throw new InvalidBoardStateStringException(
                        String.format("Incorrect number of columns in row %d", rowIndex));
            }

            for (int cellIndex=0; cellIndex < this.size; cellIndex++) {
                var rawValue = cellValues.get(cellIndex);

                try {
                    var value = PointValue.fromChar(rawValue);
                    this.board[rowIndex][cellIndex] = value;
                } catch (InvalidPointValueException e) {
                    throw new InvalidBoardStateStringException(
                            String.format("Invalid value for row %d, cell: %d, '%s'", rowIndex, cellIndex, rawValue));
                }
            }
        }
    }

    public PointValue getValue(int row, int col) throws InvalidPointLocationException {
        this.validatePoint(row, col);

        return this.board[row][col];
    }
}
