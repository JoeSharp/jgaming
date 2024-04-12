package uk.co.joesharpcs.gaming.go;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import uk.co.joesharpcs.gaming.board.BoardLocation;
import uk.co.joesharpcs.gaming.board.GridBoard;
import uk.co.joesharpcs.gaming.board.InvalidBoardStateStringException;
import uk.co.joesharpcs.gaming.board.ShapeConstraint;
import uk.co.joesharpcs.gaming.go.exceptions.*;

public class GoBoard {

  private static final int DEFAULT_SIZE = 19;

  private final int size;

  private Player whosTurn;

  private final GridBoard<PointValue> board;

  private final Map<Player, AtomicInteger> captures;

  private final Queue<Integer> lastTwoBoardHashes;

  private boolean isGameUnderway;

  private boolean previousPlayerPassed;

  GoBoard() {
    this(DEFAULT_SIZE);
  }

  GoBoard(int size, GridBoard<PointValue> board) {
    this.size = size;
    this.whosTurn = Player.BLACK;
    this.board = board;
    this.captures =
        Arrays.stream(Player.values()).collect(Collectors.toMap(p -> p, p -> new AtomicInteger(0)));
    this.lastTwoBoardHashes = new ArrayDeque<>();
    this.lastTwoBoardHashes.add(this.board.hashCode());
    this.isGameUnderway = true;
    this.previousPlayerPassed = false;
  }

  GoBoard(int size) {
    this(size, new GridBoard<>(size, PointValue.EMPTY));
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

  public void move(int row, int col) throws GoException {
    move(whosTurn, row, col);
  }

  public void move(BoardLocation location) throws GoException {
    move(whosTurn, location.row(), location.col());
  }

  public void move(Player player, int row, int col) throws GoException {
    this.board.save();

    if (!this.whosTurn.equals(player)) {
      throw new WrongPlayerException();
    }

    checkPointIsOnBoard(row, col);

    if (!PointValue.EMPTY.equals(this.board.get(row, col))) {
      throw new AlreadyOccupiedException();
    }

    this.previousPlayerPassed = false;
    this.board.set(row, col, player.getPointValue());

    boolean capturesMade = checkCaptures(player, row, col);

    // Check for suicidal move
    if (!capturesMade) {
      try {
        checkSuicidalMove(player, row, col);
      } catch (SuicidalMoveException e) {
        board.restore();
        throw e;
      }
    }

    // Check for Ko
    if (capturesMade) {
      try {
        checkKo();
      } catch (KoViolationException e) {
        this.captures.get(player).decrementAndGet();
        board.restore();
        throw e;
      }
    }

    this.whosTurn = this.whosTurn.otherPlayer();
  }

  private void checkSuicidalMove(Player player, int row, int col) throws SuicidalMoveException {
    AtomicBoolean libertiesFound = new AtomicBoolean(false);
    Set<String> dontCheckHereAgain = new HashSet<>();
    dontCheckHereAgain.add(getLocationKey(row, col));
    findLiberties(
        dontCheckHereAgain, player, row, col, (r, c) -> libertiesFound.set(true), (r, c) -> {});
    if (!libertiesFound.get()) {
      throw new SuicidalMoveException();
    }
  }

  private void checkKo() throws KoViolationException {
    int boardHash = board.hashCode();

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
    BoardLocation.getAdjacent(
        getSize(),
        row,
        col,
        (r, c) ->
            calculateCaptures(player, r, c)
                .forEach(
                    loc -> {
                      capturesMade.set(true);
                      playerCaptureCount.incrementAndGet();
                      board.set(loc.row(), loc.col(), PointValue.EMPTY);
                    }));

    return capturesMade.get();
  }

  private Set<BoardLocation> calculateCaptures(Player player, int row, int col) {
    // Are we also occupying this square?
    if (player.getPointValue().equals(board.get(row, col))) {
      return Collections.emptySet();
    }

    // Is the square empty?
    if (PointValue.EMPTY.equals(board.get(row, col))) {
      return Collections.emptySet();
    }

    // It contains the other player, does the other player have any liberties from this spot?
    AtomicBoolean libertyFound = new AtomicBoolean(false);
    Set<BoardLocation> group = new HashSet<>();
    group.add(new BoardLocation(row, col));

    findLiberties(
        player.otherPlayer(),
        row,
        col,
        (r, c) -> libertyFound.set(true),
        (r, c) -> group.add(new BoardLocation(r, c)));

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

  public void findLiberties(
      Player asPlayer,
      int row,
      int col,
      BiConsumer<Integer, Integer> libertyReceiver,
      BiConsumer<Integer, Integer> groupReceiver) {
    findLiberties(new HashSet<>(), asPlayer, row, col, libertyReceiver, groupReceiver);
  }

  private static String getLocationKey(int row, int col) {
    return String.format("%d-%d", row, col);
  }

  private void findLiberties(
      Set<String> alreadyAssessed,
      Player asPlayer,
      int row,
      int col,
      BiConsumer<Integer, Integer> libertyReceiver,
      BiConsumer<Integer, Integer> groupReceiver) {
    BoardLocation.getAdjacent(
        getSize(),
        row,
        col,
        (r, c) -> {
          // Skip it if we've already assessed it
          String key = getLocationKey(r, c);
          PointValue value = board.get(r, c);
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
    if (row < 0 || row >= this.getSize()) throw new InvalidPointLocationException();
    if (col < 0 || col >= this.getSize()) throw new InvalidPointLocationException();
  }

  @Override
  public String toString() {
    return "GoBoard{"
        + "\n\twhosTurn="
        + whosTurn
        + "\n\tcaptures="
        + captures
        + "\n\tlastTwoBoardHashes="
        + lastTwoBoardHashes
        + "\n\tisGameUnderway="
        + isGameUnderway
        + "\n\tpreviousPlayerPassed="
        + previousPlayerPassed
        + "\nboard=\n"
        + board
        + "}\n";
  }

  public String toStringBoard() {
    return board.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GoBoard goBoard = (GoBoard) o;
    return Objects.equals(board, goBoard.board);
  }

  @Override
  public int hashCode() {
    return board.hashCode();
  }

  public static GoBoard fromString(String asString) throws InvalidBoardStateStringException {
    GridBoard<PointValue> rawBoard =
        GridBoard.fromString(asString, PointValue::fromChar, ShapeConstraint.SQUARE);

    final GoBoard board = new GoBoard(rawBoard.getNumberRows(), rawBoard);

    var pointByValue = rawBoard.getValueCounts();
    var black = pointByValue.computeIfAbsent(PointValue.BLACK, (_v) -> new AtomicInteger(0)).get();
    var white = pointByValue.computeIfAbsent(PointValue.WHITE, (_v) -> new AtomicInteger(0)).get();

    if (black > white) {
      board.whosTurn = Player.WHITE;
    } else {
      board.whosTurn = Player.BLACK;
    }
    board.lastTwoBoardHashes.clear();
    board.lastTwoBoardHashes.add(board.hashCode());

    return board;
  }

  public PointValue get(int row, int col) throws InvalidPointLocationException {
    this.checkPointIsOnBoard(row, col);
    return this.board.get(row, col);
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
      throw new RuntimeException(
          "Somehow the wrong player passed when the board decided who it was");
    }
  }

  public boolean gameUnderway() {
    return isGameUnderway;
  }
}
