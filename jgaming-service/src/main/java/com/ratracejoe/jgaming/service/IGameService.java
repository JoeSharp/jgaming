package com.ratracejoe.jgaming.service;

import com.ratracejoe.jgaming.exception.CreatedByDuplicateException;
import com.ratracejoe.jgaming.exception.GameNotFoundException;
import com.ratracejoe.jgaming.model.Game;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IGameService {

  Game createGame(String createdBy, String gameType, String description)
      throws CreatedByDuplicateException;

  Game addPlayer(UUID id, String playerName) throws GameNotFoundException;

  Game getGame(UUID id) throws GameNotFoundException;

  void deleteGame(UUID id);

  List<Game> getGamesByType(String gameType);

  Optional<Game> getGameCreatedBy(String createdBy);
}
