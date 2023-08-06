package uk.co.joesharpcs.gaming.go;

import uk.co.joesharpcs.gaming.go.exceptions.*;
import uk.co.joesharpcs.gaming.utils.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class GoBoard {

    private static final int DEFAULT_SIZE = 19;

    private final int size;
    private Player whosTurn;

    private final PointValue[][] board;
    private final PointValue[][] savedBoard;

    private final Map<Player, AtomicInteger> captures;

    private final Queue<Integer> lastTwoBoardHashes;

    private boolean isGameUnderway;

    private boolean previousPlayerPassed;

    GoBoard() {
        this(DEFAULT_SIZE);
    }

    GoBoard(int size) {
        this.size = size;
        this.whosTurn = Player.BLACK;
        this.board = new PointValue[this.size][this.size];
        this.savedBoard = new PointValue[this.size][this.size];
        for (int row=0; row<this.size; row++) {
            for (int col=0; col<this.size; col++) {
                this.board[row][col] = PointValue.EMPTY;
                this.savedBoard[row][col] = PointValue.EMPTY;
            }
        }
        this.captures = Arrays.stream(Player.values())
                .collect(Collectors.toMap(p -> p, p -> new AtomicInteger(0)));
        this.lastTwoBoardHashes = new ArrayDeque<>();
        this.lastTwoBoardHashes.add(Arrays.deepHashCode(this.board));
        this.isGameUnderway = true;
        this.previousPlayerPassed = false;
    }

    public int getSize() {
        return size;
    }

    public Player getWhosTurn() {
        return this.whosTurn;
    }

    public Integer getCaptures(Player player) {
        return this.captures.get(player).get();
    }

    public GoBoard withPreexistingCaptures(Player player, int captures) {
        this.captures.get(player).set(captures);
        return this;
    }

    private void copyBoard(PointValue[][] src, PointValue[][] dest) {
        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                dest[row][col] = src[row][col];
            }
        }
    }

    private void saveBoard() {
        copyBoard(board, savedBoard);
    }
    private void restoreBoard() {
        copyBoard(savedBoard, board);
    }

    public void move(int row, int col) throws GoException {
        move(whosTurn, row, col);
    }

    public void move(BoardLocation location) throws GoException {
        move(whosTurn, location.getRow(), location.getCol());
    }

    public void move(Player player, int row, int col) throws GoException {
        saveBoard();

        if (!this.whosTurn.equals(player)) {
            throw new WrongPlayerException();
        }

        checkPointIsOnBoard(row, col);

        if (!PointValue.EMPTY.equals(this.board[row][col])) {
            throw new AlreadyOccupiedException();
        }

        this.previousPlayerPassed = false;
        this.board[row][col] = player.getPointValue();

        boolean capturesMade = checkCaptures(player, row, col);

        // Check for suicidal move
        if (!capturesMade) {
            try {
                checkSuicidalMove(player, row, col);
            } catch (SuicidalMoveException e) {
                restoreBoard();
                throw e;
            }
        }

        // Check for Ko
        if (capturesMade) {
            try {
                checkKo();
            } catch (KoViolationException e) {
                this.captures.get(player).decrementAndGet();
                restoreBoard();
                throw e;
            }
        }

        this.whosTurn = this.whosTurn.otherPlayer();
    }
    private void checkSuicidalMove(Player player, int row, int col) throws SuicidalMoveException {
        AtomicBoolean libertiesFound = new AtomicBoolean(false);
        Set<String> dontCheckHereAgain = new HashSet<>();
        dontCheckHereAgain.add(getLocationKey(row, col));
        findLiberties(dontCheckHereAgain,
                player,
                row, col,
                (r, c) -> libertiesFound.set(true),
                (r, c) -> {});
        if (!libertiesFound.get()) {
            throw new SuicidalMoveException();
        }
    }

    private void checkKo() throws KoViolationException {
        int boardHash = Arrays.deepHashCode(board);

        if (lastTwoBoardHashes.stream().anyMatch(b -> b.equals(boardHash))) {
            throw new KoViolationException();
        } else {
            lastTwoBoardHashes.add(boardHash);

            while (lastTwoBoardHashes.size() > 2) {
                lastTwoBoardHashes.remove();
            }
        }
    }

    private boolean checkCaptures(Player player, int row, int col) {
        AtomicInteger playerCaptureCount = captures.get(player);
        AtomicBoolean capturesMade = new AtomicBoolean(false);
        BoardLocation.getAdjacent(size, row, col,
                (r, c) -> calculateCaptures(player, r, c).forEach(loc -> {
                    capturesMade.set(true);
                    playerCaptureCount.incrementAndGet();
                    board[loc.getRow()][loc.getCol()] = PointValue.EMPTY;
                })
        );

        return capturesMade.get();
    }

    private Set<BoardLocation> calculateCaptures(Player player, int row, int col) {
        // Are we also occupying this square?
        if (player.getPointValue().equals(board[row][col])) {
            return Collections.emptySet();
        }

        // Is the square empty?
        if (PointValue.EMPTY.equals(board[row][col])) {
            return Collections.emptySet();
        }

        // It contains the other player, does the other player have any liberties from this spot?
        AtomicBoolean libertyFound = new AtomicBoolean(false);
        Set<BoardLocation> group = new HashSet<>();
        group.add(new BoardLocation(row, col));

        findLiberties(player.otherPlayer(),
                row, col,
                (r, c) -> libertyFound.set(true),
                (r, c) -> group.add(new BoardLocation(r, c))
        );

        // If the other player does not have any liberties, this group is being captured
        if (!libertyFound.get()) {
            return group;
        }

        return Collections.emptySet();
    }
    public GoBoard withWhosTurn(Player whosTurn) {
        this.whosTurn = whosTurn;
        return this;
    }

    public void findLiberties(Player asPlayer,
                              int row, int col,
                              BiConsumer<Integer, Integer> libertyReceiver,
                              BiConsumer<Integer, Integer> groupReceiver) {
        findLiberties(new HashSet<>(), asPlayer, row, col, libertyReceiver, groupReceiver);
    }

    private static String getLocationKey(int row, int col) {
        return String.format("%d-%d", row, col);
    }

    private void findLiberties(Set<String> alreadyAssessed,
                               Player asPlayer,
                               int row, int col,
                               BiConsumer<Integer, Integer> libertyReceiver,
                               BiConsumer<Integer, Integer> groupReceiver) {
        BoardLocation.getAdjacent(size, row, col, (r, c) -> {
            // Skip it if we've already assessed it
            String key = getLocationKey(r, c);
            PointValue value = board[r][c];
            if (alreadyAssessed.contains(key)) return;
            alreadyAssessed.add(key);

            if (PointValue.EMPTY.equals(value)) {
                // Empty..it's a liberty!
                libertyReceiver.accept(r, c);
            } else if (asPlayer.getPointValue().equals(value)) {
                // Have we found more of ourselves?
                groupReceiver.accept(r, c);
                findLiberties(alreadyAssessed, asPlayer, r, c, libertyReceiver, groupReceiver);
            }
        });
    }

    private void checkPointIsOnBoard(int row, int col) throws InvalidPointLocationException {
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

    public String toStringHelpful() {
        StringJoiner rowJoiner = new StringJoiner("\n");

        StringJoiner headingJoiner = new StringJoiner(" ");
        headingJoiner.add(".");
        for (int i=0; i<size; i++) {
            headingJoiner.add(Integer.toString(i, 10));
        }
        rowJoiner.add(headingJoiner.toString());

        for (int i=0; i<size; i++) {
            StringJoiner cellJoiner = new StringJoiner(" ");
            cellJoiner.add(Integer.toString(i,10));
            for (var cell : board[i]) {
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
        final Map<Player, AtomicInteger> playerCount = Arrays.stream(Player.values())
                .collect(Collectors.toMap(p -> p, p -> new AtomicInteger(0)));

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

                    Player.fromPointValue(value)
                            .map(playerCount::get)
                            .ifPresent(AtomicInteger::incrementAndGet);

                } catch (InvalidPointValueException e) {
                    throw new InvalidBoardStateStringException(
                            String.format("Invalid value for row %d, cell: %d, '%s'", rowIndex, cellIndex, rawValue));
                }
            }
        }

        if (playerCount.get(Player.BLACK).get() > playerCount.get(Player.WHITE).get()) {
            board.whosTurn = Player.WHITE;
        } else {
            board.whosTurn = Player.BLACK;
        }
        board.lastTwoBoardHashes.clear();
        board.lastTwoBoardHashes.add(Arrays.deepHashCode(board.board));

        return board;
    }

    public PointValue getValue(int row, int col) throws InvalidPointLocationException {
        this.checkPointIsOnBoard(row, col);

        return this.board[row][col];
    }

    public void pass(Player player) throws WrongPlayerException {
        if (!whosTurn.equals(player)) {
            throw new WrongPlayerException();
        }

        if (previousPlayerPassed) {
            isGameUnderway = false;
        }

        this.whosTurn = player.otherPlayer();
        this.captures.get(this.whosTurn).incrementAndGet();
        this.previousPlayerPassed = true;
    }

    public void pass() {
        try {
            pass(this.whosTurn);
        } catch (WrongPlayerException e) {
            throw new RuntimeException("Somehow the wrong player passed when the board decided who it was");
        }
    }

    public boolean gameUnderway() {
        return isGameUnderway;
    }
}
