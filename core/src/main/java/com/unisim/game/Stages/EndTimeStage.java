package com.unisim.game.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.unisim.game.main;

public class EndTimeStage extends Stage {
    private final main game;

    public EndTimeStage(main game) {
        this.game = game;
        initialize();
    }

    public void initialize() {
        // Sets up the stage at the end of the game.
        Image endTimeText = new Image(new Texture(Gdx.files.internal("text/TimeUpText.png")));
        ImageButton quitButtonTS = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("buttons/Quit.png"))));
        quitButtonTS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.achievementManager.setGamesFinished(game.achievementManager.getGamesFinished()+1);
                game.startNewGame();
                game.setSceneId(0);
            }
        });

        // TextField for input
        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // Replace with the path to your skin JSON
        TextField saveTextField = new TextField("", skin);
        saveTextField.setMessageText("Enter save name...");
        saveTextField.setAlignment(Align.center);

        // Save Button
        ImageButton saveButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("buttons/Save.png"))));
        saveButton.setDisabled(true); // Initially disabled

        saveTextField.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                // Enable save button if text is entered
                saveButton.setDisabled(saveTextField.getText().trim().isEmpty());
                return super.keyTyped(event, character);
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!saveButton.isDisabled()) {
                    String saveName = saveTextField.getText().trim();
                    saveGame(saveName); // Call your save method with the entered name
                    saveTextField.remove();
                    saveButton.remove();
                }
            }
        });

        // Table Layout
        Table endTimeTable = new Table();
        endTimeTable.add(endTimeText).width(700f).height(200f).row();
        endTimeTable.add(saveTextField).width(400f).height(50f).padTop(20f).row();
        endTimeTable.add(saveButton).padTop(10f).row();
        endTimeTable.add(quitButtonTS).padTop(10f);
        endTimeTable.setPosition(Gdx.graphics.getWidth() / 2f - endTimeTable.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - endTimeTable.getHeight() / 2);

        this.addActor(endTimeTable);
    }

    private void saveGame(String saveName) {
        game.saveScore(saveName);
    }
}
