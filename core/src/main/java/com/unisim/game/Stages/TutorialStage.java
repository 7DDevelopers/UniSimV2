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


    /**Takes the player to {@code mainStage} from {@code tutorialStage}.*/
    ImageButton backButtonTM;

    public TutorialStage(main game) {
        this.game = game;
        initialize();
    }

    public void initialize(){

        // Sets up "back" button on tutorialStage
            backButtonTM = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/BackButton.png")))));
        backButtonTM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSceneId(1);
            }
        });

        // Formats and adds button and text to tutorialStage.
        backButtonTM.setPosition(Gdx.graphics.getWidth() / 2f - backButtonTM.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - backButtonTM.getHeight() / 2);
        Image tutorialMenuText = new Image(new Texture(Gdx.files.internal("text/HowToPlay.png")));
        Table tutorialMenuTable = new Table();
        tutorialMenuTable.add(tutorialMenuText).width(1200f).height(700f);
        tutorialMenuTable.row();
        tutorialMenuTable.add(backButtonTM);
        tutorialMenuTable.setPosition(Gdx.graphics.getWidth() / 2f - tutorialMenuTable.getWidth() / 2,
            Gdx.graphics.getHeight()/2f - tutorialMenuTable.getHeight() / 2);
        this.addActor(tutorialMenuTable);
    }
}
