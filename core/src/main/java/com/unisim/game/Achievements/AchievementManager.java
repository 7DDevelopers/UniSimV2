package com.unisim.game.Achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class AchievementManager {
    private String path;
    private List<Achievement> achievements;


    public AchievementManager(String path){
        this.path = path;
        File file = new File(path);
        achievements = new ArrayList<>();
        if(file.exists() == false){
            try{file.createNewFile();}
            catch (IOException e){
                System.err.println("Error creating file");
            }
        }
        importAchievements();
    }
    public void addAchievement(String name, String description, Image thumbnail, Image textImage){
        Achievement scoreAchievement = new Achievement(name, description, thumbnail, textImage);
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
                Achievement achievement = new Achievement(row[0], row[1], new Image(new Texture("ui/AchievementImages/" + row[2])), new Image(new Texture("ui/AchievementImages/AchievementTextBackground.png")));
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

    private void clearLocalAchievements(){
        achievements.clear();
    }


    public List<Achievement> getAchievements() {
        return achievements;
    }
}
