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

public class PauseStage extends Stage {
    private final main game;

    /**Takes the player to {@code mainStage} from {@code pauseStage}.*/
    ImageButton playButtonPM;

    public PauseStage(main game) {
        this.game = game;
        initialize();
    }

    public void initialize(){
        // Sets up pauseStage.



        // Sets up the "quit" button on pauseStage allowing the user to exit the application.
        ImageButton quitButtonPM = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/Quit.png")))));
        quitButtonPM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        // Sets up the "resume" button on pauseStage allowing user to continue playing the game.
        playButtonPM = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/ResumeButton.png")))));
        playButtonPM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSceneId(1);
            }
        });
        // Formats and adds buttons and text to pauseStage.
        playButtonPM.setPosition(Gdx.graphics.getWidth() / 2f - playButtonPM.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - playButtonPM.getHeight() / 2);
        Image pauseMenuText = new Image(new Texture(Gdx.files.internal("text/PausedText.png")));
        Table pauseMenuTable = new Table();
        pauseMenuTable.add(pauseMenuText).width(700f).height(300f);
        pauseMenuTable.row();
        pauseMenuTable.add(playButtonPM);
        pauseMenuTable.row();
        pauseMenuTable.add(quitButtonPM);
        pauseMenuTable.setPosition(Gdx.graphics.getWidth() / 2f - pauseMenuTable.getWidth() / 2,
            Gdx.graphics.getHeight()/2f - pauseMenuTable.getHeight() / 2);
        this.addActor(pauseMenuTable);

    }
}
