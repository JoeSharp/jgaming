package com.ratracejoe.jgaming.persistence;

import com.ratracejoe.jgaming.model.Game;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameDAO implements IGameDAO {
  @Override
  public Game createGame(String createdBy, String gameType, String description) {
    return null;
  }

  @Override
  public Game addPlayer(UUID id, String playerName) {
    return null;
  }

  @Override
  public Game getGame(UUID id) {
    return null;
  }

  @Override
  public void deleteGame(UUID id) {}

  @Override
  public List<Game> getGamesByType(String gameType) {
    return null;
  }

  @Override
  public Game getGameCreatedBy(String createdBy) {
    return null;
  }
}
