package uk.co.joesharpcs.gaming.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardSlotTest {

    @Test
    public void initialiseCorrectly() {
        var slot = new BoardSlot<>("A");

        assertEquals("A", slot.get());
    }

    @Test
    public void saveRestoreCorrectly() {
        var slot = new BoardSlot<>(6);

        slot.save();
        slot.set(7);
        slot.save();
        slot.set(8);
        slot.restore();

        assertEquals(7, slot.get());
        assertEquals(6, slot.getPrevious());
    }

    @Test
    public void setGetCorrectly() {
        var slot = new BoardSlot<>("F");

        slot.set("G");

        assertEquals("G", slot.get());
    }

    @Test
    public void hashCodeExpected() {
        var slot = new BoardSlot<>("F");

        var before = slot.hashCode();
        slot.save();
        slot.set("G");
        slot.restore();
        var after = slot.hashCode();

        assertEquals(before, after, "Hashes should equal after a restore");
    }
}
