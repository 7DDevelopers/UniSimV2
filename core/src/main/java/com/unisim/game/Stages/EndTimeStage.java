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

public class EndTimeStage extends Stage {
    private final main game;

    public EndTimeStage(main game) {
        this.game = game;
        initialize();
    }

    public void initialize(){        // Sets up the stage at the end of the game.
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
        Table endTimeTable = new Table();
        endTimeTable.add(endTimeText).width(700f). height(200f);
        endTimeTable.row();
        endTimeTable.add(quitButtonTS);
        endTimeTable.setPosition(Gdx.graphics.getWidth() / 2f - endTimeTable.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - endTimeTable.getHeight() / 2);
        this.addActor(endTimeTable);}
}
