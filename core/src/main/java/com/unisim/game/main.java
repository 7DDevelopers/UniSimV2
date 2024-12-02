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
    int sceneId = 0;

    // Stages that represent different parts of the game
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

    float timer_t = 1;
    float timer = timer_t;

    /**The time left for game play.*/
    float time = 10f;
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

        menuStage = new MenuStage(this);
        mainStage = new MainStage(this);
        pauseStage = new PauseStage(this);
        tutorialStage = new TutorialStage(this);
        leaderboardStage = new LeaderboardStage(this);
        achievementStage = new AchievementsStage(this);
        endTimeStage = new EndTimeStage(this);
    }

    public boolean isScoreSaved() {
        return scoreSaved;
    }
    @Override
    public void render() {
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
        }

        // Render the correct scene
        switch (sceneId) {
            case 0:
                Gdx.input.setInputProcessor(menuStage);
                menuStage.act(Gdx.graphics.getDeltaTime());
                menuStage.draw();
                break;
            case 1:
                Gdx.input.setInputProcessor(mainStage);
                mainStage.act(Gdx.graphics.getDeltaTime());
                mainStage.draw();
                mainStage.updateTime(time);
                if(timer > 0) {
                    timer -= Gdx.graphics.getDeltaTime();;
                } else {
                    mainStage.oneSecondTimer();
                    timer = timer_t;
                }
                if (time <= 0){
                    sceneId = 4;
                }
                break;
            case 2:
                Gdx.input.setInputProcessor(pauseStage);
                pauseStage.act(Gdx.graphics.getDeltaTime());
                pauseStage.draw();
                break;
            case 3:
                Gdx.input.setInputProcessor(tutorialStage);
                tutorialStage.act(Gdx.graphics.getDeltaTime());
                tutorialStage.draw();
                break;
            case 4:
                Gdx.input.setInputProcessor(endTimeStage);
                endTimeStage.act(Gdx.graphics.getDeltaTime());
                endTimeStage.draw();
                if (scoreSaved == false){
                    saveScore();
                    System.out.println("saving");
                    scoreSaved = true;
                }
                break;
            case 5:
                Gdx.input.setInputProcessor(leaderboardStage);
                leaderboardStage.act(Gdx.graphics.getDeltaTime());
                leaderboardStage.draw();
                break;
            case 6:
                achievementStage.initialize();
                Gdx.input.setInputProcessor(achievementStage);
                achievementStage.act(Gdx.graphics.getDeltaTime());
                achievementStage.draw();
                break;
        }
    }

    public void saveScore(){
        leaderboardManager.addEntry("Luke", mainStage.getScore());
        leaderboardManager.writeLeaderBoard();
    }

    public void startNewGame(){
        time = 3f;
        achievementManager.checkEndAchievements();
        mainStage = new MainStage(this);
    }

    public static Building[] getBuildingTypes() {
        return buildingTypes;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public Skin getSkin() {
        return skin;
    }

    public int getSceneId() {
        return sceneId;
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
