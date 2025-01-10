package com.unisim.game.Leaderboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {

    // Test method to verify the creation of leaderboard entries
    @Test
    void createEntryTest() {
        // Create Entry objects with different name and score values
        Entry entry1 = new Entry("User1", 5); // Valid entry with name "User1" and score 5
        Entry entry2 = new Entry("User2", 10); // Valid entry with name "User2" and score 10
        Entry entry3 = new Entry("User2", "5"); // Entry created with score as string "5" (should be parsed)

        // Verify that the name and score values are correctly assigned to each Entry
        assertAll("legalInput", // Group of assertions for this test case
            () -> assertEquals("User1", entry1.getName()), // Verify that entry1 has name "User1"
            () -> assertEquals(5, entry1.getScore()), // Verify that entry1 has score 5
            () -> assertEquals("User2", entry2.getName()), // Verify that entry2 has name "User2"
            () -> assertEquals(10, entry2.getScore()), // Verify that entry2 has score 10
            () -> assertEquals("User2", entry3.getName()), // Verify that entry3 has name "User2"
            () -> assertEquals(5, entry3.getScore()) // Verify that entry3 has score 5 (after converting the string to int)
        );
    }
}
