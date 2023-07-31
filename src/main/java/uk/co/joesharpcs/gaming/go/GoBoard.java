package uk.co.joesharpcs.gaming.go;

import uk.co.joesharpcs.gaming.go.exceptions.*;

import java.util.*;
import java.util.stream.Collectors;

public class GoBoard {
    private static final int DEFAULT_SIZE = 19;

    private final int size;
    private Player whosTurn;

    private final PointValue[][] board;

    private final Map<Player, Integer> captures;

    GoBoard() {
        this(DEFAULT_SIZE);
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
        this.captures = Arrays.stream(Player.values()).collect(Collectors.toMap(p -> p, p -> 0));
    }

    public int getSize() {
        return size;
    }

    public Player getWhosTurn() {
        return this.whosTurn;
    }

    public Integer getCaptures(Player player) {
        return this.captures.get(player);
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

    @Override
    public String toString() {
        StringJoiner rowJoiner = new StringJoiner("\n");

        for (var row : board) {
            StringJoiner cellJoiner = new StringJoiner(" ");
            for (var cell : row) {
                cellJoiner.add(cell.toString());
            }
            rowJoiner.add(cellJoiner.toString());
        }

        return rowJoiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoBoard goBoard = (GoBoard) o;
        return size == goBoard.size && Arrays.deepEquals(board, goBoard.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size, whosTurn);
        result = 31 * result + Arrays.deepHashCode(board);
        return result;
    }

    public static GoBoard fromString(String asString) throws InvalidBoardStateStringException {

        List<String> rows = Arrays
                .stream(asString.split("\n"))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .toList();

        final GoBoard board = new GoBoard(rows.size());

        for (int rowIndex = 0; rowIndex < board.size; rowIndex++) {
            List<String> cellValues = Arrays.stream(rows.get(rowIndex).split(""))
                    .map(String::trim)
                    .filter(StringUtils::isNotBlank)
                    .toList();

            if (cellValues.size() != board.size) {
                throw new InvalidBoardStateStringException(
                        String.format("Incorrect number of columns in row %d", rowIndex));
            }

            for (int cellIndex=0; cellIndex < board.size; cellIndex++) {
                var rawValue = cellValues.get(cellIndex);

                try {
                    var value = PointValue.fromChar(rawValue);
                    board.board[rowIndex][cellIndex] = value;
                } catch (InvalidPointValueException e) {
                    throw new InvalidBoardStateStringException(
                            String.format("Invalid value for row %d, cell: %d, '%s'", rowIndex, cellIndex, rawValue));
                }
            }
        }

        return board;
    }

    public PointValue getValue(int row, int col) throws InvalidPointLocationException {
        this.validatePoint(row, col);

        return this.board[row][col];
    }
}
