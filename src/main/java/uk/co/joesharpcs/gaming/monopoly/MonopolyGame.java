package uk.co.joesharpcs.gaming.monopoly;

import java.util.ArrayList;
import java.util.List;
import uk.co.joesharpcs.gaming.chance.Dice;
import uk.co.joesharpcs.gaming.utils.GameRulesException;

public class MonopolyGame {
  private final MonopolyBoard board;
  private final Dice die;
  private final List<MonopolyPlayer> players;
  private final CircularQueueIterator<MonopolyPlayer> playerTurns;
  private GameState state;

  public MonopolyGame() {
    this.state = GameState.INITIALISING;
    this.players = new ArrayList<>();
    this.board = new MonopolyBoard();
    this.die = Dice.sixSided();
    this.playerTurns = new CircularQueueIterator<>(players);
  }

  public MonopolyGame withPlayer(MonopolyPlayer player) throws GameRulesException {
    checkState(GameState.INITIALISING);
    this.players.add(player);
    return this;
  }

  public MonopolyGame readyToPlay() throws GameRulesException {
    checkState(GameState.INITIALISING);
    this.state = GameState.PLAYING;
    return this;
  }

  private void checkState(GameState shouldBe) throws GameRulesException {
    if (!shouldBe.equals(this.state)) {
      throw new GameRulesException("Cannot add players after game started");
    }
  }
}
