package uk.co.joesharpcs.gaming.monopoly;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Player {

  public enum PlayerPiece {
    PENGUIN,
    DINOSAUR,
    CAR,
    DOG,
    CAT,
    BIRD
  }

  private final String name;
  private final PlayerPiece piece;
}
