package com.unisim.game.Achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.unisim.game.Events.EventManager;
import com.unisim.game.LandPlot;
import com.unisim.game.ScoreManager;
import com.unisim.game.Stages.MainStage;
import com.unisim.game.main;
import org.junit.jupiter.api.*;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// The AchievementManagerTest class tests the functionality of the AchievementManager class.
// It uses JUnit and Mockito to mock game objects and simulate different conditions.

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AchievementManagerTest {

    // Declaring mock objects and variables required for tests.
    AchievementManager achievementManager;
    main mockMain;
    MainStage mockMainStage;
    ScoreManager scoreManager;
    EventManager eventManager;
    LandPlot[] landPlots;

    // This method is called before all tests. It sets up mocks for various game classes and
    // initializes necessary objects such as mainStage, scoreManager, and eventManager.
    @BeforeAll
    void mockSetUp() {
        Gdx.files = mock(com.badlogic.gdx.Files.class);  // Mocking the file system.

        try{
            Mockito.mockConstruction(Texture.class);  // Mocking the Texture class (used for graphics).
            Mockito.mockConstruction(Skin.class);     // Mocking the Skin class (used for UI).
        } catch(Exception e){
            // Handle exception if any mocking fails.
        }

        // Initialize land plots that will be used in the tests.
        landPlotsInitialise();

        // Creating mock instances for the mainStage, scoreManager, eventManager, and main game class.
        mockMainStage = mock(MainStage.class);
        scoreManager = spy(new ScoreManager(mockMainStage));
        eventManager = new EventManager(mockMainStage);

        // Setting mock objects to simulate the actual game setup.
        mockMainStage.scoreManager = scoreManager;
        mockMainStage.eventManager = eventManager;

        mockMain = mock(main.class);
        mockMain.mainStage = mockMainStage;

        // Resetting achievements before each test run.
        obtainedAchievementsReset();
    }

    // This method is called before each individual test, ensuring a fresh setup for each test case.
    @BeforeEach
    void setUp() {
        achievementManager = new AchievementManager("achievements.csv", mockMain);
    }

    // After each test, reset land plots and obtained achievements.
    @AfterEach
    void reset() {
        landPlotsInitialise();
        obtainedAchievementsReset();
    }

    // Test case to check if the prestigious achievement is correctly obtained.
    @Test
    void prestigiousTest() {
        assertFalse(achievementManager.getAchievements().get(0).isObtained());  // Ensure achievement is initially not obtained.
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);  // Return the mock land plots.
        when(scoreManager.getSatisfaction()).thenReturn(75);  // Mock the satisfaction level.

        achievementManager.CheckContinuousAchievements();  // Check for continuous achievements.

        assertTrue(achievementManager.getAchievements().get(0).isObtained());  // Ensure the achievement is obtained.
    }

    // Test case to check if the clubber achievement is correctly obtained.
    @Test
    void clubberTest() {
        assertFalse(achievementManager.getAchievements().get(1).isObtained());  // Ensure achievement is initially not obtained.
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);  // Return the mock land plots.

        // Simulate placing buildings in the land plots.
        for (int i = 0; i < 3; i++) {
            main.selectedBuilding = 4;
            simulateTouchEvent(landPlots[i]);
        }

        achievementManager.CheckContinuousAchievements();  // Check for continuous achievements.
        assertTrue(achievementManager.getAchievements().get(1).isObtained());  // Ensure the achievement is obtained.
    }

    // Test case to check if the builder achievement is correctly obtained.
    @Test
    void builderTest() {
        assertFalse(achievementManager.getAchievements().get(2).isObtained());  // Ensure achievement is initially not obtained.
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);  // Return the mock land plots.

        // Simulate placing buildings in land plots until the achievement is unlocked.
        for (int i = 0; i < landPlots.length - 1; i++) {
            main.selectedBuilding = 3;
            simulateTouchEvent(landPlots[i]);
            achievementManager.CheckContinuousAchievements();
            assertFalse(achievementManager.getAchievements().get(2).isObtained());  // Ensure achievement is not obtained yet.
        }

        // Finally, place a building to unlock the achievement.
        main.selectedBuilding = 3;
        simulateTouchEvent(landPlots[8]);

        achievementManager.CheckContinuousAchievements();  // Check for continuous achievements.
        assertTrue(achievementManager.getAchievements().get(2).isObtained());  // Ensure the achievement is obtained.
    }

    // Test case to check if the lecturer achievement is correctly obtained based on finished games.
    @Test
    void lecturerTest() {
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);  // Return the mock land plots.

        // Simulate checking achievements after several games are finished.
        for (int i = 1; i < 4; i++) {
            achievementManager.checkEndAchievements();  // Check for end achievements.
            assertFalse(achievementManager.getAchievements().get(3).isObtained());  // Ensure achievement is not obtained.
            achievementManager.setGamesFinished(i);  // Set the number of finished games.
        }

        achievementManager.checkEndAchievements();  // Final check for end achievements.
        assertTrue(achievementManager.getAchievements().get(3).isObtained());  // Ensure the achievement is obtained.
    }

    // Test case to check if the environmentalist achievement is correctly obtained.
    @Test
    void environmentalistTest() {
        main.selectedBuilding = 4;
        simulateTouchEvent(landPlots[0]);  // Simulate placing a building.
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);  // Return the mock land plots.
        achievementManager.checkEndAchievements();  // Check for end achievements.
        assertFalse(achievementManager.getAchievements().get(4).isObtained());  // Ensure achievement is not obtained yet.

        // Reinitialize land plots and check for the achievement again.
        landPlotsInitialise();
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);
        achievementManager.checkEndAchievements();  // Check for end achievements.
        assertTrue(achievementManager.getAchievements().get(4).isObtained());  // Ensure the achievement is obtained.
    }

    // Helper method to reset the obtained achievements by clearing the "obtainedAchievements.csv" file.
    private void obtainedAchievementsReset() {
        File file = new File("obtainedAchievements.csv");
        if(!file.exists()){
            try {
                file.createNewFile();  // Create the file if it doesn't exist.
            } catch (IOException e) {
                throw new RuntimeException(e);  // Throw exception if file creation fails.
            }
        }
        try{
            PrintWriter out = new PrintWriter(new FileWriter("obtainedAchievements.csv", false));
            out.print("");  // Clear the file contents.
            out.close();
        }
        catch (IOException e){
            System.err.println("Error writing leaderboard");  // Print error if file writing fails.
        }
    }

    // Helper method to initialize land plots with mock data.
    private void landPlotsInitialise() {
        landPlots = new LandPlot[9];  // Initialize the land plots array.
        landPlots[0] = new LandPlot(2, 360 + (int)(8.5 * 40), 2 * 40, 80, 80);
        landPlots[1] = new LandPlot(2, 360 + (int)(3.5 * 40), 2 * 40, 80, 80);
        landPlots[2] = new LandPlot(3, 360 + 3 * 40, (int)(5.5 * 40), 120, 120);
        // Additional land plots initialization goes here...
    }

    // Helper method to simulate a touch event on a land plot.
    private void simulateTouchEvent(LandPlot landPlot) {
        InputEvent inputEvent = new InputEvent();
        inputEvent.setType(InputEvent.Type.touchUp);  // Set the event type to touch-up.

        ClickListener listener = (ClickListener) landPlot.getButton().getListeners().get(1);
        listener.clicked(inputEvent, 0, 0);  // Simulate the touch event.
    }
}
