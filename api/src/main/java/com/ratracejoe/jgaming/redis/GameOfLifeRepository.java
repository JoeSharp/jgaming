package com.ratracejoe.jgaming.redis;

import com.ratracejoe.jgaming.model.IdentifiedGameOfLife;
import com.ratracejoe.jgaming.model.StoredGameOfLife;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.joesharpcs.gaming.gol.GameOfLife;

@Repository
public interface GameOfLifeRepository extends CrudRepository<StoredGameOfLife, UUID> {
  static StoredGameOfLife write(IdentifiedGameOfLife identified) {
    return new StoredGameOfLife(identified.id(), identified.game().toString());
  }

  static IdentifiedGameOfLife read(StoredGameOfLife stored) {
    return new IdentifiedGameOfLife(stored.id(), GameOfLife.fromString(stored.board()));
  }
}
