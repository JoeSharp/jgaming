package com.ratracejoe.jgaming.service;

import com.ratracejoe.jgaming.model.GameType;
import java.util.UUID;

public interface IAbstractGameService {
  GameType getGameType();

  void create(UUID id);

  void destroy(UUID id);
}
