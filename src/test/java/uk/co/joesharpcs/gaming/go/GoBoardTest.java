package uk.co.joesharpcs.gaming.go;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.BiConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import uk.co.joesharpcs.gaming.board.InvalidBoardStateStringException;
import uk.co.joesharpcs.gaming.go.exceptions.*;

class GoBoardTest {
  @Test
  public void defaultBoardCreated() {
    // When
    var board = new GoBoard();

    // Then
    assertEquals(19, board.getSize());
    assertEquals(Player.BLACK, board.getWhosTurn());
    assertEquals(0, board.getCaptures(Player.WHITE));
    assertEquals(0, board.getCaptures(Player.BLACK));
  }

  @Test
  public void specifiedSmallBoardCreated() throws GoException {
    // When
    final int CUSTOM_BOARD_SIZE = 9;
    var board = new GoBoard(CUSTOM_BOARD_SIZE);

    // Then
    assertEquals(CUSTOM_BOARD_SIZE, board.getSize());
    assertEquals(Player.BLACK, board.getWhosTurn());

    // Board is empty
    for (int row = 0; row < CUSTOM_BOARD_SIZE; row++) {
      for (int col = 0; col < CUSTOM_BOARD_SIZE; col++) {
        var value = board.get(row, col);
        assertEquals(PointValue.EMPTY, value);
      }
    }
  }

  @Test
  public void wrongPlayerTriesToStart() {
    // Given
    var board = new GoBoard();

    // When
    assertThrows(
        WrongPlayerException.class,
        () -> {
          board.move(Player.WHITE, 1, 2);
        });
  }

  @Test
  public void firstMovesCorrect() throws GoException {
    // Given
    var board = new GoBoard();

    // When
    board.move(Player.BLACK, 4, 5);
    var result = board.get(4, 5);

    // Then
    assertEquals(PointValue.BLACK, result);
  }

  @Test
  public void getInvalidLocation() {
    // Given
    var board = new GoBoard(4);

    // When, Then
    assertThrows(InvalidPointLocationException.class, () -> board.get(4, 1));
    assertThrows(InvalidPointLocationException.class, () -> board.get(1, 4));
  }

  @Test
  public void moveInvalidLocation() {
    // Given
    var board = new GoBoard(5);

    // When, Then
    assertThrows(InvalidPointLocationException.class, () -> board.move(Player.BLACK, 5, 1));
    assertThrows(InvalidPointLocationException.class, () -> board.move(Player.BLACK, 1, 5));
  }

  @Test
  public void secondMovesCorrect() throws GoException {
    // Given
    var board = new GoBoard();

    // When
    board.move(Player.BLACK, 4, 5);
    board.move(4, 6);
    var result = board.get(4, 6);

    // Then
    assertEquals(PointValue.WHITE, result);
  }

  @Test
  public void alreadyOccupiedThrownCorrect() throws GoException {
    // Given
    var board = new GoBoard();

    // When
    board.move(Player.BLACK, 4, 7);

    // Then
    assertThrows(AlreadyOccupiedException.class, () -> board.move(Player.WHITE, 4, 7));
  }

  @Test
  public void testBoardEqualityTrue() throws GoException {
    String boardString =
        """
                .........
                .........
                ....BBB..
                ...BW.B..
                ....WWB..
                ....BB...
                .........
                ...WWW...
                .........
                """;

    // Given
    var board1 = GoBoard.fromString(boardString);
    var board2 = GoBoard.fromString(boardString);

    // When, Then
    assertEquals(board1, board2);
    board1.move(Player.WHITE, 1, 1);
    assertNotEquals(board1, board2);
  }

