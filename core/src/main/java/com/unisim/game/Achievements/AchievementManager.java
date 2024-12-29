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
import com.unisim.game.Leaderboard.Entry;
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

    private String defaultData = """
        Prestigious University,Achieve a student satisfaction of 75%,Prestigious.png,0,75,true,0
        Clubber,Build 3 clubs in one game,Clubber.png,0,3,true,1
        Builder,Place a building in every slot,Builder.png,0,9,true,2
        Lecturer,Finish 3 games,Clubber.png,0,3,false,3
        Environmentalist,Place no buildings,Clubber.png,0,0,false,4
    """;

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
        importObtainedAchievements();
    }

    public void importObtainedAchievements() {
        BufferedReader reader = null;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader("obtainedAchievements.csv"));
            while ((line = reader.readLine()) != null) {

                achievements.get(Integer.parseInt(line)).setObtained(true);
            }
        } catch (Exception e) {
            System.err.println("Error reading obtained achievements");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void importAchievements(){
        // Reads csv and imports the achievements
        clearLocalAchievements();
        BufferedReader reader = null;
        String line = "";
        try{
            reader = new BufferedReader(new StringReader(defaultData));
            while((line = reader.readLine()) != null){

                String[] row = line.split(",");
                Achievement achievement = new Achievement(row[0], row[1], new Image(new Texture("ui/AchievementImages/" + row[2])), Integer.parseInt(row[3]), Integer.parseInt(row[4]), row[5].equals("true"), Integer.parseInt(row[6]));
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
                    writeAchievement(achievement);

                    recentAchievement = achievement;
                    playingAnimation = true;
                    game.mainStage.initializeAchievementPopup(achievement);
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
                    writeAchievement(achievement);
                }
            }
        }
    }

    private void clearLocalAchievements(){
        achievements.clear();
    }

    public void writeAchievement(Achievement achievement)  {
        File file = new File("obtainedAchievements.csv");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try{
            PrintWriter out = new PrintWriter(new FileWriter("obtainedAchievements.csv", true));

            out.println(achievement.getID());

            out.close();
        }
        catch (IOException e){
            System.err.println("Error writing leaderboard");
        }
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
