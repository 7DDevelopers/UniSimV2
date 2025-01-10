package com.unisim.game.Achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.unisim.game.LandPlot;
import com.unisim.game.Stages.MainStage;
import com.unisim.game.main;

import java.io.*;
import java.util.*;

/**
 * Manages the achievements in the game, including loading, tracking, and updating their progress.
 * Handles continuous achievements during gameplay and end-of-game achievements.
 */
public class AchievementManager {
    private final main game; // Reference to the main game instance
    private String path; // File path for achievements storage
    private List<Achievement> achievements; // List of all achievements

    private int gamesFinished; // Number of games completed by the player
    private boolean playingAnimation; // Whether an achievement animation is currently playing
    private Achievement recentAchievement; // The most recently unlocked achievement

    /**
     * Constructs an AchievementManager and initializes it with default achievements if no file exists.
     *
     * @param path The file path where achievements are stored.
     * @param game Reference to the main game instance.
     */
    public AchievementManager(String path, main game) {
        this.game = game;
        this.path = path;
        achievements = new ArrayList<>();
        this.gamesFinished = 0;
        this.playingAnimation = false;
        this.recentAchievement = null;

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeDefaultAchievements(file);
            } catch (IOException e) {
                System.err.println("Error creating file");
            }
        }
        importAchievements();
    }

    /**
     * Writes default achievements to the specified file.
     *
     * @param file The file where default achievements are written.
     */
    private void writeDefaultAchievements(File file) {
        String defaultData = """
        Prestigious University,Achieve a student satisfaction of 75%,Prestigious.png,0,75,true
        Clubber,Build 3 clubs in one game,Clubber.png,0,3,true
        Builder,Place a building in every slot,Builder.png,0,9,true
        Lecturer,Finish 3 games,Clubber.png,0,3,false
        Environmentalist,Place no buildings,Clubber.png,0,9,false
    """;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(defaultData.trim());
        } catch (IOException e) {
            System.err.println("Error writing default achievements to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds a new achievement to the achievement list.
     *
     * @param name            The name of the achievement.
     * @param description     A description of the achievement.
     * @param thumbnail       The thumbnail image for the achievement.
     * @param currentProgress The current progress made towards the achievement.
     * @param requiredProgress The progress required to unlock the achievement.
     * @param continuous      Whether the achievement is continuous or one-time.
     */
    public void addAchievement(String name, String description, Image thumbnail, int currentProgress, int requiredProgress, boolean continuous) {
        Achievement achievement = new Achievement(name, description, thumbnail, currentProgress, requiredProgress, continuous);
        achievements.add(achievement);
    }

    /**
     * Imports achievements from a CSV file at the specified path.
     */
    public void importAchievements() {
        clearLocalAchievements();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                Achievement achievement = new Achievement(
                    row[0],
                    row[1],
                    new Image(new Texture("ui/AchievementImages/" + row[2])),
                    Integer.parseInt(row[3]),
                    Integer.parseInt(row[4]),
                    row[5].equals("true")
                );
                achievements.add(achievement);
            }
        } catch (Exception e) {
            System.err.println("Error reading achievements");
            e.printStackTrace();
        }
    }

    /**
     * Writes the achievement leaderboard to the file.
     */
    public void writeLeaderBoard() {
        try (PrintWriter out = new PrintWriter(new File(path))) {
            for (Achievement achievement : achievements) {
                out.printf("%s,%s\n", achievement.getName(), achievement.getDescription());
            }
        } catch (IOException e) {
            System.err.println("Error writing achievements");
        }
    }

    /**
     * Checks the progress of continuous achievements and updates their status if completed.
     */
    public void CheckContinuousAchievements() {
        // Prestigious University
        achievements.get(0).setCurrentProgress(game.mainStage.scoreManager.getSatisfaction());

        // Clubber
        int clubCount = 0;
        for (LandPlot landPlot : game.mainStage.getLandPlots()) {
            if (landPlot.getBuildingPlaced() != null && landPlot.getBuildingPlaced().getName().equals("Club")) {
                clubCount++;
            }
        }
        achievements.get(1).setCurrentProgress(clubCount);

        // Builder
        int buildingCount = 0;
        for (LandPlot landPlot : game.mainStage.getLandPlots()) {
            if (landPlot.getBuildingPlaced() != null) {
                buildingCount++;
            }
        }
        achievements.get(2).setCurrentProgress(buildingCount);

        // Check if any continuous achievement has been completed
        for (Achievement achievement : achievements) {
            if (achievement.isContinuous() && !achievement.isObtained()) {
                if (achievement.getCurrentProgress() >= achievement.getRequiredProgress()) {
                    System.out.println("Obtained: " + achievement.getName());
                    achievement.setObtained(true);
                    recentAchievement = achievement;
                    playingAnimation = true;
                    game.mainStage.initializeAchievementPopup(achievement);
                }
            }
        }
    }

    /**
     * Checks end-of-game achievements and updates their status if completed.
     */
    public void checkEndAchievements() {
        // Lecturer
        achievements.get(3).setCurrentProgress(gamesFinished);

        // Environmentalist
        int emptyPlotCount = 0;
        for (LandPlot landPlot : game.mainStage.getLandPlots()) {
            if (landPlot.getBuildingPlaced() == null) {
                emptyPlotCount++;
            }
        }
        achievements.get(4).setCurrentProgress(emptyPlotCount);

        // Check if any end-game achievement has been completed
        for (Achievement achievement : achievements) {
            if (!achievement.isContinuous() && !achievement.isObtained()) {
                if (achievement.getCurrentProgress() >= achievement.getRequiredProgress()) {
                    System.out.println("Obtained: " + achievement.getName());
                    achievement.setObtained(true);
                }
            }
        }
    }

    /**
     * Clears the local achievements list.
     */
    private void clearLocalAchievements() {
        achievements.clear();
    }

    /**
     * Gets the list of all achievements.
     *
     * @return A list of achievements.
     */
    public List<Achievement> getAchievements() {
        return achievements;
    }

    /**
     * Sets the number of games finished by the player.
     *
     * @param gamesFinished The number of games finished.
     */
    public void setGamesFinished(int gamesFinished) {
        this.gamesFinished = gamesFinished;
    }

    /**
     * Gets the number of games finished by the player.
     *
     * @return The number of games finished.
     */
    public int getGamesFinished() {
        return gamesFinished;
    }

    /**
     * Checks whether an achievement animation is currently playing.
     *
     * @return True if an animation is playing, false otherwise.
     */
    public boolean isPlayingAnimation() {
        return playingAnimation;
    }

    /**
     * Gets the most recently unlocked achievement.
     *
     * @return The most recent achievement.
     */
    public Achievement getRecentAchievement() {
        return recentAchievement;
    }
}
