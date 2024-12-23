package com.unisim.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.unisim.game.Achievements.AchievementManager;
import com.unisim.game.Leaderboard.LeaderboardManager;
import com.unisim.game.Stages.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter implements InputProcessor {

    public static OrthographicCamera camera;

    public float getTime() {
        return time;
    }

    /**Represents the current scene.*/
    public int sceneId = 0;

    public Skin skin;
    /**The stage for the menu displayed at the start of the game.*/
    MenuStage menuStage;
    /**The stage where the player plays the game.*/
    public MainStage mainStage;
    /**The stage for when the game is paused.*/
    PauseStage pauseStage;
    /**The stage to show the player the tutorial.*/
    TutorialStage tutorialStage;
    /**The stage that shows when the timer is up and the game is finished.*/
    EndTimeStage endTimeStage;
    /**The stage that shows when the leaderboard is viewed.*/
    LeaderboardStage leaderboardStage;
    /**The stage that shows when the achievements are viewed.*/
    AchievementsStage achievementStage;

    /**Represents which {@link Building} from {@code buildingTypes} has been selected for placement. -1 by default when
     * nothing is selected.*/
    public static int selectedBuilding = -1;

    /**The default types of {@link Building} that are deep copied when a {@link Building} is associated with a specific
     * {@link LandPlot}.*/
    static Building[] buildingTypes = new Building[]{
        new Building("Accommodation.png", 3, "Accommodation"),
        new Building("LectureHall.png", 3, "LectureHall"),
        new Building("FoodHall.png", 3, "FoodHall"),
        new Building("Gym.png", 2, "Gym"),
        new Building("Club.png", 2, "Club")
    };

    /**Floats used to calculate a one-second timer*/
    float timer_t = 1;
    float timer = timer_t;

    /**The time left for game play.*/
    float time = 300f;
    /**Whether the score for the current game has been saved.*/
    boolean scoreSaved = false;
    /**The background colour used for all stages.*/
    Color mainColour;
    /**Controls the background colour given by {@code mainColour}.*/
    ShapeRenderer backgroundColourSR;

    /**The leaderboard manager used to access and modify the leaderboard file.*/
    public LeaderboardManager leaderboardManager;

    /**The achievement manager used to manage achievements.*/
    public AchievementManager achievementManager;

    @Override
    public void create() {
        // Sets up skin for labels on mainStage
        skin = new Skin(Gdx.files.internal("default_skin/uiskin.json"));

        // Variables to change size of camera.
        float w = 1260;
        float h = 640;

        // Sets up camera.
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.translate(-300f, 0f);
        camera.update();

        // Sets up background colour for stages.
        mainColour = new Color(0f, 0.23f, 0.17f, 1f);
        backgroundColourSR = new ShapeRenderer();

        // Sets up leaderboard manager
        leaderboardManager = new LeaderboardManager("leaderboard.csv");

        //Sets up achievement manager
        achievementManager = new AchievementManager("achievements.csv", this);

        // Sets up each game stage
        menuStage = new MenuStage(this);
        mainStage = new MainStage(this);
        pauseStage = new PauseStage(this);
        tutorialStage = new TutorialStage(this);
        leaderboardStage = new LeaderboardStage(this);
        achievementStage = new AchievementsStage(this);
        endTimeStage = new EndTimeStage(this);
    }

    @Override
    public void render() {
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the timer only in the main stage (gameplay)
        if (sceneId == 1) {
            time -= Gdx.graphics.getDeltaTime();
            // Convert to minutes and seconds
            int timerValue = Math.round(time);
            int minutes = timerValue / 60;
            int seconds = timerValue % 60;

            //Update the timer display
            mainStage.timeTextLabel.setText(String.format("%d:%02d", minutes, seconds));

            //Check Achievements
            achievementManager.CheckContinuousAchievements();

            //Play animation if needed
            if (achievementManager.isPlayingAnimation()){
                mainStage.playAchievementAnimation(achievementManager.getRecentAchievement());
            }
        }

        // Render the correct scene
        switch (sceneId) {
            case 0:
                // Menu scene
                Gdx.input.setInputProcessor(menuStage);
                menuStage.act(Gdx.graphics.getDeltaTime());
                menuStage.draw();
                break;
            case 1:
                // Main game scene
                Gdx.input.setInputProcessor(mainStage);
                mainStage.act(Gdx.graphics.getDeltaTime());
                mainStage.draw();
                mainStage.updateTime(time);
                if(timer > 0) {
                    timer -= Gdx.graphics.getDeltaTime();;
                } else {
                    // Triggers a function once per second
                    mainStage.oneSecondTimer();
                    timer = timer_t;
                }
                if (time <= 0){
                    sceneId = 4;
                }
                break;
            case 2:
                // Paused game stage
                Gdx.input.setInputProcessor(pauseStage);
                pauseStage.act(Gdx.graphics.getDeltaTime());
                pauseStage.draw();
                break;
            case 3:
                // Tutorial stage
                Gdx.input.setInputProcessor(tutorialStage);
                tutorialStage.act(Gdx.graphics.getDeltaTime());
                tutorialStage.draw();
                break;
            case 4:
                // End of the game scene
                Gdx.input.setInputProcessor(endTimeStage);
                endTimeStage.act(Gdx.graphics.getDeltaTime());
                endTimeStage.draw();
                break;
            case 5:
                // Leaderboard scene
                Gdx.input.setInputProcessor(leaderboardStage);
                leaderboardStage.act(Gdx.graphics.getDeltaTime());
                leaderboardStage.draw();
                break;
            case 6:
                // Achievement scene
                achievementStage.initialize();
                Gdx.input.setInputProcessor(achievementStage);
                achievementStage.act(Gdx.graphics.getDeltaTime());
                achievementStage.draw();
                break;
        }
    }
    /**Saves the current game score to the leaderboard*/
    public void saveScore(String name){
        leaderboardManager.addEntry(name, mainStage.getScore());
        leaderboardManager.writeLeaderBoard();
    }
    /**Allows the game to reset betweek games*/
    public void startNewGame(){
        time = 300f;
        achievementManager.checkEndAchievements();
        mainStage = new MainStage(this);
    }
    /**Passes the list of possible buildings*/
    public static Building[] getBuildingTypes() {
        return buildingTypes;
    }
    /**Changes the current scene*/
    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }
    @Override
    public void resize(int width, int height) {


        // Notify all stages of the new size
        menuStage.getViewport().update(width, height, true);
        mainStage.getViewport().update(width, height, true);
        pauseStage.getViewport().update(width, height, true);
        tutorialStage.getViewport().update(width, height, true);
        endTimeStage.getViewport().update(width, height, true);
        leaderboardStage.getViewport().update(width, height, true);
        achievementStage.getViewport().update(width, height, true);


    }


    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;

    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

}
