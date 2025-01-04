package com.unisim.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BuildingTest {
    @Test
    void variousBuildingTypeTest() {
        assertEquals(5, main.buildingTypes.length);
        assertAll("",
            () -> assertEquals("Accommodation", main.buildingTypes[0].getName()),
            () -> assertEquals("LectureHall", main.buildingTypes[1].getName()),
            () -> assertEquals("FoodHall", main.buildingTypes[2].getName()),
            () -> assertEquals("Gym", main.buildingTypes[3].getName()),
            () -> assertEquals("Club", main.buildingTypes[4].getName())
        );
    }

}
