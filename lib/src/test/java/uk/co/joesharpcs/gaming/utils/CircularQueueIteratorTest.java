package uk.co.joesharpcs.gaming.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;
import org.junit.jupiter.api.Test;

class CircularQueueIteratorTest {

  @Test
  void nextWorksCorrectly() {
    // Given
    List<String> items = List.of("Joe", "Jenny", "Tom");
    CircularQueueIterator<String> cQueue = new CircularQueueIterator<>(items);

    // When
    var first = cQueue.next();
    var second = cQueue.next();
    var third = cQueue.next();
    var fourth = cQueue.next();

    // Then
    assertThat(first, is("Joe"));
    assertThat(second, is("Jenny"));
    assertThat(third, is("Tom"));
    assertThat(fourth, is("Joe"));
  }

  @Test
  void peekWorksCorrectly() {
    // Given
    List<String> items = List.of("Joe", "Jenny", "Tom");
    CircularQueueIterator<String> cQueue = new CircularQueueIterator<>(items);

    // When
    var firstOnce = cQueue.peek();
    var firstTwice = cQueue.peek();
    var firstIterate = cQueue.next();
    var second = cQueue.next();

    // Then
    assertThat(firstOnce, is("Joe"));
    assertThat(firstTwice, is("Joe"));
    assertThat(firstIterate, is("Joe"));
    assertThat(second, is("Jenny"));
  }

  @Test
  void seekFindsMatch() {
    // Given
    List<String> items = List.of("Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet");
    CircularQueueIterator<String> cQueue = new CircularQueueIterator<>(items);

    // When
    var found = cQueue.seekMatch("Blue"::equals);
    var blue = cQueue.next();
    var green = cQueue.next();

    // Then
    assertThat(found, is(true));
    assertThat(blue, is("Blue"));
    assertThat(green, is("Indigo"));
  }

  @Test
  void seekIgnoresNoMatch() {
    // Given
    List<String> items = List.of("Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet");
    CircularQueueIterator<String> cQueue = new CircularQueueIterator<>(items);

    // When
    var found = cQueue.seekMatch("foo"::equals);
    var red = cQueue.next();
    var orange = cQueue.next();

    // Then
    assertThat(found, is(false));
    assertThat(red, is("Red"));
    assertThat(orange, is("Orange"));
  }
}
