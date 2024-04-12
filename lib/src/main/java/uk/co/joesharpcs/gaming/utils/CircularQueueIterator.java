package uk.co.joesharpcs.gaming.utils;

import java.util.List;
import java.util.function.Predicate;

public class CircularQueueIterator<T> {

  private final List<T> items;
  private int index;

  public CircularQueueIterator(final List<T> items) {
    this.items = items;
    this.index = 0;
  }

  public boolean seekMatch(Predicate<T> matcher) {
    for (int i = 0; i < items.size(); i++) {
      if (matcher.test(items.get(i))) {
        index = i;
        return true;
      }
    }

    return false;
  }

  public T next() {
    var result = peek();
    this.index++;
    this.index %= this.items.size();
    return result;
  }

  public T peek() {
    if (this.items.isEmpty()) return null;
    return this.items.get(this.index);
  }
}
