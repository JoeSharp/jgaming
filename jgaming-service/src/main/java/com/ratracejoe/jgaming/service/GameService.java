package com.ratracejoe.jgaming.service;

import com.ratracejoe.jgaming.persistence.GameDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameService implements IGameService {
  private final GameDAO gameDAO;
}
