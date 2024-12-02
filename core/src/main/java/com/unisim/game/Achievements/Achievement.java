package com.unisim.game.Achievements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Achievement {
    private String name;
    private String description;
    private Image thumbnail;

    private int currentProgress;
    private int requiredProgress;
    private boolean continuous;

    private boolean obtained;

    public Achievement(String name, String desc, Image thumbnail, int currentProgress, int requiredProgress, boolean continuous) {
        this.name = name;
        this.description = desc;
        this.thumbnail = thumbnail;
        this.currentProgress = currentProgress;
        this.requiredProgress = requiredProgress;
        this.continuous = continuous;

        this.obtained = false; //change to load save file
    }

    public void setRequirements(){

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public void setObtained(boolean obtained) {
        this.obtained = obtained;
    }

    public boolean isObtained() {
        return obtained;
    }
}
