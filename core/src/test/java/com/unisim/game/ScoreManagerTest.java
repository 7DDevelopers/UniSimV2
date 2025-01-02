package com.unisim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.unisim.game.Events.EventManager;
import com.unisim.game.Stages.MainStage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreManagerTest {

    ScoreManager scoreManager;
    LandPlot[] landPlots;
    EventManager eventManager;
    MainStage mainStage;

    public static LandPlot[][] data(){
        return new LandPlot[][] {};
    }

    public static int[] expected(){
        return new int[] {};
    }

    @BeforeAll
    static void mockSetUp() {
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        MockedConstruction<Texture> mockedTexture = Mockito.mockConstruction(Texture.class);
        MockedConstruction<Skin> mockedSkin = Mockito.mockConstruction(Skin.class);
    }

    @BeforeEach
    void setUp() {
        mainStage = mock(MainStage.class);
        scoreManager = new ScoreManager(mainStage);
        eventManager = spy(new EventManager(mainStage));
        mainStage.eventManager = eventManager;

        when(mainStage.getLandPlots()).thenReturn(landPlots);

        setLandPlots();
    }

    @AfterEach
    void reset() {
        setLandPlots();
    }

    @Test
    void initialTest() {
        when(mainStage.getLandPlots()).thenReturn(landPlots);
        assertEquals(0, scoreManager.getSatisfaction());
        assertEquals(0, scoreManager.getScore());
    }

    @Test
    void accommodationTest() {
        Building mockAccommodationBuilding = mock(Building.class);
        when(mockAccommodationBuilding.getName()).thenReturn("Accommodation");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockAccommodationBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(10,scoreManager.getSatisfaction());
    }

    @Test
    void accommodationNextToLectureHallTest(){
        Building mockAccomodationBuilding = mock(Building.class);
        Building mockLectureHallBuilding = mock(Building.class);
        when(mockAccomodationBuilding.getName()).thenReturn("Accommodation");
        when(mockLectureHallBuilding.getName()).thenReturn("LectureHall");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockAccomodationBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(landPlots[3].getBuildingPlaced()).thenReturn(mockLectureHallBuilding);
        when(landPlots[3].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(20, scoreManager.getSatisfaction());
    }

    @Test
    void accommodationNextToFoodHallText(){
        Building mockAccomodationBuilding = mock(Building.class);
        Building mockFoodHallBuilding = mock(Building.class);
        when(mockAccomodationBuilding.getName()).thenReturn("Accommodation");
        when(mockFoodHallBuilding.getName()).thenReturn("FoodHall");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockAccomodationBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(landPlots[3].getBuildingPlaced()).thenReturn(mockFoodHallBuilding);
        when(landPlots[3].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(20, scoreManager.getSatisfaction());
    }

    @Test
    void accommodationNextToGymText(){
        Building mockAccomodationBuilding = mock(Building.class);
        Building mockGymBuilding = mock(Building.class);
        when(mockAccomodationBuilding.getName()).thenReturn("Accommodation");
        when(mockGymBuilding.getName()).thenReturn("Gym");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockAccomodationBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(landPlots[3].getBuildingPlaced()).thenReturn(mockGymBuilding);
        when(landPlots[3].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(20, scoreManager.getSatisfaction());
    }

    @Test
    void accommodationNextToClubext(){
        Building mockAccomodationBuilding = mock(Building.class);
        Building mockClubBuilding = mock(Building.class);
        when(mockAccomodationBuilding.getName()).thenReturn("Accommodation");
        when(mockClubBuilding.getName()).thenReturn("Club");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockAccomodationBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(landPlots[3].getBuildingPlaced()).thenReturn(mockClubBuilding);
        when(landPlots[3].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(20, scoreManager.getSatisfaction());
    }

    @Test
    void lectureHallText(){
        Building mockLectureHallBuilding = mock(Building.class);
        when(mockLectureHallBuilding.getName()).thenReturn("LectureHall");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockLectureHallBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(0, scoreManager.getSatisfaction());
    }

    @Test
    void foodhallTest(){
        Building mockFoodHallBuilding = mock(Building.class);
        when(mockFoodHallBuilding.getName()).thenReturn("FoodHall");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockFoodHallBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(5, scoreManager.getSatisfaction());
    }

    @Test
    void gymTest(){
        Building mockGymBuilding = mock(Building.class);
        when(mockGymBuilding.getName()).thenReturn("Gym");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockGymBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(5, scoreManager.getSatisfaction());
    }

    @Test
    void clubTest(){
        Building mockClubBuilding = mock(Building.class);
        when(mockClubBuilding.getName()).thenReturn("Club");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockClubBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(5, scoreManager.getSatisfaction());
    }


    private void setLandPlots(){
        landPlots = new LandPlot[9];
        landPlots[0] = spy(new LandPlot(2, 360 + (int) (8.5 * 40), 2 * 40, 80, 80));
        landPlots[1] = spy(new LandPlot(2, 360 + (int) (3.5 * 40), 2 * 40, 80, 80));
        landPlots[2] = spy(new LandPlot(3, 360 + 3 * 40, (int) (5.5 * 40), 120, 120));
        landPlots[3] = spy(new LandPlot(3, 360 + 3 * 40, (int) (8.6 * 40), 120, 120));
        landPlots[4] = spy(new LandPlot(3, 360 + (int) (12.5 * 40), (13 * 40), 120, 120));
        landPlots[5] = spy(new LandPlot(3, 360 + (int) (16.7 * 40), (11 * 40), 120, 120));
        landPlots[6] = spy(new LandPlot(3, 360 + (int) (20.7 * 40), 11 * 40, 120, 120));
        landPlots[7] = spy(new LandPlot(3, 360 + (int) (24.2 * 40), 11 * 40, 120, 120));
        landPlots[8] = spy(new LandPlot(2, 360 + 24 * 40, (int) (6.5 * 40), 80, 80));
    }

}
