package com.ratracejoe.jgaming.redis;

import com.ratracejoe.jgaming.model.Game;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, UUID> {

  default List<Game> findAllWithPredicate(Predicate<Game> predicate) {
    return stream().filter(predicate).toList();
  }

  default Optional<Game> findWithPredicate(Predicate<Game> predicate) {
    return stream().filter(predicate).findFirst();
  }

  default Stream<Game> stream() {
    return StreamSupport.stream(this.findAll().spliterator(), false);
  }
}
