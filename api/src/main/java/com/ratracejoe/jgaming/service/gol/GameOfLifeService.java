package com.ratracejoe.jgaming.service.gol;

import com.ratracejoe.jgaming.exception.GameNotFoundException;
import com.ratracejoe.jgaming.exception.InvalidGameParameters;
import com.ratracejoe.jgaming.model.IdentifiedGameOfLife;
import com.ratracejoe.jgaming.model.StoredGameOfLife;
import com.ratracejoe.jgaming.redis.GameOfLifeRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.joesharpcs.gaming.board.GridBoard;
import uk.co.joesharpcs.gaming.gol.GameOfLife;
import uk.co.joesharpcs.gaming.gol.GolPatterns;

@RequiredArgsConstructor
@Service
public class GameOfLifeService implements IGameOfLifeService {

  private final GameOfLifeRepository repository;

  @Override
  public IdentifiedGameOfLife create() {
    return createWithBoard(GolPatterns.BEACON);
  }

  @Override
  public IdentifiedGameOfLife create(List<List<Boolean>> grid) {
    UUID id = UUID.randomUUID();
    GameOfLife newGame = new GameOfLife(new GridBoard<>(grid));
    StoredGameOfLife stored = new StoredGameOfLife(id, newGame.toString());
    stored = repository.save(stored);
    return GameOfLifeRepository.read(stored);
  }

  @Override
  public IdentifiedGameOfLife create(String namedPattern) throws InvalidGameParameters {
    String board = GolPatterns.NAMED_PATTERNS.get(namedPattern);
    if (Objects.isNull(board)) {
      throw new InvalidGameParameters(
          String.format("Pattern named %s does not exist", namedPattern));
    }

    return createWithBoard(board);
  }

  private IdentifiedGameOfLife createWithBoard(String board) {
    UUID id = UUID.randomUUID();
    GameOfLife newGame = GameOfLife.fromString(board);
    StoredGameOfLife stored = new StoredGameOfLife(id, newGame.toString());
    stored = repository.save(stored);
    return GameOfLifeRepository.read(stored);
  }

  @Override
  public IdentifiedGameOfLife get(UUID id) throws GameNotFoundException {
    return repository
        .findById(id)
        .map(GameOfLifeRepository::read)
        .orElseThrow(() -> new GameNotFoundException(id));
  }

  @Override
  public IdentifiedGameOfLife iterate(UUID id) throws GameNotFoundException {
    IdentifiedGameOfLife stored = this.get(id);
    stored.game().iterate();
    repository.save(GameOfLifeRepository.write(stored));
    return stored;
  }

  @Override
  public void destroy(UUID id) {
    repository.deleteById(id);
  }
}
