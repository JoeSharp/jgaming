package com.ratracejoe.jgaming.service;

import com.ratracejoe.jgaming.JgamingServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JgamingServiceApplication.class)
class GameServiceTest {
  @Autowired private IGameService gameService;
}
