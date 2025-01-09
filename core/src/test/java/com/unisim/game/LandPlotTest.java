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

import org.mockito.MockedConstruction;
import org.mockito.Mockito;


class LandPlotTest {

    LandPlot smallLandPlot;
    LandPlot largeLandPlot;

    @BeforeAll
    static void mockSetUp() {
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        try{
            Mockito.mockConstruction(Texture.class);
            Mockito.mockConstruction(Skin.class);
        } catch (Exception e) {
        }
    }

    @BeforeEach
    void setUp() {
        smallLandPlot = new LandPlot(2, 0, 0, 80, 80);
        largeLandPlot = new LandPlot(3, 0, 0, 120, 120);
    }

    @Test
    void buttonClickWithSelectedBuildingTest() {
        main.selectedBuilding = 0;

        simulateTouchEvent(largeLandPlot);

        assertTrue(largeLandPlot.isOccupied());
        assertNotNull(largeLandPlot.getBuildingPlaced());
        assertEquals(-1, main.selectedBuilding);
    }

    @Test
    void buttonClickWithoutSelectedBuildingTest() {
        main.selectedBuilding = -1;

        simulateTouchEvent(largeLandPlot);

        assertFalse(largeLandPlot.isOccupied());
        assertNull(largeLandPlot.getBuildingPlaced());
        assertEquals(-1, main.selectedBuilding);
    }

    @Test
    void largeBuildingOnLargeLandPlotTest() {
        main.selectedBuilding = 0;

        simulateTouchEvent(largeLandPlot);

        assertTrue(largeLandPlot.isOccupied());
        assertNotNull(largeLandPlot.getBuildingPlaced());
        assertEquals("Accommodation", largeLandPlot.getBuildingPlaced().getName());
        assertEquals(-1, main.selectedBuilding);
    }

    @Test
    void smallBuildingOnSmallLandPlotTest() {
        main.selectedBuilding = 3;

        simulateTouchEvent(smallLandPlot);

        assertTrue(smallLandPlot.isOccupied());
        assertNotNull(smallLandPlot.getBuildingPlaced());
        assertEquals("Gym", smallLandPlot.getBuildingPlaced().getName());
        assertEquals(-1, main.selectedBuilding);
    }

    @Test
    void smallBuildingOnLargeLandPlotTest() {
        main.selectedBuilding = 3;

        simulateTouchEvent(largeLandPlot);

        assertTrue(largeLandPlot.isOccupied());
        assertNotNull(largeLandPlot.getBuildingPlaced());
        assertEquals("Gym", largeLandPlot.getBuildingPlaced().getName());
        assertEquals(-1, main.selectedBuilding);
    }

    @Test
    void largeBuildingOnSmallLandPlotTest() {
        main.selectedBuilding = 0;

        simulateTouchEvent(smallLandPlot);

        assertFalse(smallLandPlot.isOccupied());
        assertNull(smallLandPlot.getBuildingPlaced());
        assertEquals(0, main.selectedBuilding);
    }

    @Test
    void placeBuildingOnOccupiedLandPlotTest() {
        main.selectedBuilding = 0;
        simulateTouchEvent(largeLandPlot);

        main.selectedBuilding = 1;
        simulateTouchEvent(largeLandPlot);

        assertTrue(largeLandPlot.isOccupied());
        assertNotNull(largeLandPlot.getBuildingPlaced());
        assertEquals("Accommodation", largeLandPlot.getBuildingPlaced().getName());
    }

    private void simulateTouchEvent(LandPlot landPlot) {
        InputEvent inputEvent = new InputEvent();
        inputEvent.setType(InputEvent.Type.touchUp);

        ClickListener listener = (ClickListener) landPlot.getButton().getListeners().get(1);
        listener.clicked(inputEvent, 0, 0);
    }
}
