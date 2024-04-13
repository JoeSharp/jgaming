package com.ratracejoe.jgaming.redis;

import com.ratracejoe.jgaming.model.StoredGameOfLife;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameOfLifeRepository extends CrudRepository<StoredGameOfLife, UUID> {}
