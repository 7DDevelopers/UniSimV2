package com.unisim.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BuildingTest {

    // Test method to verify the various building types in the 'main' class
    @Test
    void variousBuildingTypeTest() {
        // Check if the length of the buildingTypes array is 5
        assertEquals(5, main.buildingTypes.length);

        // Perform assertions to verify the names of each building type in the 'buildingTypes' array
        assertAll("",
            () -> assertEquals("Accommodation", main.buildingTypes[0].getName()), // Verify the first building type is "Accommodation"
            () -> assertEquals("LectureHall", main.buildingTypes[1].getName()),    // Verify the second building type is "LectureHall"
            () -> assertEquals("FoodHall", main.buildingTypes[2].getName()),       // Verify the third building type is "FoodHall"
            () -> assertEquals("Gym", main.buildingTypes[3].getName()),            // Verify the fourth building type is "Gym"
            () -> assertEquals("Club", main.buildingTypes[4].getName())            // Verify the fifth building type is "Club"
        );
    }

}
