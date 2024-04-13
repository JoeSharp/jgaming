package com.ratracejoe.jgaming.controller;

import com.ratracejoe.jgaming.dto.NewGameDTO;
import com.ratracejoe.jgaming.exception.CreatedByDuplicateException;
import com.ratracejoe.jgaming.exception.GameNotFoundException;
import com.ratracejoe.jgaming.model.Game;
import com.ratracejoe.jgaming.service.GameService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {
  private final GameService gameService;

  @GetMapping("/hello")
  public String getHello() {
    return "Hello";
  }

  @PostMapping(
      value = "/start",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public String startGame(@RequestBody NewGameDTO newGame) throws CreatedByDuplicateException {
    var game =
        gameService.createGame(newGame.createdBy(), newGame.gameType(), newGame.description());
    return game.id().toString();
  }

  @PostMapping(value = "/addPlayer/{id}", consumes = MediaType.TEXT_PLAIN_VALUE)
  public Game addPlayer(@PathVariable("id") UUID id, @RequestBody String playerName)
      throws GameNotFoundException {
    return gameService.addPlayer(id, playerName);
  }

  @GetMapping("/createdBy/{createdBy}")
  public ResponseEntity<Game> getGameCreatedBy(@PathVariable("createdBy") String createdBy) {
    return gameService
        .getGameCreatedBy(createdBy)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.noContent().build());
  }

  @GetMapping("/{id}")
  public Game getGame(@PathVariable("id") UUID id) throws GameNotFoundException {
    return gameService.getGame(id);
  }

  @DeleteMapping("/{id}")
  public void endGame(@PathVariable("id") UUID id) {
    gameService.deleteGame(id);
  }
}
