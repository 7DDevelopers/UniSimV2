package com.unisim.game.Leaderboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardManagerTest {

    LeaderboardManager leaderboardManager;

    @BeforeEach
    void setUp() {
        leaderboardManager = new LeaderboardManager("leaderboardTest.csv");
    }

    @Test
    void addEntryTest() {
        leaderboardManager.addEntry("User1", 10);
        leaderboardManager.addEntry("User2", 5);
        List<Entry> correctResult = new ArrayList<>();
        correctResult.add(new Entry("User1", 10));
        correctResult.add(new Entry("User2", 5));
        for (int i = 0; i < correctResult.size(); i++) {
            assertTrue(entryEqual(leaderboardManager.getLeaderboard().get(i),correctResult.get(i)));
        }
    }

    private boolean entryEqual(Entry entry1, Entry entry2){
        return entry1.getName() == entry2.getName() && entry1.getScore() == entry2.getScore();
    }
}
