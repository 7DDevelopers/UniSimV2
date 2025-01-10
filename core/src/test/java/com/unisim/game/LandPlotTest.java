package com.unisim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;

class LandPlotTest {

    // LandPlot objects representing small and large land plots
    LandPlot smallLandPlot;
    LandPlot largeLandPlot;

    // Static setup for mocking Gdx files and Texture, Skin classes before all tests
    @BeforeAll
    static void mockSetUp() {
        Gdx.files = mock(com.badlogic.gdx.Files.class); // Mocking the Gdx.files class for file handling
        try {
            // Mocking the construction of Texture and Skin objects to avoid real resource loading
            Mockito.mockConstruction(Texture.class);
            Mockito.mockConstruction(Skin.class);
        } catch (Exception e) {
            // Catch any exceptions during the mocking process
        }
    }

    // Setup method to initialize small and large land plot objects before each test
    @BeforeEach
    void setUp() {
        smallLandPlot = new LandPlot(2, 0, 0, 80, 80);  // Creating a small land plot with dimensions 80x80
        largeLandPlot = new LandPlot(3, 0, 0, 120, 120); // Creating a large land plot with dimensions 120x120
    }

    // Test for placing a building on a land plot when a building is selected
    @Test
    void buttonClickWithSelectedBuildingTest() {
        main.selectedBuilding = 0;  // Simulate selecting a building

        simulateTouchEvent(largeLandPlot); // Simulate a touch event on the large land plot

        // Assert that the land plot is now occupied, a building is placed, and the selected building is reset
        assertTrue(largeLandPlot.isOccupied());
        assertNotNull(largeLandPlot.getBuildingPlaced());
        assertEquals(-1, main.selectedBuilding);  // Selected building is reset to -1
    }

    // Test for placing a building on a land plot when no building is selected
    @Test
    void buttonClickWithoutSelectedBuildingTest() {
        main.selectedBuilding = -1;  // Simulate no building being selected

        simulateTouchEvent(largeLandPlot); // Simulate a touch event on the large land plot

        // Assert that the land plot is not occupied, no building is placed, and the selected building remains -1
        assertFalse(largeLandPlot.isOccupied());
        assertNull(largeLandPlot.getBuildingPlaced());
        assertEquals(-1, main.selectedBuilding);  // No building selected
    }

    // Test for placing a large building on a large land plot
    @Test
    void largeBuildingOnLargeLandPlotTest() {
        main.selectedBuilding = 0;  // Simulate selecting a large building (Accommodation)

        simulateTouchEvent(largeLandPlot);  // Simulate a touch event on the large land plot

        // Assert that the large land plot is occupied by the Accommodation building and the selected building is reset
        assertTrue(largeLandPlot.isOccupied());
        assertNotNull(largeLandPlot.getBuildingPlaced());
        assertEquals("Accommodation", largeLandPlot.getBuildingPlaced().getName());  // Verify the correct building name
        assertEquals(-1, main.selectedBuilding);  // Selected building is reset to -1
    }

    // Test for placing a small building on a small land plot
    @Test
    void smallBuildingOnSmallLandPlotTest() {
        main.selectedBuilding = 3;  // Simulate selecting a small building (Gym)

        simulateTouchEvent(smallLandPlot);  // Simulate a touch event on the small land plot

        // Assert that the small land plot is occupied by the Gym building and the selected building is reset
        assertTrue(smallLandPlot.isOccupied());
        assertNotNull(smallLandPlot.getBuildingPlaced());
        assertEquals("Gym", smallLandPlot.getBuildingPlaced().getName());  // Verify the correct building name
        assertEquals(-1, main.selectedBuilding);  // Selected building is reset to -1
    }

    // Test for placing a small building on a large land plot
    @Test
    void smallBuildingOnLargeLandPlotTest() {
        main.selectedBuilding = 3;  // Simulate selecting a small building (Gym)

        simulateTouchEvent(largeLandPlot);  // Simulate a touch event on the large land plot

        // Assert that the large land plot is occupied by the Gym building and the selected building is reset
        assertTrue(largeLandPlot.isOccupied());
        assertNotNull(largeLandPlot.getBuildingPlaced());
        assertEquals("Gym", largeLandPlot.getBuildingPlaced().getName());  // Verify the correct building name
        assertEquals(-1, main.selectedBuilding);  // Selected building is reset to -1
    }

    // Test for placing a large building on a small land plot, which should not be possible
    @Test
    void largeBuildingOnSmallLandPlotTest() {
        main.selectedBuilding = 0;  // Simulate selecting a large building (Accommodation)

        simulateTouchEvent(smallLandPlot);  // Simulate a touch event on the small land plot

        // Assert that the small land plot is not occupied and no building is placed
        assertFalse(smallLandPlot.isOccupied());
        assertNull(smallLandPlot.getBuildingPlaced());
        assertEquals(0, main.selectedBuilding);  // Selected building remains as the original one (Accommodation)
    }

    // Test for trying to place a different building on an already occupied land plot
    @Test
    void placeBuildingOnOccupiedLandPlotTest() {
        main.selectedBuilding = 0;  // Simulate selecting a building (Accommodation)
        simulateTouchEvent(largeLandPlot);  // Place the first building on the large land plot

        main.selectedBuilding = 1;  // Simulate selecting another building (LectureHall)
        simulateTouchEvent(largeLandPlot);  // Try placing the second building on the same land plot

        // Assert that the land plot is still occupied by the first building (Accommodation)
        assertTrue(largeLandPlot.isOccupied());
        assertNotNull(largeLandPlot.getBuildingPlaced());
        assertEquals("Accommodation", largeLandPlot.getBuildingPlaced().getName());
    }

    // Helper method to simulate a touch event on a land plot
    private void simulateTouchEvent(LandPlot landPlot) {
        InputEvent inputEvent = new InputEvent();
        inputEvent.setType(InputEvent.Type.touchUp);  // Set the event type to touchUp

        // Get the ClickListener from the land plot's button and trigger the clicked event
        ClickListener listener = (ClickListener) landPlot.getButton().getListeners().get(1);
        listener.clicked(inputEvent, 0, 0);  // Trigger the click event on the land plot
    }
}
