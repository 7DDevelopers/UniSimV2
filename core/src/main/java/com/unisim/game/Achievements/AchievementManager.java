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
import com.sun.tools.javac.Main;
import com.unisim.game.LandPlot;
import com.unisim.game.Stages.MainStage;
import com.unisim.game.main;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class AchievementManager {
    private final main game;

    private String path;
    private List<Achievement> achievements;

    private int gamesFinished;

    private boolean playingAnimation;
    private Achievement recentAchievement;

    public AchievementManager(String path, main game){
        this.game = game;
        this.path = path;
        File file = new File(path);
        achievements = new ArrayList<>();
        this.gamesFinished = 0;
        this.playingAnimation = false;
        this.recentAchievement = null;
        if(file.exists() == false){
            try{file.createNewFile();}
            catch (IOException e){
                System.err.println("Error creating file");
            }
        }
        importAchievements();
    }
    public void addAchievement(String name, String description, Image thumbnail, int currentProgress, int requiredProgress, boolean continuous){
        Achievement scoreAchievement = new Achievement(name, description, thumbnail, currentProgress, requiredProgress, continuous);
        achievements.add(scoreAchievement);
    }

    public void importAchievements(){
        // Reads csv and imports the achievements
        clearLocalAchievements();
        BufferedReader reader = null;
        String line = "";
        try{
            reader = new BufferedReader(new FileReader(path));
            while((line = reader.readLine()) != null){

                String[] row = line.split(",");
                Achievement achievement = new Achievement(row[0], row[1], new Image(new Texture("ui/AchievementImages/" + row[2])), Integer.parseInt(row[3]), Integer.parseInt(row[4]), row[5].equals("true"));
                achievements.add(achievement);
            }
        }
        catch (Exception e){
            System.err.println("Error reading achievements");
            System.err.println(e);
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeLeaderBoard()  {
        File file = new File(path);
        try{
            PrintWriter out = new PrintWriter(file);

            for(Achievement achievement : achievements){
                out.printf("%s,%d\n", achievement.getName(), achievement.getDescription());
            }
            out.close();
        }
        catch (IOException e){
            System.err.println("Error writing achievements");
        }

    }

    //Listen for achievements
    public void CheckContinuousAchievements(){
        //Prestigious
        achievements.get(0).setCurrentProgress(game.mainStage.scoreManager.getSatisfaction());

        //Clubber
        int lpCount = 0;
        for (LandPlot landPlot : game.mainStage.getLandPlots()) {
            if(landPlot.getBuildingPlaced() != null) {
                if (landPlot.getBuildingPlaced().getName() == "Club") {
                    lpCount++;
                }
            }
        }
        achievements.get(1).setCurrentProgress(lpCount);

        //Builder
        lpCount = 0;
        for (LandPlot landPlot : game.mainStage.getLandPlots()) {
            if(landPlot.getBuildingPlaced() != null) {
                lpCount++;
            }
        }
        achievements.get(2).setCurrentProgress(lpCount);

        for(Achievement achievement : achievements){
            if (achievement.isContinuous() && !achievement.isObtained()) {
                if (achievement.getCurrentProgress() >= achievement.getRequiredProgress()) {
                    System.out.println("Obtained: " + achievement.getName());
                    achievement.setObtained(true);
                    recentAchievement = achievement;
                    playingAnimation = true;
                }
            }
        }
    }

    public void checkEndAchievements(){
        //Lecturer
        achievements.get(3).setCurrentProgress(gamesFinished);

        //Environmentalist
        int lpCount = 0;
        for (LandPlot landPlot : game.mainStage.getLandPlots()) {
            if(landPlot.getBuildingPlaced() != null) {
                lpCount++;
            }
        }
        achievements.get(4).setCurrentProgress(lpCount);


        for(Achievement achievement : achievements){
            if (!achievement.isContinuous() && !achievement.isObtained()) {
                if (achievement.getCurrentProgress() >= achievement.getRequiredProgress()) {
                    System.out.println("Obtained: " + achievement.getName());
                    achievement.setObtained(true);
                }
            }
        }
    }

    private void clearLocalAchievements(){
        achievements.clear();
    }


    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setGamesFinished(int gamesFinished) {
        this.gamesFinished = gamesFinished;
    }

    public int getGamesFinished() {
        return gamesFinished;
    }

    public boolean isPlayingAnimation() {
        return playingAnimation;
    }

    public Achievement getRecentAchievement() {
        return recentAchievement;
    }
}
