package com.unisim.game.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.unisim.game.Achievements.Achievement;
import com.unisim.game.main;

public class AchievementsStage extends Stage {
    private final main game;

    /**Contains the table for the achievements.*/
    Table achievementsTable;
    /**Takes the player to {@code menuStage} from {@code achievementsStage}.*/
    ImageButton backAchievements;

    public AchievementsStage(main game) {
        this.game = game;
        initialize();
    }

    public void initialize(){
        // Sets up achievements stage
        backAchievements = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/BackButton.png")))));
        backAchievements.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSceneId(0);
            }
        });
        Table achievementsButtonTable = new Table();
        achievementsButtonTable.add(backAchievements);
        achievementsButtonTable.top().left();
        achievementsButtonTable.setFillParent(true);

        achievementsTable = new Table(game.skin);
        Label nameHeader = new Label("Achievement", game.skin);
        nameHeader.setColor(Color.WHITE);
        Label scoreHeader = new Label("Description", game.skin);
        achievementsTable.add(nameHeader).padRight(20);
        achievementsTable.add(scoreHeader).row();

        achievementsTable.setPosition((Gdx.graphics.getWidth() - achievementsTable.getWidth()) / 2,
            (Gdx.graphics.getHeight() - achievementsTable.getHeight()) / 2);

        for (int i = 0; i < game.achievementManager.getAchievements().size() && i < 5; i++) {
            Achievement achievement = game.achievementManager.getAchievements().get(i);
            //Adds the achievement image
            Stack stack = new Stack();

            stack.add(achievement.getThumbnail());
            if(!achievement.isObtained()){
                stack.add(new Image(new Texture("ui/AchievementImages/AchievementBackgroundGrey.png")));
            }

            achievementsTable.add(stack).width(120).height(120).padRight(20);

            //Adds the achievement text box
            stack = new Stack();
            stack.add(new Image(new Texture("ui/AchievementImages/AchievementTextBackground.png")));

            Label label = new Label(achievement.getDescription(), game.skin);
            label.setAlignment(1);
            label.setFontScale(1);
            stack.add(label);

            if(!achievement.isObtained()){
                stack.add(new Image(new Texture("ui/AchievementImages/AchievementTextBackgroundGrey.png")));
            }

            achievementsTable.add(stack).width(480).height(120).row();
        }

        this.addActor(achievementsTable);
        this.addActor(achievementsButtonTable);


    }
}
