package uk.co.joesharpcs.gaming.monopoly.spaces;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PropertySpace implements MonopolyBoardSpace {
  private final Colour colour;
  private final String name;
  private final int value;
}
