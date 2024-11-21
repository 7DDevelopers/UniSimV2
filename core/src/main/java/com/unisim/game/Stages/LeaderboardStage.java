package com.unisim.game.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.unisim.game.Leaderboard.Entry;
import com.unisim.game.main;

public class LeaderboardStage extends Stage {
    private final main game;

    /**Contains the table for the leaderboard.*/
    Table leaderboardTable;
    /**Takes the player to {@code menuStage} from {@code leaderboardStage}.*/
    ImageButton backLeaderboard;

    public LeaderboardStage(main game) {
        this.game = game;
        initialize();
    }

    public void initialize(){
        // Sets up leaderboard stage
        backLeaderboard = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/BackButton.png")))));
        backLeaderboard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSceneId(0);
            }
        });
        Table leaderboardButtonTable = new Table();
        leaderboardButtonTable.add(backLeaderboard);
        leaderboardButtonTable.top().left();
        leaderboardButtonTable.setFillParent(true);

        leaderboardTable = new Table(game.skin);
        Label nameHeader = new Label("Name", game.skin);
        nameHeader.setColor(Color.WHITE);
        Label scoreHeader = new Label("Score", game.skin);
        leaderboardTable.add(nameHeader).padRight(20);
        leaderboardTable.add(scoreHeader).row();

        leaderboardTable.setPosition((Gdx.graphics.getWidth() - leaderboardTable.getWidth()) / 2,
            (Gdx.graphics.getHeight() - leaderboardTable.getHeight()) / 2);

        for (int i = 0; i < game.leaderboardManager.getLeaderboard().size() && i < 5; i++) {
            Entry entry = game.leaderboardManager.getLeaderboard().get(i);
            leaderboardTable.add(entry.getName()).padRight(20);
            leaderboardTable.add(Integer.toString(entry.getScore())).row();
        }

        this.addActor(leaderboardTable);
        this.addActor(leaderboardButtonTable);


    }
}
