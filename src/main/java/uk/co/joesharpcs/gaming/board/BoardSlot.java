package uk.co.joesharpcs.gaming.board;

import java.util.Objects;
import java.util.Stack;

public class BoardSlot<T> {
    private Stack<T> history;
    private T currentValue;

    public BoardSlot(T currentValue) {
        this.currentValue = currentValue;
        this.history = new Stack<>();
    }

    public void save() {
        history.push(this.currentValue);
    }

    public void restore() {
        this.currentValue = history.pop();
    }

    public T get() {
        return this.currentValue;
    }

    public void set(T value) {
        this.currentValue = value;
    }

    @Override
    public String toString() {
        return this.currentValue.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardSlot<?> boardSlot = (BoardSlot<?>) o;
        return Objects.equals(currentValue, boardSlot.currentValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentValue);
    }
}
