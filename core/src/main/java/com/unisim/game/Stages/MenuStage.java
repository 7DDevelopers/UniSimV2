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

public class MenuStage extends Stage {
    private final main game;

    /**The button on the main menu that takes the player to the game stage.*/
    ImageButton playImgButton;

    /**The button on the main menu that takes the player to the leaderboard.*/
    ImageButton leaderboardImgButton;

    /**The button on the main menu that takes the player to the achievements.*/
    ImageButton achievementsImgButton;

    public MenuStage(main game) {
        this.game = game;
        initialize();
    }

    private void initialize(){

        // Sets up "play" button on menuStage.
        playImgButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/PlayButtonNew.png")))));
        playImgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSceneId(1);
            }
        });

        // Sets up "leaderboard" button on menuStage.

        leaderboardImgButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/LeaderboardNew.png")))));
        leaderboardImgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSceneId(5);
            }
        });

        // Sets up "acheivements" button on menuStage.

        achievementsImgButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/AchievementsNew.png")))));
        achievementsImgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSceneId(6);
            }
        });

        // Sets up text on menuStage.
        Image mainMenuText = new Image(new Texture(Gdx.files.internal("text/HomeText.png")));

        // Formats buttons and text on menuStage.
        Table mainMenuTable = new Table();
        mainMenuTable.add(mainMenuText).width(700f).height(300f);
        mainMenuTable.row();
        mainMenuTable.add(playImgButton);
        mainMenuTable.row();
        mainMenuTable.add(leaderboardImgButton);
        mainMenuTable.row();
        mainMenuTable.add(achievementsImgButton);
        mainMenuTable.setPosition(Gdx.graphics.getWidth() / 2f - mainMenuTable.getWidth() / 2,
            Gdx.graphics.getHeight()/2f - mainMenuTable.getHeight() / 2);

        this.addActor(mainMenuTable);

    }
}
