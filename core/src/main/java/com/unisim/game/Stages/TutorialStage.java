package com.unisim.game.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.unisim.game.main;

public class TutorialStage extends Stage {
    private final main game;

    // Buttons
    private ImageButton backButtonTM;
    private ImageButton eventsHelpButton;

    // Image displayed for tutorial
    private Image tutorialMenuText;

    // State tracking
    private boolean showingHowToPlay2 = false;

    public TutorialStage(main game) {
        this.game = game;
        initialize();
    }

    /** Sets up the tutorial stage */
    public void initialize() {
        // Sets up "Back" button on tutorialStage
        backButtonTM = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/BackNew.png")))));
        backButtonTM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (showingHowToPlay2) {
                    tutorialMenuText.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("text/HowToPlay.png")))));
                    eventsHelpButton.setVisible(true);
                    showingHowToPlay2 = false;
                } else {
                    game.setSceneId(1);
                }
            }
        });

        // Sets up "Events" button on tutorialStage
        eventsHelpButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/EventsNew.png")))));
        eventsHelpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tutorialMenuText.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("text/HowToPlay2.png")))));
                eventsHelpButton.setVisible(false);
                showingHowToPlay2 = true;
            }
        });

        // Sets up tutorial text image
        tutorialMenuText = new Image(new Texture(Gdx.files.internal("text/HowToPlay.png")));

        // Format and add buttons and text to the tutorial stage
        Table tutorialMenuTable = new Table();
        tutorialMenuTable.add(tutorialMenuText).width(1200f).height(700f);
        tutorialMenuTable.row();

        // Add buttons in a sub-table for alignment
        Table buttonTable = new Table();
        buttonTable.add(backButtonTM).pad(10f);
        buttonTable.add(eventsHelpButton).pad(10f);

        tutorialMenuTable.add(buttonTable);

        // Center the table on the screen
        tutorialMenuTable.setPosition(
            Gdx.graphics.getWidth() / 2f - tutorialMenuTable.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - tutorialMenuTable.getHeight() / 2
        );

        this.addActor(tutorialMenuTable);
    }
}
