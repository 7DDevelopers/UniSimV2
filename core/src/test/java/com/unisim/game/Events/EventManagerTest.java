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

    EventManager eventManager;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MainStage mainStage = mock(MainStage.class);

        GameMap gameMap = mock(GameMap.class);

        Field mapField = MainStage.class.getDeclaredField("map");
        mapField.setAccessible(true);
        mapField.set(mainStage, gameMap);

        // Initialize the EventManager with the mocked MainStage
        eventManager = new EventManager(mainStage);
    }

    @Test
    void ExamSeasonTest() {
        eventManager.startExamSeason(0);
        assertAll("",
            () -> assertEquals(10, eventManager.lectureHallBonus()),
            () -> assertEquals(5, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(-10, eventManager.clubBonus())
        );

        eventManager.examSeasonEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    @Test
    void stormTest() {
        eventManager.startStorm(0);
        assertAll("",
            () -> assertEquals(-2, eventManager.lectureHallBonus()),
            () -> assertEquals(10, eventManager.accommodationBonus()),
            () -> assertEquals(-2, eventManager.foodHallBonus()),
            () -> assertEquals(-2, eventManager.gymBonus()),
            () -> assertEquals(-2, eventManager.clubBonus())
        );

        eventManager.stormEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    @Test
    void freshersWeekTest() {
        eventManager.startFreshersWeek(0);
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(15, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(10, eventManager.clubBonus())
        );

        eventManager.freshersWeekEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    @Test
    void heatwaveTest() {
        eventManager.startHeatwave(0);
        assertAll("",
            () -> assertEquals(5, eventManager.lectureHallBonus()),
            () -> assertEquals(5, eventManager.accommodationBonus()),
            () -> assertEquals(5, eventManager.foodHallBonus()),
            () -> assertEquals(5, eventManager.gymBonus()),
            () -> assertEquals(5, eventManager.clubBonus())
        );

        eventManager.heatwaveEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    @Test
    void wintertest(){
        eventManager.startWinter(0);
        assertAll("",
            () -> assertEquals(2, eventManager.lectureHallBonus()),
            () -> assertEquals(10, eventManager.accommodationBonus()),
            () -> assertEquals(2, eventManager.foodHallBonus()),
            () -> assertEquals(-2, eventManager.gymBonus()),
            () -> assertEquals(-2, eventManager.clubBonus())
        );

        eventManager.winterEvent.end();
        assertAll("",
            () -> assertEquals(0, eventManager.lectureHallBonus()),
            () -> assertEquals(0, eventManager.accommodationBonus()),
            () -> assertEquals(0, eventManager.foodHallBonus()),
            () -> assertEquals(0, eventManager.gymBonus()),
            () -> assertEquals(0, eventManager.clubBonus())
        );
    }

    @Test
    void multipleEventsTest() {
        eventManager.startFreshersWeek(0);
        eventManager.startStorm(0);
        assertAll("",
            () -> assertEquals(-2, eventManager.lectureHallBonus()),
            () -> assertEquals(25, eventManager.accommodationBonus()),
            () -> assertEquals(-2, eventManager.foodHallBonus()),
            () -> assertEquals(-2, eventManager.gymBonus()),
            () -> assertEquals(8, eventManager.clubBonus())
        );

        eventManager.freshersWeekEvent.end();
        eventManager.startExamSeason(0);
        assertAll("",
            () -> assertEquals(8, eventManager.lectureHallBonus()),
            () -> assertEquals(15, eventManager.accommodationBonus()),
            () -> assertEquals(-2, eventManager.foodHallBonus()),
            () -> assertEquals(-2, eventManager.gymBonus()),
            () -> assertEquals(-12, eventManager.clubBonus())
        );

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

    @Test
    void realisticSimulationTest() {
        List<String> expected;

        eventManager.startFreshersWeek(300);
        eventManager.startStorm(300);
        expected = Arrays.asList("Freshers Week", "Rain storm");
        assertEquals(expected, eventManager.getActiveEvents());

        eventManager.eventChecker(290);
        expected = Arrays.asList("Freshers Week", "Rain storm");
        assertEquals(expected, eventManager.getActiveEvents());

        eventManager.eventChecker(280);
        assertTrue(eventManager.getActiveEvents().isEmpty());

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
