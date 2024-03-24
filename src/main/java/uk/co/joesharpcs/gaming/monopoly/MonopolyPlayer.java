package uk.co.joesharpcs.gaming.monopoly;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MonopolyPlayer {

  private final String name;
  private final MonopolyPiece piece;
}
