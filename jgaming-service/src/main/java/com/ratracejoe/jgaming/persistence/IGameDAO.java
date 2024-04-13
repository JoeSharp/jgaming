package com.ratracejoe.jgaming.persistence;

import com.ratracejoe.jgaming.model.Game;
import java.util.List;
import java.util.UUID;

public interface IGameDAO {
  Game createGame(String creatorName, String gameType, String description);

  Game addPlayer(UUID id, String playerName);

  Game getGame(UUID id);

  void deleteGame(UUID id);

  List<Game> getGamesByType(String gameType);

  Game getGameCreatedBy(String createdBy);
}
