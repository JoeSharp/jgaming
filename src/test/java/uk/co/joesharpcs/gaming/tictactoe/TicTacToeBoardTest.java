package uk.co.joesharpcs.gaming.tictactoe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import uk.co.joesharpcs.gaming.tictactoe.exceptions.AlreadyWonException;
import uk.co.joesharpcs.gaming.tictactoe.exceptions.TicTacToeException;

class TicTacToeBoardTest {

  @Test
  public void winCorrectlyCrossMiddleCol() throws TicTacToeException {
    // Given
    var board = new TicTacToeBoard();

    // When
    board.move(1, 1); // X
    board.move(1, 2); // O
    board.move(0, 1); // X
    board.move(2, 2); // O
    board.move(2, 1); // X
    TicTacToePlayer result = board.getWinner();

    // Then
    assertEquals(TicTacToePlayer.CROSSES, result);
  }

  @Test
  public void winCorrectlyNoughtsTopRow() throws TicTacToeException {
    // Given
    var board = new TicTacToeBoard();

    // When
    board.move(1, 1); // X
    board.move(0, 0); // O
    board.move(2, 2); // X
    board.move(0, 1); // O
    board.move(2, 0); // X
    board.move(0, 2); // O
    TicTacToePlayer result = board.getWinner();

    // Then
    assertEquals(TicTacToePlayer.NOUGHTS, result);
  }

  @Test
  public void winCorrectlyNoughtsNegativeDiag() throws TicTacToeException {
    // Given
    var board = new TicTacToeBoard();

    // When
    board.move(0, 1); // X
    board.move(0, 0); // O
    board.move(2, 1); // X
    board.move(1, 1); // O
    board.move(2, 0); // X
    board.move(2, 2); // O
    TicTacToePlayer result = board.getWinner();

    // Then
    assertEquals(TicTacToePlayer.NOUGHTS, result);
  }

  @Test
  public void winCorrectlyCrossesPositiveDiag() throws TicTacToeException {
    // Given
    var board = new TicTacToeBoard();

    // When
    board.move(2, 0); // X
    board.move(2, 1); // O
    board.move(1, 1); // X
    board.move(1, 2); // O
    board.move(0, 2); // X
    TicTacToePlayer result = board.getWinner();

    // Then
    assertEquals(TicTacToePlayer.CROSSES, result);
  }

  @Test
  public void alreadyWonDetected() throws TicTacToeException {
    // Given
    var board = new TicTacToeBoard();

    // When
    board.move(1, 1); // X
    board.move(1, 2); // O
    board.move(0, 1); // X
    board.move(2, 2); // O
    board.move(2, 1); // X

    // Then
    assertThrows(AlreadyWonException.class, () -> board.move(0, 0));
  }
}
