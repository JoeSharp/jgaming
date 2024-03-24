package uk.co.joesharpcs.gaming.tictactoe;

import java.util.Objects;
import uk.co.joesharpcs.gaming.tictactoe.exceptions.AlreadyOccupiedException;
import uk.co.joesharpcs.gaming.tictactoe.exceptions.AlreadyWonException;
import uk.co.joesharpcs.gaming.tictactoe.exceptions.TicTacToeException;

public class TicTacToeBoard {
  public static final TicTacToePlayer DEFAULT_STARTER = TicTacToePlayer.CROSSES;

  private static final int DIMENSION = 3;

  private final TicTacToePointValue[][] board = new TicTacToePointValue[DIMENSION][DIMENSION];

  private TicTacToePlayer whosTurn;

  private TicTacToePlayer winner;

  public TicTacToeBoard() {
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        board[i][j] = TicTacToePointValue.EMPTY;
      }
    }
    this.whosTurn = DEFAULT_STARTER;
  }

  public void move(int i, int j) throws TicTacToeException {
    if (!Objects.isNull(winner)) {
      throw new AlreadyWonException();
    }

    if (!TicTacToePointValue.EMPTY.equals(this.board[i][j])) {
      throw new AlreadyOccupiedException();
    }

    this.board[i][j] = this.whosTurn.getPointValue();

    this.whosTurn = this.whosTurn.otherPlayer();

    determineWinner();
  }

  public TicTacToePlayer getWinner() {
    return winner;
  }

  private void determineWinner() {
    for (int i = 0; i < DIMENSION; i++) {
      var rowResult = isWinningSet(board[i][0], board[i][1], board[i][2]);
      if (!Objects.isNull(rowResult)) {
        winner = rowResult;
        return;
      }

      var colResult = isWinningSet(board[0][i], board[1][i], board[2][i]);
      if (!Objects.isNull(colResult)) {
        winner = colResult;
        return;
      }
    }

    var negativeDiag = isWinningSet(board[0][0], board[1][1], board[2][2]);
    if (!Objects.isNull(negativeDiag)) {
      winner = negativeDiag;
      return;
    }

    var positiveDiag = isWinningSet(board[2][0], board[1][1], board[0][2]);
    if (!Objects.isNull(positiveDiag)) {
      winner = positiveDiag;
    }
  }

  private TicTacToePlayer isWinningSet(
      TicTacToePointValue p1, TicTacToePointValue p2, TicTacToePointValue p3) {
    if (!p1.equals(TicTacToePointValue.EMPTY) && p1.equals(p2) && p1.equals(p3)) {
      return switch (p1) {
        case NOUGHT -> TicTacToePlayer.NOUGHTS;
        case CROSS -> TicTacToePlayer.CROSSES;
        default -> null;
      };
    }
    return null;
  }
}
