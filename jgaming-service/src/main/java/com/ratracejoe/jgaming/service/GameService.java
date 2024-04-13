package com.ratracejoe.jgaming.service;

import com.ratracejoe.jgaming.exception.CreatedByDuplicateException;
import com.ratracejoe.jgaming.exception.GameNotFoundException;
import com.ratracejoe.jgaming.model.Game;
import com.ratracejoe.jgaming.model.GameType;
import com.ratracejoe.jgaming.redis.GameRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameService implements IGameService {
  private final GameRepository gameRepository;

  @Override
  public Game createGame(String createdBy, GameType gameType, String description)
      throws CreatedByDuplicateException {
    var existingGame = getGameCreatedBy(createdBy);
    if (existingGame.isPresent()) {
      throw new CreatedByDuplicateException(existingGame.get().gameType(), createdBy);
    }
    Game game =
        new Game(UUID.randomUUID(), createdBy, gameType, description, Collections.emptyList());
    return gameRepository.save(game);
  }

  @Override
  public Game addPlayer(UUID id, String playerName) throws GameNotFoundException {
    Game game = this.getGame(id);
    game.players().add(playerName);
    return gameRepository.save(game);
  }

  @Override
  public Game getGame(UUID id) throws GameNotFoundException {
    return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
  }

  @Override
  public void deleteGame(UUID id) {
    gameRepository.deleteById(id);
  }

  @Override
  public List<Game> getGamesByType(GameType gameType) {
    return gameRepository.findAllWithPredicate(g -> gameType.equals(g.gameType()));
  }

  @Override
  public Optional<Game> getGameCreatedBy(String createdBy) {
    return gameRepository.findWithPredicate(g -> createdBy.equals(g.createdBy()));
  }
}