  @Test
  public void testToString() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
            """
                .........
                .........
                ....BBB..
                ...BW.B..
                ....WWB..
                ....BB...
                .........
                .........
                .........
                """);

    // When
    var result = board.toStringBoard();

    // Then
    var expected =
        """
                . 0 1 2 3 4 5 6 7 8
                0 . . . . . . . . .
                1 . . . . . . . . .
                2 . . . . B B B . .
                3 . . . B W . B . .
                4 . . . . W W B . .
                5 . . . . B B . . .
                6 . . . . . . . . .
                7 . . . . . . . . .
                8 . . . . . . . . .""";
    assertEquals(expected, result);
  }

  @Test
  public void initialisedByString() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
            """
                .........
                .........
                ....BBB..
                ...BW.B..
                ....WWB..
                ....BB...
                .........
                .........
                .........
                """);

    // When
    var value24 = board.get(2, 4);
    var value34 = board.get(3, 4);
    var value53 = board.get(5, 3);

    // Then
    assertEquals(PointValue.BLACK, value24);
    assertEquals(PointValue.WHITE, value34);
    assertEquals(PointValue.EMPTY, value53);
  }

  @Test
  public void initialisedByStringWithSpaces() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . . . . . . .
                . . . . B B B . .
                . . . B W . B . .
                . . . . W W B . .
                . . . . B B . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .""");

    // When
    var value24 = board.get(2, 4);
    var value34 = board.get(3, 4);
    var value53 = board.get(5, 3);

    // Then
    assertEquals(PointValue.BLACK, value24);
    assertEquals(PointValue.WHITE, value34);
    assertEquals(PointValue.EMPTY, value53);
  }

  @Test
  public void findSingleLiberty() throws GoException {
    // Given
    BiConsumer<Integer, Integer> libertyReceiver = Mockito.mock();
    BiConsumer<Integer, Integer> groupReceiver = Mockito.mock();
    var board =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . W . . . . .
                . . W B B . . . .
                . W B W B W . . .
                . B W W B . . . .
                . . B . . . . . .
                . . . . . . . . .
                """);

    // When
    board.findLiberties(Player.WHITE, 5, 3, libertyReceiver, groupReceiver);

    // Then
    Mockito.verify(libertyReceiver).accept(7, 3);
    Mockito.verifyNoMoreInteractions(libertyReceiver);

    Mockito.verify(groupReceiver).accept(5, 3);
    Mockito.verify(groupReceiver).accept(6, 3);
    Mockito.verify(groupReceiver).accept(6, 2);
    Mockito.verifyNoMoreInteractions(groupReceiver);
  }

  @Test
  public void findMultipleLiberty() throws GoException {
    // Given
    BiConsumer<Integer, Integer> libertyReceiver = Mockito.mock();
    BiConsumer<Integer, Integer> groupReceiver = Mockito.mock();
    var board =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . W . . . . .
                . . W . B . . . .
                . W . W B W . . .
                . B W W B . . . .
                . . B . . . . . .
                . . . . . . . . .
                """);

    // When
    board.findLiberties(Player.WHITE, 5, 3, libertyReceiver, groupReceiver);

    // Then
    Mockito.verify(libertyReceiver).accept(7, 3);
    Mockito.verify(libertyReceiver).accept(5, 2);
    Mockito.verify(libertyReceiver).accept(4, 3);
    Mockito.verifyNoMoreInteractions(libertyReceiver);

    Mockito.verify(groupReceiver).accept(5, 3);
    Mockito.verify(groupReceiver).accept(6, 3);
    Mockito.verify(groupReceiver).accept(6, 2);
    Mockito.verifyNoMoreInteractions(groupReceiver);
  }

  @Test
  public void koRuleEnforcedInMiddle() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . . . . . . .
                . . . W B . . . .
                . . W . W B . . .
                . . . W B . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                """);

    // When
    board.move(Player.BLACK, 3, 3);

    // Then
    var afterFirstGo =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . . . . . . .
                . . . W B . . . .
                . . W B . B . . .
                . . . W B . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                """);
    assertEquals(afterFirstGo, board);
    assertEquals(1, board.getCaptures(Player.BLACK));

    assertThrows(KoViolationException.class, () -> board.move(Player.WHITE, 3, 4));
    assertEquals(0, board.getCaptures(Player.WHITE));
    var afterSecondAttemptedGo =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . . . . . . .
                . . . W B . . . .
                . . W B . B . . .
                . . . W B . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                """);
    assertEquals(afterSecondAttemptedGo, board);
  }

  @Test
  public void koRuleEnforcedInCorner() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
                """
                . . . .
                . . . .
                . . W B
                . W B .
                """)
            .withWhosTurn(Player.WHITE);

    // When
    board.move(Player.WHITE, 3, 3);

    // Then
    var afterFirstGo =
        GoBoard.fromString(
            """
                . . . .
                . . . .
                . . W B
                . W . W
                """);
    assertEquals(afterFirstGo, board);
    assertEquals(1, board.getCaptures(Player.WHITE));

    assertThrows(KoViolationException.class, () -> board.move(Player.BLACK, 3, 2));
    assertEquals(0, board.getCaptures(Player.BLACK));
    var afterSecondAttemptedGo =
        GoBoard.fromString(
            """
                . . . .
                . . . .
                . . W B
                . W . W
                """);
    assertEquals(afterSecondAttemptedGo, board);
  }

  @Test
  public void captureCorrectly() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . W . . . . .
                . . W B B . . . .
                . W B W B W . . .
                . B W W B . . . .
                . . B . . . . . .
                . . . . . . . . .
                """);

    // When
    board.move(Player.BLACK, 7, 3);

    // Then
    GoBoard refBoard =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . W . . . . .
                . . W B B . . . .
                . W B . B W . . .
                . B . . B . . . .
                . . B B . . . . .
                . . . . . . . . .
                """);
    assertEquals(refBoard, board);
    assertEquals(3, board.getCaptures(Player.BLACK));
    assertEquals(0, board.getCaptures(Player.WHITE));
  }

  @Test
  public void determinesTurnMidwayThroughCorrectlyWhite() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . W . . . . .
                . . W B B W . . .
                . W B W B W . . .
                . B W . B . . . .
                . . B B . . . . .
                . . . . . . . . .
                """);

    assertEquals(Player.WHITE, board.getWhosTurn());
  }

  @Test
  public void determinesTurnMidwayThroughCorrectlyBlack() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . B . . . . .
                . . . . . . . . .
                . . B . W . . . .
                . . . . . . . . .
                . . B W . . . . .
                . . . . . . . . .
                . . . W . . . . .
                . . . . . . . . .
                """);

    assertEquals(Player.BLACK, board.getWhosTurn());
  }

  @Test
  public void preventSuicidalMoveCorrectlyInMiddle() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
            """
                . . . . . . . . .
                . . . . . . . . .
                . . . . . . . . .
                . . . W . . . . .
                . . W B B W . . .
                . W B W B W . . .
                . B W . B . . . .
                . . B B . . . . .
                . . . . . . . . .
                """);

    assertThrows(SuicidalMoveException.class, () -> board.move(Player.WHITE, 6, 3));
  }

  @Test
  public void preventSuicidalMoveCorrectlyInCorner() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
            """
                . . . .
                . . . .
                B B . .
                . B . .
                """);

    // When, Then
    assertThrows(SuicidalMoveException.class, () -> board.move(Player.WHITE, 3, 0));
  }

  @Test
  public void passingEndsGame() throws GoException {
    // Given
    var board =
        GoBoard.fromString(
                """
                . . . .
                . . . .
                B B . .
                . B . .
                """)
            .withWhosTurn(Player.WHITE)
            .withPreexistingCaptures(Player.WHITE, 3)
            .withPreexistingCaptures(Player.BLACK, 4);
    assertTrue(board.gameUnderway());

    // When
    board.pass(Player.WHITE);
    board.pass(); // will use overloaded that uses whosTurn

    // Then
    assertEquals(4, board.getCaptures(Player.WHITE));
    assertEquals(5, board.getCaptures(Player.BLACK));
    assertFalse(board.gameUnderway());
  }

  @Test
  public void invalidPointValueDetected() {
    String input =
        """
                .........
                .........
                ....BBB..
                ...BX.B..
                ....WWB..
                ....BB...
                .........
                .........
                .........
                """;

    assertThrows(InvalidPointValueException.class, () -> GoBoard.fromString(input));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        """
                .........
                ....BBB..
                ...BW.B..
                ....WWB..
                ....BB...
                .........
                .........
                .........
                """,
        """
                ........
                ........
                ....BBB.
                ...BW.B.
                ....WWB.
                ....BB..
                ........
                ........
                ........
                """
      })
  public void notASquareDetected(String value) {
    // Given
    assertThrows(InvalidBoardStateStringException.class, () -> GoBoard.fromString(value));
  }
}
