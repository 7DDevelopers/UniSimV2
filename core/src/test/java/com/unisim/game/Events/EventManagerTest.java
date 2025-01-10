package com.unisim.game.Events;

import com.unisim.game.GameMap;
import com.unisim.game.Stages.MainStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static  org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class EventManagerTest {

    // Declare EventManager instance to be tested
    EventManager eventManager;

    // Setup mock objects before each test
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Mock MainStage and GameMap
        MainStage mainStage = mock(MainStage.class);
        GameMap gameMap = mock(GameMap.class);

        // Use reflection to set the mocked GameMap in MainStage
        Field mapField = MainStage.class.getDeclaredField("map");
        mapField.setAccessible(true);
        mapField.set(mainStage, gameMap);

        // Initialize the EventManager with the mocked MainStage
        eventManager = new EventManager(mainStage);
    }

    // Test method for "Exam Season" event
    @Test
    void examSeasonTest() {
        eventManager.startExamSeason(0);
        // Verify the bonus values during the Exam Season event
        assertAll("",
            () -> assertEquals(10, eventManager.lectureHallBonus()),
            () -> assertEquals(5, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(-10, eventManager.clubBonus())
        );

        // End the Exam Season event and verify the bonus values are reset
        eventManager.examSeasonEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    // Test method for "Storm" event
    @Test
    void stormTest() {
        eventManager.startStorm(0);
        // Verify the bonus values during the Storm event
        assertAll("",
            () -> assertEquals(-2, eventManager.lectureHallBonus()),
            () -> assertEquals(10, eventManager.accommodationBonus()),
            () -> assertEquals(-2, eventManager.foodHallBonus()),
            () -> assertEquals(-2, eventManager.gymBonus()),
            () -> assertEquals(-2, eventManager.clubBonus())
        );

        // End the Storm event and verify the bonus values are reset
        eventManager.stormEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    // Test method for "Freshers Week" event
    @Test
    void freshersWeekTest() {
        eventManager.startFreshersWeek(0);
        // Verify the bonus values during the Freshers Week event
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(15, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(10, eventManager.clubBonus())
        );

        // End the Freshers Week event and verify the bonus values are reset
        eventManager.freshersWeekEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    // Test method for "Heatwave" event
    @Test
    void heatwaveTest() {
        eventManager.startHeatwave(0);
        // Verify the bonus values during the Heatwave event
        assertAll("",
            () -> assertEquals(5, eventManager.lectureHallBonus()),
            () -> assertEquals(5, eventManager.accommodationBonus()),
            () -> assertEquals(5, eventManager.foodHallBonus()),
            () -> assertEquals(5, eventManager.gymBonus()),
            () -> assertEquals(5, eventManager.clubBonus())
        );

        // End the Heatwave event and verify the bonus values are reset
        eventManager.heatwaveEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    // Test method for "Winter" event
    @Test
    void wintertest() {
        eventManager.startWinter(0);
        // Verify the bonus values during the Winter event
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(10, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );

        // End the Winter event and verify the bonus values are reset
        eventManager.winterEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    // Test method for handling multiple events occurring at once
    @Test
    void multipleEventsTest() {
        eventManager.startFreshersWeek(0);
        eventManager.startStorm(0);
        // Verify the combined bonus values when both Freshers Week and Storm are active
        assertAll("",
            () -> assertEquals(-2, eventManager.lectureHallBonus()),
            () -> assertEquals(25, eventManager.accommodationBonus()),
            () -> assertEquals(-2, eventManager.foodHallBonus()),
            () -> assertEquals(-2, eventManager.gymBonus()),
            () -> assertEquals(8, eventManager.clubBonus())
        );

        // End Freshers Week and start Exam Season, then verify bonus values
        eventManager.freshersWeekEvent.end();
        eventManager.startExamSeason(0);
        assertAll("",
            () -> assertEquals(8, eventManager.lectureHallBonus()),
            () -> assertEquals(15, eventManager.accommodationBonus()),
            () -> assertEquals(-2, eventManager.foodHallBonus()),
            () -> assertEquals(-2, eventManager.gymBonus()),
            () -> assertEquals(-12, eventManager.clubBonus())
        );

        // End the Storm and Exam Season events, and verify bonus values reset
        eventManager.stormEvent.end();
        eventManager.examSeasonEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    // Test method for realistic simulation of events at different times
    @Test
    void realisticSimulationTest() {
        List<String> expected;

        // Start Freshers Week and Storm events
        eventManager.startFreshersWeek(300);
        eventManager.startStorm(300);
        expected = Arrays.asList("Freshers Week", "Rain storm");
        assertEquals(expected, eventManager.getActiveEvents());

        // Verify event changes over time
        eventManager.eventChecker(290);
        expected = Arrays.asList("Freshers Week", "Rain storm");
        assertEquals(expected, eventManager.getActiveEvents());

        eventManager.eventChecker(280);
        assertTrue(eventManager.getActiveEvents().isEmpty());

        // Start more events and check active events at different times
        eventManager.startExamSeason(150);
        eventManager.startStorm(145);
        eventManager.startWinter(145);
        expected = Arrays.asList("Rain storm", "Exam season", "Christmas");
        assertEquals(expected, eventManager.getActiveEvents());
        eventManager.eventChecker(134);
        expected = Arrays.asList("Rain storm", "Christmas");
        assertEquals(expected, eventManager.getActiveEvents());
        eventManager.eventChecker(129);
        assertTrue(eventManager.getActiveEvents().isEmpty());

        // Start Heatwave and Freshers Week events, and check their overlap
        eventManager.startHeatwave(100);
        eventManager.startFreshersWeek(90);
        expected = Arrays.asList("Freshers Week", "Heatwave");
        assertEquals(expected, eventManager.getActiveEvents());
        eventManager.eventChecker(86);
        assertEquals(expected, eventManager.getActiveEvents());
        eventManager.eventChecker(85);
        expected = Arrays.asList("Freshers Week");
        assertEquals(expected, eventManager.getActiveEvents());
        eventManager.eventChecker(76);
        assertEquals(expected, eventManager.getActiveEvents());
        eventManager.eventChecker(75);
        assertTrue(eventManager.getActiveEvents().isEmpty());
    }
}
