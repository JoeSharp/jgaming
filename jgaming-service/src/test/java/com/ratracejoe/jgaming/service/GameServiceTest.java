package com.ratracejoe.jgaming.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ratracejoe.jgaming.AbstractTest;
import com.ratracejoe.jgaming.exception.CreatedByDuplicateException;
import com.ratracejoe.jgaming.exception.GameNotFoundException;
import com.ratracejoe.jgaming.model.Game;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class GameServiceTest extends AbstractTest {
  @Autowired private IGameService gameService;

  @Test
  void createAndGetGame() throws GameNotFoundException, CreatedByDuplicateException {
    // Given
    String createdBy = randomValue("user");
    String gameType = "chess";
    String userDescription = "created by bots";

    // When
    Game created = gameService.createGame(createdBy, gameType, userDescription);
    Game found = gameService.getGame(created.id());

    // Then
    assertThat(created.id()).isNotNull();
    assertThat(found.id()).isEqualTo(created.id());
    assertThat(found.gameType()).isEqualTo(gameType);
    assertThat(found.userDescription()).isEqualTo(userDescription);
  }

  @Test
  void deleteGame() throws CreatedByDuplicateException {
    // Given
    String createdBy = randomValue("user");
    String gameType = "chess";
    String userDescription = "created by bots";
    Game created = gameService.createGame(createdBy, gameType, userDescription);

    // When
    gameService.deleteGame(created.id());
    List<Game> list = gameService.getGamesByType(gameType);

    // Then
    assertThatThrownBy(() -> gameService.getGame(created.id()))
        .isInstanceOf(GameNotFoundException.class)
        .hasMessage(String.format("Game with ID %s not found", created.id()));
    Condition<Game> deletedGameId =
        new Condition<>(d -> d.id().equals(created.id()), "Deleted game found");
    assertThat(list).doNotHave(deletedGameId);
  }

  @Test
  void onlyOneGamePerCreator() throws CreatedByDuplicateException {
    // Given
    String createdBy = randomValue("user");
    String gameType1 = "chess";
    String userDescription1 = "first game";
    String gameType2 = "go";
    String userDescription2 = "second game should fail";
    gameService.createGame(createdBy, gameType1, userDescription1);

    // When, Then
    assertThatThrownBy(() -> gameService.createGame(createdBy, gameType2, userDescription2))
        .isInstanceOf(CreatedByDuplicateException.class)
        .hasMessage("A game of type chess has already been created by " + createdBy);
  }

  private static String randomValue(String prefix) {
    return String.format("%s-%s", prefix, UUID.randomUUID());
  }
}
