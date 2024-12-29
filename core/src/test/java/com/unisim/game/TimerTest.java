package com.unisim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.unisim.game.Stages.MainStage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TimerTest {

    private main game;

    @BeforeAll
    static void mockSetUp() {
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        MockedConstruction<Texture> mockedTexture = Mockito.mockConstruction(Texture.class);
        MockedConstruction<Skin> mockedSkin = Mockito.mockConstruction(Skin.class);
    }

    @BeforeEach
    public void setUp() {
        // Initialize the main game class
        game = mock(main.class);
        game.mainStage = mock(MainStage.class);
        game.create();
        game.mainStage.initialize();
        game.time = 300f;

    }

    @Test
    public void testTimerInitialization() {
        // Ensure that the timer starts at 300 seconds
        assertEquals(300.0, game.time, "Timer should start at 300 seconds (5 minutes).");
    }


    @Test
    public void testTimerDisplayUpdate() {
        // Simulate a few seconds passing and check if the time display updates correctly

        // Initial time (5 minutes = 300 seconds)
        int initialMinutes = 5;
        int initialSeconds = 0;

        // Simulate the game time passing for 10 seconds
        for (int i = 0; i < 10; i++) {
            game.time -= 1f;
        }

        // After 10 seconds, the time should be 4 minutes and 50 seconds
        int minutesLeft = (int) (game.time / 60);
        int secondsLeft = (int) (game.time % 60);

        assertEquals(4, minutesLeft, "Minutes left should be 4 after 10 seconds.");
        assertEquals(50, secondsLeft, "Seconds left should be 50 after 10 seconds.");
    }
}
