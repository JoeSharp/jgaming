package com.ratracejoe.jgaming.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ratracejoe.jgaming.JgamingServiceApplication;
import com.ratracejoe.jgaming.exception.CreatedByDuplicateException;
import com.ratracejoe.jgaming.model.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JgamingServiceApplication.class)
class GameDAOTest {
  @Autowired private IGameDAO gameDAO;

  @Test
  void createAndGetGame() {
    // Given
    String creator = "testUser";
    String gameType = "chess";
    String userDescription = "created by bots";

    // When
    Game created = gameDAO.createGame(creator, gameType, userDescription);
    Game found = gameDAO.getGame(created.id());

    // Then
    assertThat(created.id()).isNotNull();
    assertThat(found.id()).isEqualTo(created.id());
    assertThat(found.gameType()).isEqualTo(gameType);
    assertThat(found.userDescription()).isEqualTo(userDescription);
  }

  @Test
  void deleteGame() {
    // Given
    String creator = "testUser";
    String gameType = "chess";
    String userDescription = "created by bots";
    Game created = gameDAO.createGame(creator, gameType, userDescription);

    // When
    gameDAO.deleteGame(created.id());
    var found = gameDAO.getGame(created.id());
    var list = gameDAO.getGamesByType(gameType);

    // Then
    assertThat(found).isNull();
    assertThat(list).isEmpty();
  }

  @Test
  void onlyOneGamePerCreator() {
    // Given
    String creator = "testUser";
    String gameType1 = "chess";
    String userDescription1 = "first game";
    String gameType2 = "go";
    String userDescription2 = "second game should fail";
    gameDAO.createGame(creator, gameType1, userDescription1);

    // When, Then
    assertThatThrownBy(() -> gameDAO.createGame(creator, gameType2, userDescription2))
        .isInstanceOf(CreatedByDuplicateException.class)
        .hasMessage("A game of type chess has already been created by testUser");
  }
}
