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
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreManagerTest {

    // Declare objects for testing
    ScoreManager scoreManager;
    LandPlot[] landPlots;
    EventManager eventManager;
    MainStage mainStage;

    // Static setup for mocking Gdx.files, Texture, and Skin classes before any tests run
    @BeforeAll
    static void mockSetUp() {
        Gdx.files = mock(com.badlogic.gdx.Files.class); // Mocking the Gdx.files class for file handling
        try {
            // Mocking the construction of Texture and Skin objects to prevent actual resource loading during tests
            Mockito.mockConstruction(Texture.class);
            Mockito.mockConstruction(Skin.class);
        } catch (Exception e) {
            // Catch any exceptions that may arise during the mocking process
        }
    }

    // Setup method to initialize objects before each test
    @BeforeEach
    void setUp() {
        mainStage = mock(MainStage.class); // Mock MainStage
        scoreManager = new ScoreManager(mainStage); // Create ScoreManager instance with the mocked MainStage
        eventManager = spy(new EventManager(mainStage)); // Create and spy on EventManager
        mainStage.eventManager = eventManager; // Set the eventManager in the mainStage mock

        when(mainStage.getLandPlots()).thenReturn(landPlots); // Mock the getLandPlots method

        setLandPlots(); // Initialize land plots for each test
    }

    // Reset the land plots after each test
    @AfterEach
    void reset() {
        setLandPlots(); // Reset the land plots for the next test
    }

    // Test the initial satisfaction and score values
    @Test
    void initialTest() {
        when(mainStage.getLandPlots()).thenReturn(landPlots);
        assertEquals(0, scoreManager.getSatisfaction()); // Verify that initial satisfaction is 0
        assertEquals(0, scoreManager.getScore()); // Verify that initial score is 0
    }

    // Test the satisfaction value when an accommodation building is placed
    @Test
    void accommodationTest() {
        Building mockAccommodationBuilding = mock(Building.class);
        when(mockAccommodationBuilding.getName()).thenReturn("Accommodation"); // Mock the name of the building
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockAccommodationBuilding); // Set the building in the land plot
        when(landPlots[2].isOccupied()).thenReturn(true); // Set the land plot as occupied
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(10, scoreManager.getSatisfaction()); // Verify satisfaction after placing accommodation
    }

    // Test the satisfaction value when an accommodation building is next to a lecture hall
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

        assertEquals(20, scoreManager.getSatisfaction()); // Verify satisfaction when accommodation is next to lecture hall
    }

    // Test the satisfaction value when an accommodation building is next to a food hall
    @Test
    void accommodationNextToFoodHallTest(){
        Building mockAccomodationBuilding = mock(Building.class);
        Building mockFoodHallBuilding = mock(Building.class);
        when(mockAccomodationBuilding.getName()).thenReturn("Accommodation");
        when(mockFoodHallBuilding.getName()).thenReturn("FoodHall");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockAccomodationBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(landPlots[3].getBuildingPlaced()).thenReturn(mockFoodHallBuilding);
        when(landPlots[3].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(20, scoreManager.getSatisfaction()); // Verify satisfaction when accommodation is next to food hall
    }

    // Test the satisfaction value when an accommodation building is next to a gym
    @Test
    void accommodationNextToGymTest(){
        Building mockAccomodationBuilding = mock(Building.class);
        Building mockGymBuilding = mock(Building.class);
        when(mockAccomodationBuilding.getName()).thenReturn("Accommodation");
        when(mockGymBuilding.getName()).thenReturn("Gym");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockAccomodationBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(landPlots[3].getBuildingPlaced()).thenReturn(mockGymBuilding);
        when(landPlots[3].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(20, scoreManager.getSatisfaction()); // Verify satisfaction when accommodation is next to gym
    }

    // Test the satisfaction value when an accommodation building is next to a club
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

        assertEquals(20, scoreManager.getSatisfaction()); // Verify satisfaction when accommodation is next to club
    }

    // Test the satisfaction value when a lecture hall is placed
    @Test
    void lectureHallTest(){
        Building mockLectureHallBuilding = mock(Building.class);
        when(mockLectureHallBuilding.getName()).thenReturn("LectureHall");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockLectureHallBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(0, scoreManager.getSatisfaction()); // Verify satisfaction when a lecture hall is placed (no bonus)
    }

    // Test the satisfaction value when a food hall is placed
    @Test
    void foodhallTest(){
        Building mockFoodHallBuilding = mock(Building.class);
        when(mockFoodHallBuilding.getName()).thenReturn("FoodHall");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockFoodHallBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(5, scoreManager.getSatisfaction()); // Verify satisfaction when food hall is placed
    }

    // Test the satisfaction value when a gym is placed
    @Test
    void gymTest(){
        Building mockGymBuilding = mock(Building.class);
        when(mockGymBuilding.getName()).thenReturn("Gym");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockGymBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(5, scoreManager.getSatisfaction()); // Verify satisfaction when gym is placed
    }

    // Test the satisfaction value when a club is placed
    @Test
    void clubTest(){
        Building mockClubBuilding = mock(Building.class);
        when(mockClubBuilding.getName()).thenReturn("Club");
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockClubBuilding);
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(5, scoreManager.getSatisfaction()); // Verify satisfaction when club is placed
    }

    // Test the score calculation logic
    @Test
    void scoreTest() {
        Building mockAccomodationBuilding = mock(Building.class);
        when(mockAccomodationBuilding.getName()).thenReturn("Accommodation");
        when(mainStage.getLandPlots()).thenReturn(landPlots);

        assertEquals(0, scoreManager.getScore()); // Verify score is initially 0

        // Simulate time passing and check if score changes
        for (int i = 0; i < 10; i++) {
            scoreManager.getSatisfaction();
            scoreManager.updateScore();
        }
        assertEquals(0, scoreManager.getScore()); // Verify that the score is still 0 after some time

        // Set accommodation as placed and verify score increase
        when(landPlots[2].isOccupied()).thenReturn(true);
        when(landPlots[2].getBuildingPlaced()).thenReturn(mockAccomodationBuilding);
        for (int i = 0; i < 10; i++) {
            scoreManager.getSatisfaction();
            scoreManager.updateScore();
        }
        assertEquals(100, scoreManager.getScore()); // Verify score after accommodation placement

        // Simulate event and verify further score increase
        eventManager.startFreshersWeek(0);
        for (int i = 0; i < 10; i++) {
            scoreManager.getSatisfaction();
            scoreManager.updateScore();
        }
        assertEquals(350, scoreManager.getScore()); // Verify score after Freshers Week event
    }

    // Helper method to set up land plots for tests
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
