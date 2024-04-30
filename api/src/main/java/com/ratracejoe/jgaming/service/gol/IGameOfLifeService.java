package com.ratracejoe.jgaming.service.gol;

import com.ratracejoe.jgaming.exception.GameNotFoundException;
import com.ratracejoe.jgaming.exception.InvalidGameParameters;
import com.ratracejoe.jgaming.model.IdentifiedGameOfLife;
import java.util.List;
import java.util.UUID;

public interface IGameOfLifeService {

  IdentifiedGameOfLife create();

  IdentifiedGameOfLife create(List<List<Boolean>> grid);

  IdentifiedGameOfLife create(String namedPattern) throws InvalidGameParameters;

  IdentifiedGameOfLife get(UUID id) throws GameNotFoundException;

  IdentifiedGameOfLife iterate(UUID id) throws GameNotFoundException;

  void destroy(UUID id);
}
