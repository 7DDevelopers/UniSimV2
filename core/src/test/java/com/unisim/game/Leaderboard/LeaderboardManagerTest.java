package com.unisim.game.Leaderboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardManagerTest {

    // Declare the LeaderboardManager object that will be tested
    LeaderboardManager leaderboardManager;

    // Setup method to initialize the LeaderboardManager before each test
    @BeforeEach
    void setUp() {
        leaderboardManager = new LeaderboardManager("leaderboardTest.csv"); // Initialize with a test CSV file
    }

    // Test method to verify the functionality of adding entries to the leaderboard
    @Test
    void addEntryTest() {
        // Add two entries to the leaderboard
        leaderboardManager.addEntry("User1", 10); // User1 with score 10
        leaderboardManager.addEntry("User2", 5); // User2 with score 5

        // Prepare a list of correct entries that should match the leaderboard
        List<Entry> correctResult = new ArrayList<>();
        correctResult.add(new Entry("User1", 10)); // Expected entry for User1
        correctResult.add(new Entry("User2", 5));  // Expected entry for User2

        // Loop through each entry in the correct result list and compare it with the leaderboard
        for (int i = 0; i < correctResult.size(); i++) {
            // Use a helper method to check if the entries are equal
            assertTrue(entryEqual(leaderboardManager.getLeaderboard().get(i), correctResult.get(i)));
        }
    }

    // Helper method to compare two Entry objects for equality
    private boolean entryEqual(Entry entry1, Entry entry2){
        // Check if both name and score are equal
        return entry1.getName() == entry2.getName() && entry1.getScore() == entry2.getScore();
    }
}
