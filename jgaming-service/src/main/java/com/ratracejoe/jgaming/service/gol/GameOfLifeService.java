package com.ratracejoe.jgaming.service.gol;

import com.ratracejoe.jgaming.exception.GameNotFoundException;
import com.ratracejoe.jgaming.model.GameType;
import com.ratracejoe.jgaming.model.StoredGameOfLife;
import com.ratracejoe.jgaming.redis.GameOfLifeRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.joesharpcs.gaming.gol.GameOfLife;

@RequiredArgsConstructor
@Service
public class GameOfLifeService implements IGameOfLifeService {
  private final GameOfLifeRepository repository;

  @Override
  public GameType getGameType() {
    return GameType.GAME_OF_LIFE;
  }

  @Override
  public void create(UUID id) {
    GameOfLife newGame = new GameOfLife();
    StoredGameOfLife stored = new StoredGameOfLife(id, newGame);
    repository.save(stored);
  }

  @Override
  public void iterate(UUID id) throws GameNotFoundException {
    StoredGameOfLife stored = repository.findById(id)
            .orElseThrow(() -> new GameNotFoundException(id));
    stored.game().iterate();
    repository.save(stored);
  }

  @Override
  public void destroy(UUID id) {
    repository.deleteById(id);
  }
}
