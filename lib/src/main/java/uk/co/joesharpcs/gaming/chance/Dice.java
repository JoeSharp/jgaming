package uk.co.joesharpcs.gaming.chance;

import java.util.Random;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Yes I know the singular for Dice is Die I thought it would be odd having a class called Die that
 * didn't kill anything.
 */
@RequiredArgsConstructor
public class Dice {
  @Getter private final int numberOfSides;
  private final Random random = new Random();

  public int roll() {
    return random.nextInt(1, numberOfSides + 1);
  }

  public static Dice sixSided() {
    return new Dice(6);
  }

  public static Dice coin() {
    return new Dice(2);
  }
}
