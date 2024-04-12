package uk.co.joesharpcs.gaming.monopoly;

import java.util.ArrayList;
import java.util.List;
import uk.co.joesharpcs.gaming.chance.Dice;
import uk.co.joesharpcs.gaming.utils.CircularQueueIterator;
import uk.co.joesharpcs.gaming.utils.GameRulesException;

public class Game {
  private final Board board;
  private final Dice die;
  private final List<Player> players;
  private final CircularQueueIterator<Player> playerTurns;
  private GameState state;

  public Game() {
    this.state = GameState.INITIALISING;
    this.players = new ArrayList<>();
    this.board = new Board();
    this.die = Dice.sixSided();
    this.playerTurns = new CircularQueueIterator<>(players);
  }

  public Game withPlayer(Player player) throws GameRulesException {
    checkState(GameState.INITIALISING);
    if (this.players.stream().map(Player::getName).anyMatch(p -> p.equals(player.getName()))) {
      throw new GameRulesException(
          String.format("All player names must be unique, %s already exists", player.getName()));
    }
    this.players.add(player);
    return this;
  }

  public Game readyToPlay() throws GameRulesException {
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
