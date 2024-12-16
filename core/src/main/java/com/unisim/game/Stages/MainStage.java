package com.unisim.game.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.unisim.game.Achievements.Achievement;
import com.unisim.game.Events.EventManager;
import com.unisim.game.Leaderboard.LeaderboardManager;
import com.unisim.game.*;

import java.util.Random;

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
    int score=0;
    /**Handles the display of {@code score}.*/
    Label scoreTextLabel;
    float time;

    public EventManager eventManager;

    public ScoreManager scoreManager;

    public int satisfaction;

    ParticleEffect rainEffect;

    private float achievementAnimTime = 3f;
    private float animCurrentTime = 0f;
    private float xPos;
    private float animSpeed = 150;
    private Table achievementsTable;

    Table eventTable;

    public GameMap map;

    /**The file paths for each different type of building.*/
    String[] filePaths;
    public MainStage(main game) {
        this.game = game;
        initialize();
    }

    public void updateTime(float time) {
        this.time = time;
    }

    public void oneSecondTimer(){
        eventManager.eventChecker(time);
        score = scoreManager.getScore();
        satisfaction = scoreManager.getSatisfaction();
        scoreTextLabel.setText(satisfaction + "%");
        System.out.println(Math.ceil(time));
        double integerTime = Math.ceil(time);
        if(integerTime == 299 || integerTime == 199 || integerTime == 99) {
            eventManager.startFreshersWeek(time);
        }
        if(integerTime == 250 || integerTime == 150 || integerTime == 50) {
            eventManager.startExamSeason(time);
        }
        if(integerTime == 275 || integerTime == 225 || integerTime == 175|| integerTime == 125|| integerTime == 75 || integerTime == 25) {
            Random rand = new Random();
            int eventPicker = rand.nextInt(2);
            if(eventPicker == 0){
                eventManager.startStorm(time);
            }
            else if(eventPicker==1 || !(eventManager.winterEvent.isActive())){
                eventManager.startHeatwave(time);
            }
        }
        if(integerTime == 260 || integerTime == 160 || integerTime == 60) {
            eventManager.startWinter(time);
            map.winterSeasonMap();
        }
        scoreManager.updateScore();
    }


    public int getScore() {
        return score;
    }

    public void initialize(){
        //Initialize achievement popup
        xPos = Gdx.graphics.getWidth()/2;

        // Load and configure particle effect
        rainEffect = new ParticleEffect();
        rainEffect.load(Gdx.files.internal("particles/rain.p"), Gdx.files.internal("particles"));
        rainEffect.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight()); // Start from the top
        rainEffect.start();

        // Sets up mainStage
        eventManager = new EventManager(this);
        scoreManager = new ScoreManager(this);
        map = new GameMap();
        this.addActor(map);

        // Sets up labels for buildings and counter columns
        Label buildingsTitle = new Label("Buildings", game.skin);
        buildingsTitle.setFontScale(1.5f);
        Label countersTitle = new Label("Counter", game.skin);
        countersTitle.setFontScale(1.5f);

        // Creates filepath array using buildingTypes.
        filePaths = new String[5];
        for (int i = 0; i < filePaths.length; i++) {
            filePaths[i] = game.getBuildingTypes()[i].getPath();
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

        eventTable = new Table();

        Table parentTable = new Table();
        parentTable.setFillParent(false); // Allows manual positioning

        Label eventTitle = new Label("Active Events", game.skin);
        eventTitle.setFontScale(1.5f);
        eventTitle.setAlignment(Align.center); // Center-align the title

        parentTable.add(eventTitle).center().padTop(10).padBottom(10).row();

        parentTable.add(eventTable).expand().fill();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.7f); // Black background with 70% opacity
        pixmap.fill();
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        parentTable.setBackground(backgroundDrawable);
        pixmap.dispose(); // Dispose Pixmap to prevent memory leaks

        parentTable.pad(10);
        parentTable.setSize(200, 250);
        parentTable.setPosition(Gdx.graphics.getWidth() - 1150, Gdx.graphics.getHeight() - 270);

        this.addActor(parentTable);
    }

    public LandPlot[] getLandPlots() {
        return landPlots;
    }
    @Override
    public void draw() {
        super.draw();
        if (eventManager.stormEvent.isActive()){
        // Draw the rain effect
        if (rainEffect != null) {
            getBatch().begin();
            rainEffect.draw(getBatch());
            getBatch().end();

            // Stop the effect if it's finished
            if (rainEffect.isComplete()) {
                rainEffect.dispose();
                rainEffect = null;
            }
        }}
    }
    @Override
    public void act(float delta) {
        super.act(delta);

        // Update the rain effect
        if (rainEffect != null) {
            if (eventManager.stormEvent.isActive()){
            rainEffect.update(delta);}
        }

        // Update active events in the eventTable
        updateEventTable();
    }
    private void updateEventTable() {
        // Clear the table except for the title row
        eventTable.clear();

        // Get the active events from the EventManager
        java.util.List<String> activeEvents = eventManager.getActiveEvents();

        // Add each active event to the table
        for (String event : activeEvents) {
            Label eventLabel = new Label(event, game.skin);
            eventTable.add(eventLabel).left().padTop(5).padBottom(5);
            eventTable.row();
        }
    }

    public void initializeAchievementPopup(Achievement achievement){
        // Sets up achievement
        xPos = Gdx.graphics.getWidth()+150;
        animCurrentTime = 0f;

        achievementsTable = new Table(game.skin);

        achievementsTable.add(achievement.getThumbnail()).width(60).height(60);

        Stack stack = new Stack();
        stack.add(new Image(new Texture("ui/AchievementImages/AchievementTextBackground.png")));

        Label label = new Label(achievement.getDescription(), game.skin);
        label.setAlignment(1);
        label.setFontScale(1);
        stack.add(label);

        achievementsTable.add(stack).width(240).height(60);

        achievementsTable.setPosition(xPos,
            Gdx.graphics.getHeight() - 30);

        achievementsTable.setZIndex(500);

        this.addActor(achievementsTable);
    }

    public void playAchievementAnimation(Achievement achievement){
        animCurrentTime += Gdx.graphics.getDeltaTime();

        if(animCurrentTime < achievementAnimTime-1){//-
            xPos -= Gdx.graphics.getDeltaTime() * animSpeed;
        } else if (animCurrentTime >=achievementAnimTime-1 && animCurrentTime <= achievementAnimTime) {
            xPos = xPos;
        }else{//+
            xPos += Gdx.graphics.getDeltaTime() * animSpeed;
        }

        achievementsTable.setPosition(xPos,
            Gdx.graphics.getHeight() - 60);
    }

    public GameMap getGameMap(){
        return map;
    }
}
