package com.unisim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.unisim.game.Stages.MainStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


class LandPlotTest {

    LandPlot[] landPlots;

    @BeforeEach
    void setUp() {
        MainStage mainStage = mock(MainStage.class);
        Gdx.files = mock(com.badlogic.gdx.Files.class);
        MockedConstruction<Texture> mockedTexture = Mockito.mockConstruction(Texture.class);
        MockedConstruction<Skin> mockedSkin = Mockito.mockConstruction(Skin.class);

        initialise();
    }

    @Test
    void testButtonClickSetsBuilding() {
        when(main.selectedBuilding).thenReturn(0);

        Button button = landPlots[0].getButton();

        InputEvent inputEvent = new InputEvent();
        inputEvent.setType(InputEvent.Type.touchDown);
        inputEvent.setStage(landPlots[0].getStage());

        button.fire(inputEvent);

        assertTrue(landPlots[0].isOccupied());
        assertNotNull(landPlots[0].getBuildingPlaced());
    }

    @Test
    void legalPlacementTest() {

    }

    private void simulateTouchEvent(LandPlot landPlot) {
    }

    private void initialise(){
        landPlots = new LandPlot[9];
        landPlots[0] = new LandPlot(2, 360 + (int) (8.5 * 40), 2 * 40, 80, 80);
        landPlots[1] = new LandPlot(2, 360 + (int) (3.5 * 40), 2 * 40, 80, 80);
        landPlots[2] = new LandPlot(3, 360 + 3 * 40, (int) (5.5 * 40), 120, 120);
        landPlots[3] = new LandPlot(3, 360 + 3 * 40, (int) (8.6 * 40), 120, 120);
        landPlots[4] = new LandPlot(3, 360 + (int) (12.5 * 40), (13 * 40), 120, 120);
        landPlots[5] = new LandPlot(3, 360 + (int) (16.7 * 40), (11 * 40), 120, 120);
        landPlots[6] = new LandPlot(3, 360 + (int) (20.7 * 40), 11 * 40, 120, 120);
        landPlots[7] = new LandPlot(3, 360 + (int) (24.2 * 40), 11 * 40, 120, 120);
        landPlots[8] = new LandPlot(2, 360 + 24 * 40, (int) (6.5 * 40), 80, 80);

    }
}
