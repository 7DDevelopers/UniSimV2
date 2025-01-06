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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AchievementManagerTest {

    AchievementManager achievementManager;
    main mockMain;
    MainStage mockMainStage;
    ScoreManager scoreManager;
    EventManager eventManager;
    LandPlot[] landPlots;

    @BeforeAll
    void mockSetUp() {
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        MockedConstruction<Texture> mockedTexture = Mockito.mockConstruction(Texture.class);
        MockedConstruction<Skin> mockedSkin = Mockito.mockConstruction(Skin.class);

        landPlotsInitialise();

        mockMainStage = mock(MainStage.class);
        scoreManager = spy(new ScoreManager(mockMainStage));
        eventManager = new EventManager(mockMainStage);

        mockMainStage.scoreManager = scoreManager;
        mockMainStage.eventManager = eventManager;

        mockMain = mock(main.class);
        mockMain.mainStage = mockMainStage;

        obtainedAchievementsReset();
    }

    @BeforeEach
    void setUp() {
        achievementManager = new AchievementManager("achievements.csv", mockMain);
    }

    @AfterEach
    void reset() {
        landPlotsInitialise();
        obtainedAchievementsReset();
    }

    @Test
    void prestigiousTest() {
        assertFalse(achievementManager.getAchievements().get(0).isObtained());
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);
        when(scoreManager.getSatisfaction()).thenReturn(75);

        achievementManager.CheckContinuousAchievements();

        assertTrue(achievementManager.getAchievements().get(0).isObtained());
    }

    @Test
    void clubberTest() {
        assertFalse(achievementManager.getAchievements().get(1).isObtained());
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);
        for (int i = 0; i < 3; i++) {
            main.selectedBuilding = 4;
            simulateTouchEvent(landPlots[i]);
        }

        achievementManager.CheckContinuousAchievements();
        assertTrue(achievementManager.getAchievements().get(1).isObtained());
    }

    @Test
    void builderTest() {
        assertFalse(achievementManager.getAchievements().get(2).isObtained());
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);
        for (int i = 0; i < landPlots.length - 1; i++) {
            main.selectedBuilding = 3;
            simulateTouchEvent(landPlots[i]);
            achievementManager.CheckContinuousAchievements();
            assertFalse(achievementManager.getAchievements().get(2).isObtained());
        }

        main.selectedBuilding = 3;
        simulateTouchEvent(landPlots[8]);

        achievementManager.CheckContinuousAchievements();
        assertTrue(achievementManager.getAchievements().get(2).isObtained());
    }

    @Test
    void lecturerTest() {
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);

        for (int i = 1; i < 4; i++) {
            achievementManager.checkEndAchievements();
            assertFalse(achievementManager.getAchievements().get(3).isObtained());
            achievementManager.setGamesFinished(i);
        }

        achievementManager.checkEndAchievements();
        assertTrue(achievementManager.getAchievements().get(3).isObtained());
    }

    @Test
    void enviromentalistTest() {
        main.selectedBuilding = 4;
        simulateTouchEvent(landPlots[0]);
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);
        achievementManager.checkEndAchievements();
        assertFalse(achievementManager.getAchievements().get(4).isObtained());

        landPlotsInitialise();
        when(mockMainStage.getLandPlots()).thenReturn(landPlots);
        achievementManager.checkEndAchievements();
        assertTrue(achievementManager.getAchievements().get(4).isObtained());
    }

    private void obtainedAchievementsReset() {
        File file = new File("obtainedAchievements.csv");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try{
            PrintWriter out = new PrintWriter(new FileWriter("obtainedAchievements.csv", false));

            out.print("");

            out.close();
        }
        catch (IOException e){
            System.err.println("Error writing leaderboard");
        }
    }

    private void landPlotsInitialise() {
        landPlots = new LandPlot[9];
        landPlots[0] = new LandPlot(2, 360 + (int)(8.5 * 40), 2 * 40, 80, 80);
        landPlots[1] = new LandPlot(2, 360 + (int)(3.5 * 40), 2 * 40, 80, 80);
        landPlots[2] = new LandPlot(3, 360 + 3 * 40, (int)(5.5 * 40), 120, 120);
        landPlots[3] = new LandPlot(3, 360 + 3 * 40, (int)(8.6 * 40), 120, 120);
        landPlots[4] = new LandPlot(3, 360 + (int)(12.5 * 40), (13 * 40), 120, 120);
        landPlots[5] = new LandPlot(3, 360 + (int)(16.7 * 40), (11 * 40), 120, 120);
        landPlots[6] = new LandPlot(3, 360 + (int)(20.7 * 40), 11 * 40, 120, 120);
        landPlots[7] = new LandPlot(3, 360 + (int)(24.2 * 40), 11 * 40, 120, 120);
        landPlots[8] = new LandPlot(2, 360 + 24 * 40, (int)(6.5 * 40), 80, 80);
    }


    private void simulateTouchEvent(LandPlot landPlot) {
        InputEvent inputEvent = new InputEvent();
        inputEvent.setType(InputEvent.Type.touchUp);

        ClickListener listener = (ClickListener) landPlot.getButton().getListeners().get(1);
        listener.clicked(inputEvent, 0, 0);
    }
}
