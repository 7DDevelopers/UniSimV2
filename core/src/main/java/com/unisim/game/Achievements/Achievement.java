package com.unisim.game.Achievements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Achievement {
    private String name;
    private String description;
    private Image thumbnail;
    private Image textImage;

    public Achievement(String name, String desc, Image thumbnail, Image textImage) {
        this.name = name;
        this.description = desc;
        this.thumbnail = thumbnail;
        this.textImage = textImage;
    }

    public void setRequirements(){

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getTextImage(){
        return textImage;
    }

    public Image getThumbnail(){
        return thumbnail;
    }
}
