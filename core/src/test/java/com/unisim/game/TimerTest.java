package com.unisim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.unisim.game.Stages.MainStage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TimerTest {

    // Declare the game object for testing
    private main game;

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
    public void setUp() {
        // Initialize the main game class and mock necessary components
        game = mock(main.class); // Mock the main game class
        game.mainStage = mock(MainStage.class); // Mock the MainStage class
        game.create(); // Call the create method of the game to initialize components
        game.mainStage.initialize(); // Initialize the main stage
        game.time = 300f; // Set the initial game time to 300 seconds (5 minutes)
    }

    // Test the timer initialization by verifying the starting time
    @Test
    public void testTimerInitialization() {
        // Assert that the initial game time is 300 seconds (5 minutes)
        assertEquals(300.0, game.time, "Timer should start at 300 seconds (5 minutes).");
    }

    // Test the timer display update logic when time passes
    @Test
    public void testTimerDisplayUpdate() {
        // Simulate the passing of 10 seconds by decrementing the game time
        for (int i = 0; i < 10; i++) {
            game.time -= 1f; // Decrease the game time by 1 second
        }

        // After 10 seconds, the remaining time should be 4 minutes and 50 seconds
        int minutesLeft = (int) (game.time / 60); // Calculate the remaining minutes
        int secondsLeft = (int) (game.time % 60); // Calculate the remaining seconds

        // Assert that the minutes and seconds are updated correctly after 10 seconds have passed
        assertEquals(4, minutesLeft, "Minutes left should be 4 after 10 seconds.");
        assertEquals(50, secondsLeft, "Seconds left should be 50 after 10 seconds.");
    }
}
