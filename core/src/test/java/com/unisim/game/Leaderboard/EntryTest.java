package com.unisim.game.Leaderboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {

    @Test
    void createEntryTest() {
        Entry entry1 = new Entry("User1", 5);
        Entry entry2 = new Entry("User2", 10);
        Entry entry3 = new Entry("User2", "5");

        assertAll("legalInput",
            () -> assertEquals("User1", entry1.getName()),
            () -> assertEquals(5, entry1.getScore()),
            () -> assertEquals("User2", entry2.getName()),
            () -> assertEquals(10, entry2.getScore()),
            () -> assertEquals("User2", entry3.getName()),
            () -> assertEquals(5, entry3.getScore())
        );
    }
}
