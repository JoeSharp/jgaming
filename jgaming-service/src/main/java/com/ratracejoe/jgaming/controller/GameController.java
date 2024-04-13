package com.ratracejoe.jgaming.controller;

import com.ratracejoe.jgaming.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/game")
public class GameController {
  private final GameService gameService;

  @GetMapping("/hello")
  public String getHello() {
    return "Hello";
  }
}
