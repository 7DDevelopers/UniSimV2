package com.unisim.game.Achievements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Represents an achievement in the game. Each achievement has a name, description,
 * thumbnail, progress tracking, and whether it is continuous or one-time.
 */
public class Achievement {
    private String name; // The name of the achievement
    private String description; // A description of what the achievement is for
    private Image thumbnail; // The thumbnail image associated with the achievement

    private int currentProgress; // The current progress made towards the achievement
    private int requiredProgress; // The progress required to complete the achievement
    private boolean continuous; // Whether the achievement is continuous or one-time

    private boolean obtained; // Indicates whether the achievement has been obtained

    /**
     * Constructs a new Achievement.
     *
     * @param name             The name of the achievement.
     * @param desc             A description of the achievement.
     * @param thumbnail        The thumbnail image for the achievement.
     * @param currentProgress  The current progress made towards the achievement.
     * @param requiredProgress The total progress required to complete the achievement.
     * @param continuous       Whether the achievement is continuous or a one-time unlock.
     */
    public Achievement(String name, String desc, Image thumbnail, int currentProgress, int requiredProgress, boolean continuous) {
        this.name = name;
        this.description = desc;
        this.thumbnail = thumbnail;
        this.currentProgress = currentProgress;
        this.requiredProgress = requiredProgress;
        this.continuous = continuous;
        this.obtained = false;
    }

    /**
     * Sets the requirements for the achievement.
     * This method can be customized to dynamically adjust achievement requirements.
     */
    public void setRequirements() {
        // Implementation goes here
    }

    /**
     * Gets the name of the achievement.
     *
     * @return The name of the achievement.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the achievement.
     *
     * @return The description of the achievement.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the thumbnail image of the achievement.
     *
     * @return The thumbnail image associated with the achievement.
     */
    public Image getThumbnail() {
        return thumbnail;
    }

    /**
     * Gets the current progress made towards the achievement.
     *
     * @return The current progress as an integer.
     */
    public int getCurrentProgress() {
        return currentProgress;
    }

    /**
     * Sets the current progress of the achievement.
     *
     * @param progress The new progress value to set.
     */
    public void setCurrentProgress(int progress) {
        currentProgress = progress;
    }

    /**
     * Gets the total progress required to complete the achievement.
     *
     * @return The required progress as an integer.
     */
    public int getRequiredProgress() {
        return requiredProgress;
    }

    /**
     * Determines whether the achievement is continuous.
     *
     * @return True if the achievement is continuous, false if it is one-time.
     */
    public boolean isContinuous() {
        return continuous;
    }

    /**
     * Checks whether the achievement has been obtained.
     *
     * @return True if the achievement is obtained, false otherwise.
     */
    public boolean isObtained() {
        return obtained;
    }

    /**
     * Sets the obtained status of the achievement.
     *
     * @param obtained True to mark the achievement as obtained, false otherwise.
     */
    public void setObtained(boolean obtained) {
        this.obtained = obtained;
    }
}
