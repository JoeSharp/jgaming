package uk.co.joesharpcs.gaming.board;

import java.util.Objects;
import java.util.Optional;

public class BoardSlot<T> {
    private T currentValue;
    private T savedValue;

    public BoardSlot(T currentValue) {
        this.currentValue = currentValue;
        this.savedValue = null;
    }

    public void save() {
        this.savedValue = this.currentValue;
    }

    public void restore() {
        this.currentValue = this.savedValue;
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
