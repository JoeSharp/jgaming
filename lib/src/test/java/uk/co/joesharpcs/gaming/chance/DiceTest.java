package uk.co.joesharpcs.gaming.chance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiceTest {
  public static Stream<Arguments> getDice() {
    return Stream.of(Arguments.of(Dice.coin()), Arguments.of(Dice.sixSided()));
  }

  @ParameterizedTest
  @MethodSource("getDice")
  void testDie(Dice dice) {
    // Given
    final int NUMBER_ROLLS = 1000;
    final Map<Integer, Integer> count = new HashMap<>();
    for (int i = 1; i <= dice.getNumberOfSides(); i++) {
      count.put(i, 0);
    }

    // When
    for (int i = 0; i < NUMBER_ROLLS; i++) {
      var result = dice.roll();
      count.computeIfPresent(result, (key, value) -> value + 1);
    }

    // Then
    for (int i = 1; i <= dice.getNumberOfSides(); i++) {
      assertThat(String.format("Rolls of %d should be non zero", i), count.get(i), greaterThan(0));
    }
  }
}
