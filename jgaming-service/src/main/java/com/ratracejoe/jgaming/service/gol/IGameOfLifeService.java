package com.ratracejoe.jgaming.service.gol;

import com.ratracejoe.jgaming.exception.GameNotFoundException;
import com.ratracejoe.jgaming.service.IAbstractGameService;
import java.util.UUID;

public interface IGameOfLifeService extends IAbstractGameService {

  void iterate(UUID id) throws GameNotFoundException;
}
