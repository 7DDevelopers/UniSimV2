package com.unisim.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.unisim.game.Leaderboard.Entry;
import com.unisim.game.Leaderboard.LeaderboardManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter implements InputProcessor {

    public static OrthographicCamera camera;

    /**Represents the current scene.*/
    int sceneId = 0;

    // Stages that represent different parts of the game

    /**The stage for the menu displayed at the start of the game.*/
    Stage menuStage;
    /**The stage where the player plays the game.*/
    Stage mainStage;
    /**The stage for when the game is paused.*/
    Stage pauseStage;
    /**The stage to show the player the tutorial.*/
    Stage tutorialStage;
    /**The stage that shows when the timer is up and the game is finished.*/
    Stage endTimeStage;
    /**The stage that shows when the leaderboard is viewed.*/
    Stage leaderboardStage;

    Skin skin;

    /**The button on the main menu that takes the player to the game stage.*/
    ImageButton playImgButton;

    /**The button on the main menu that takes the player to the leaderboard.*/
    ImageButton leaderboardImgButton;

    // Labels and buttons on the main stage.

    /**The labels that show how many instances of each building have been placed.*/
    Label[] buildingCounters;
    /**The labels for the names of the buildings.*/
    Label[] buildingLabels;
    /**The buttons that allow the user to select/deselect a building to place.*/
    ImageButton[] buildingButtons;

    // Buttons on the main stage for navigation.

    /**Takes the player to {@code pauseStage} from {@code mainStage}.*/
    ImageButton pauseButton;
    /**Takes the player to {@code tutorialStage} from {@code mainStage}.*/
    ImageButton tutorialButton;

    // Buttons on the pause and tutorial stages for navigation.

    /**Takes the player to {@code mainStage} from {@code pauseStage}.*/
    ImageButton playButtonPM;
    /**Takes the player to {@code mainStage} from {@code tutorialStage}.*/
    ImageButton backButtonTM;
    /**Takes the player to {@code menuStage} from {@code leaderboardStage}.*/
    ImageButton backLeaderboard;

    // Attributes for the display and behaviour of the score and timer.

    /**The score achieved by the player.*/
    int score;
    /**Handles the display of {@code score}.*/
    Label scoreTextLabel;

    /**The time left for game play.*/
    float time = 10f;

    /**Whether the score for the current game has been saved.*/
    boolean scoreSaved = false;

    /**Handles the display of {@code time}.*/
    Label timeTextLabel;

    // Attributes for the background colour of the game.

    /**The background colour used for all stages.*/
    Color mainColour;
    /**Controls the background colour given by {@code mainColour}.*/
    ShapeRenderer backgroundColourSR;

    // Main tables for the layout of mainStage.

    /**Contains the buttons for selection/deselection of which building to place. Also contains the labels.*/
    Table buildingsTable;
    /**Contains the table for the leaderboard.*/
    Table leaderboardTable;
    /**Contains the score and timer displays, and the pause and tutorial buttons.*/
    Table miscTable;
    /**The main table for {@code mainStage}, contains {@code buildingsTable} and {@code miscTable}.*/
    Table buttonsTable;

    /**Contains the {@link LandPlot} for building placement on the map.*/
    LandPlot[] landPlots;

    /**The leaderboard manager used to access and modify the leaderboard file.*/
    LeaderboardManager leaderboardManager;

    /**The default types of {@link Building} that are deep copied when a {@link Building} is associated with a specific
     * {@link LandPlot}.*/
    static Building[] buildingTypes = new Building[]{
        new Building("Accommodation.png", 3),
        new Building("LectureHall.png", 3 ),
        new Building("FoodHall.png", 3),
        new Building("Gym.png", 2),
        new Building("Club.png", 2)
    };

    /**Represents which {@link Building} from {@code buildingTypes} has been selected for placement. -1 by default when
     * nothing is selected.*/
    public static int selectedBuilding = -1;

    /**The file paths for each different type of building.*/
    String[] filePaths;

    @Override
    public void create() {

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

        // Sets up menuStage and buttons on it.

        menuStage = new Stage();

        // Sets up "play" button on menuStage.
        playImgButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/PlayButton.png")))));
        playImgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneId = 1;
            }
        });

        // Sets up "leaderboard" button on menuStage.



        leaderboardImgButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/Leaderboard.png")))));
        leaderboardImgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneId = 5;
            }
        });

        // Sets up leaderboard manager
        leaderboardManager = new LeaderboardManager("leaderboard.csv");


        // Sets up text on menuStage.
        Image mainMenuText = new Image(new Texture(Gdx.files.internal("text/HomeText.png")));

        // Formats buttons and text on menuStage.
        Table mainMenuTable = new Table();
        mainMenuTable.add(mainMenuText).width(700f).height(300f);
        mainMenuTable.row();
        mainMenuTable.add(playImgButton);
        mainMenuTable.row();
        mainMenuTable.add(leaderboardImgButton);
        mainMenuTable.setPosition(Gdx.graphics.getWidth() / 2f - mainMenuTable.getWidth() / 2,
                                  Gdx.graphics.getHeight()/2f - mainMenuTable.getHeight() / 2);

        menuStage.addActor(mainMenuTable);

        // Sets up skin for labels on mainStage
        skin = new Skin(Gdx.files.internal("default_skin/uiskin.json"));

        // Sets up leaderboard stage
        backLeaderboard = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/BackButton.png")))));
        backLeaderboard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneId = 0;
            }
        });
        // Formats buttons and text on menuStage.
        Table leaderboardButtonTable = new Table();
        leaderboardButtonTable.add(backLeaderboard);
        leaderboardButtonTable.top().left();
        leaderboardButtonTable.setFillParent(true);

        leaderboardStage = new Stage();
        leaderboardTable = new Table(skin);
        Label nameHeader = new Label("Name", skin);
        nameHeader.setColor(Color.WHITE);
        Label scoreHeader = new Label("Score", skin);
        leaderboardTable.add(nameHeader).padRight(20);
        leaderboardTable.add(scoreHeader).row();

        leaderboardTable.setPosition((Gdx.graphics.getWidth() - leaderboardTable.getWidth()) / 2,
            (Gdx.graphics.getHeight() - leaderboardTable.getHeight()) / 2);
        for(Entry entry : leaderboardManager.getLeaderboard()){
            leaderboardTable.add(entry.getName()).padRight((20));
            leaderboardTable.add(Integer.toString((entry.getScore()))).row();
        }
        leaderboardStage.addActor(leaderboardTable);
        leaderboardStage.addActor(leaderboardButtonTable);

        // Sets up mainStage

        mainStage = new Stage();
        mainStage.addActor(new GameMap());

        // Sets up labels for buildings and counter columns
        Label buildingsTitle = new Label("Buildings", skin);
        buildingsTitle.setFontScale(1.5f);
        Label countersTitle = new Label("Counter", skin);
        countersTitle.setFontScale(1.5f);

        // Creates filepath array using buildingTypes.
        filePaths = new String[5];
        for (int i = 0; i < filePaths.length; i++) {
            filePaths[i] = buildingTypes[i].getName();
        }

        // Sets up buttons for building selection/deselection.
        buildingButtons = new ImageButton[5];
        for (int i = 0; i < filePaths.length; i++) {
            buildingButtons[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(filePaths[i]))));
        }

        // Sets up counters describing the number of each building type placed.
        buildingCounters = new Label[5];
        for (int i = 0; i < filePaths.length; i++) {
            buildingCounters[i] = new Label("0", skin);
        }

        // Sets up labels for each building type.
        buildingLabels = new Label[5];
        for (int i = 0; i < filePaths.length; i++) {
            String buildingLabel = filePaths[i];
            buildingLabel = buildingLabel.substring(0, buildingLabel.length()-4);
            buildingLabels[i] = new Label(buildingLabel, skin);
        }

        // Helps to format mainStage.
        buildingsTable = new Table();
        buildingsTable.add(buildingsTitle);
        buildingsTable.add(countersTitle);

        // Formats buttons and counters on mainStage.
        int cellD = 110;
        for (int i = 0; i < buildingCounters.length; i++) {
            buildingsTable.row();
            buildingsTable.add(buildingButtons[i]).width(cellD).height(cellD);
            buildingsTable.add(buildingCounters[i]);
            buildingsTable.row();
            buildingsTable.add(buildingLabels[i]).width(cellD).height(20);
        }

        // Adds event listeners to buttons.
        for (int i = 0; i < filePaths.length; i++) {
            int finalI = i;
            buildingButtons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // If there is no currently selected building, the player can select a building.
                    if (selectedBuilding < 0) {
                        buildingCounters[finalI].setText(String.valueOf(Integer.parseInt(buildingCounters[finalI].getText().toString()) + 1));
                        main.selectedBuilding = finalI;
                    }
                    // The player can deselect the currently selected building.
                    else if (selectedBuilding == finalI) {
                        buildingCounters[finalI].setText(String.valueOf(Integer.parseInt(buildingCounters[finalI].getText().toString()) + -1));
                        main.selectedBuilding = -1;

                    }
                }
            });
        }

        // Sets up, formats and adds score to mainStage.
        Table scoreTable = new Table();
        Label scoreTitleLabel = new Label("Score", skin);
        scoreTitleLabel.setFontScale(1.5f);
        scoreTextLabel = new Label(String.valueOf(score), skin);
        scoreTable.add(scoreTitleLabel);
        scoreTable.row();
        scoreTable.add(scoreTextLabel);

        // Sets up, formats and adss timer to mainStage.
        Table timerTable = new Table();
        Label timeTitleLabel = new Label("Time Left", skin);
        timeTextLabel = new Label(String.valueOf(time), skin);
        timeTitleLabel.setFontScale(1.5f);
        timerTable.add(timeTitleLabel);
        timerTable.row();
        timerTable.add(timeTextLabel);

        // Sets up pause and tutorial buttons.
        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/PauseSquareButton.png")))));
        tutorialButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/QuestionMark.png")))));
        // Sets up listeners for the buttons, so the player can navigate between stages.
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Moves player to pauseStage.
                sceneId = 2;
            }
        });
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Moves player to tutorialStage.
                sceneId = 3;
            }
        });

        // Formats mainStage

        miscTable = new Table();
        miscTable.add(pauseButton);
        miscTable.add(tutorialButton);
        miscTable.row();
        miscTable.add(scoreTable);
        miscTable.add(timerTable);

        buttonsTable = new Table();
        buttonsTable.add(miscTable);
        buttonsTable.row();
        buttonsTable.add(buildingsTable);

        buttonsTable.setPosition(150, Gdx.graphics.getHeight() / 2f);
        mainStage.addActor(buttonsTable);

        // Sets up the LandPlots for building placement.

        int pxpt = 40; // The number of pixels per tile on the tilemap.
        int size2 = pxpt * 2;
        int size3 = pxpt * 3;
        int bw = 360; // The gap before the tilemap that the building buttons sit in.

        // Sets up LandPlots correct placements regarding the tilemap.
        landPlots = new LandPlot[9];
        landPlots[0] = new LandPlot(2, bw + (int)(8.5 * pxpt), 2 * pxpt, size2, size2);
        landPlots[1] = new LandPlot(2, bw + (int)(3.5 * pxpt), 2 * pxpt, size2, size2);
        landPlots[2] = new LandPlot(3, bw + 3 * pxpt, (int)(5.5 * pxpt), size3, size3);
        landPlots[3] = new LandPlot(3, bw + 3 * pxpt, (int)(8.6 * pxpt), size3, size3);
        landPlots[4] = new LandPlot(3, bw + (int)(12.5 * pxpt), (13 * pxpt), size3, size3);
        landPlots[5] = new LandPlot(3, bw + (int)(16.7 * pxpt), (11 * pxpt), size3, size3);
        landPlots[6] = new LandPlot(3, bw + (int)(20.7 * pxpt), 11 * pxpt, size3, size3);
        landPlots[7] = new LandPlot(3, bw + (int)(24.2 * pxpt), 11 * pxpt, size3, size3);
        landPlots[8] = new LandPlot(2, bw + 24 * pxpt, (int)(6.5 * pxpt), size2, size2);

        // Adds the LandPlot components to mainStage.
        for (LandPlot landPlot : landPlots) {
            mainStage.addActor(landPlot.image);
            mainStage.addActor(landPlot.button);
            mainStage.addActor(landPlot);
        }

        Gdx.input.setInputProcessor(menuStage);

        // Sets up pauseStage.

        pauseStage = new Stage();

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
                sceneId = 1;
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
        pauseStage.addActor(pauseMenuTable);

        // Sets up tutorialStage

        tutorialStage = new Stage();

        // Sets up "back" button on tutorialStage
        backButtonTM = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/BackButton.png")))));
        backButtonTM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneId = 1;
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
        tutorialStage.addActor(tutorialMenuTable);

        // Sets up the stage at the end of the game.

        endTimeStage = new Stage();

        // Sets up, formats and adds text and "quit" button to tutorialStage
        Image endTimeText = new Image(new Texture(Gdx.files.internal("text/TimeUpText.png")));
        ImageButton quitButtonTS = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("buttons/Quit.png"))));
        quitButtonTS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });
        Table endTimeTable = new Table();
        endTimeTable.add(endTimeText).width(700f). height(200f);
        endTimeTable.row();
        endTimeTable.add(quitButtonTS);
        endTimeTable.setPosition(Gdx.graphics.getWidth() / 2f - endTimeTable.getWidth() / 2,
                                 Gdx.graphics.getHeight() / 2f - endTimeTable.getHeight() / 2);
        endTimeStage.addActor(endTimeTable);

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

            // Update the timer display
            timeTextLabel.setText(String.format("%d:%02d", minutes, seconds));
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
        }
    }

    public void saveScore(){
        leaderboardManager.addEntry("Luke", score);
        leaderboardManager.writeLeaderBoard();
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
