package uk.co.joesharpcs.gaming.monopoly;

public record Property(Colour colour, String name, int value) {

  public enum Colour {
    BROWN,
    LIGHT_BLUE,
    PINK,
    ORANGE,
    RED,
    YELLOW,
    GREEN,
    NAVY_BLUE
  }
}
