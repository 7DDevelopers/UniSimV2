package com.unisim.game.Achievements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Achievement {
    private String name;
    private String description;
    private Image thumbnail;

    private int ID;

    private int currentProgress;
    private int requiredProgress;
    private boolean continuous;

    private boolean obtained;

    public Achievement(String name, String desc, Image thumbnail, int currentProgress, int requiredProgress, boolean continuous, int ID) {
        this.name = name;
        this.description = desc;
        this.thumbnail = thumbnail;
        this.currentProgress = currentProgress;
        this.requiredProgress = requiredProgress;
        this.continuous = continuous;
        this.obtained = false;
        this.ID = ID;
    }

    public void setRequirements(){

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getID(){
        return ID;
    }

    void setID(int newID){
        ID = newID;
    }

    public Image getThumbnail(){
        return thumbnail;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int progress) {
        currentProgress = progress;
    }

    public int getRequiredProgress() {
        return requiredProgress;
    }

    public boolean isContinuous() {
        return continuous;
    }

    public boolean isObtained() {
        return obtained;
    }

    public void setObtained(boolean obtained) {
        this.obtained = obtained;
    }
}
