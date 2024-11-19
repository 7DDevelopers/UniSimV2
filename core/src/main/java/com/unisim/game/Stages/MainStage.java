package com.unisim.game.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.unisim.game.Leaderboard.LeaderboardManager;
import com.unisim.game.*;

public class MainStage extends Stage {
    private final main game;

    /**Contains the score and timer displays, and the pause and tutorial buttons.*/
    Table miscTable;
    /**The main table for {@code mainStage}, contains {@code buildingsTable} and {@code miscTable}.*/
    Table buttonsTable;
    /**Contains the buttons for selection/deselection of which building to place. Also contains the labels.*/
    Table buildingsTable;

    /**Contains the {@link LandPlot} for building placement on the map.*/
    LandPlot[] landPlots;
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

    /**Handles the display of {@code time}.*/
    public Label timeTextLabel;

    // Attributes for the display and behaviour of the score and timer.

    /**The score achieved by the player.*/
    int score = 3;
    /**Handles the display of {@code score}.*/
    Label scoreTextLabel;
    float time;







    /**The file paths for each different type of building.*/
    String[] filePaths;
    public MainStage(main game) {
        this.game = game;
        initialize();
    }

    public void updateTime(float time) {
        this.time = time;
    }


    public void initialize(){

        // Sets up mainStage


        this.addActor(new GameMap());

        // Sets up labels for buildings and counter columns
        Label buildingsTitle = new Label("Buildings", game.skin);
        buildingsTitle.setFontScale(1.5f);
        Label countersTitle = new Label("Counter", game.skin);
        countersTitle.setFontScale(1.5f);

        // Creates filepath array using buildingTypes.
        filePaths = new String[5];
        for (int i = 0; i < filePaths.length; i++) {
            filePaths[i] = game.getBuildingTypes()[i].getName();
        }

        // Sets up buttons for building selection/deselection.
        buildingButtons = new ImageButton[5];
        for (int i = 0; i < filePaths.length; i++) {
            buildingButtons[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(filePaths[i]))));
        }

        // Sets up counters describing the number of each building type placed.
        buildingCounters = new Label[5];
        for (int i = 0; i < filePaths.length; i++) {
            buildingCounters[i] = new Label("0", game.skin);
        }

        // Sets up labels for each building type.
        buildingLabels = new Label[5];
        for (int i = 0; i < filePaths.length; i++) {
            String buildingLabel = filePaths[i];
            buildingLabel = buildingLabel.substring(0, buildingLabel.length()-4);
            buildingLabels[i] = new Label(buildingLabel, game.skin);
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
                    if (game.selectedBuilding < 0) {
                        buildingCounters[finalI].setText(String.valueOf(Integer.parseInt(buildingCounters[finalI].getText().toString()) + 1));
                        main.selectedBuilding = finalI;
                    }
                    // The player can deselect the currently selected building.
                    else if (game.selectedBuilding == finalI) {
                        buildingCounters[finalI].setText(String.valueOf(Integer.parseInt(buildingCounters[finalI].getText().toString()) + -1));
                        main.selectedBuilding = -1;

                    }
                }
            });
        }

        // Sets up, formats and adds score to mainStage.
        Table scoreTable = new Table();
        Label scoreTitleLabel = new Label("Score", game.skin);
        scoreTitleLabel.setFontScale(1.5f);
        scoreTextLabel = new Label(String.valueOf(score), game.skin);
        scoreTable.add(scoreTitleLabel);
        scoreTable.row();
        scoreTable.add(scoreTextLabel);

        // Sets up, formats and adss timer to mainStage.
        Table timerTable = new Table();
        Label timeTitleLabel = new Label("Time Left", game.skin);
        timeTextLabel = new Label(String.valueOf(game.getTime()), game.skin);
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
                game.setSceneId(2);
            }
        });
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Moves player to tutorialStage.
                game.setSceneId(3);
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
        this.addActor(buttonsTable);

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
            this.addActor(landPlot.getImage());
            this.addActor(landPlot.getButton());
            this.addActor(landPlot);
        }

        //Gdx.input.setInputProcessor(menuStage);
    }
}
