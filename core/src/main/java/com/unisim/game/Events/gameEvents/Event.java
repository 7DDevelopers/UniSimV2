package com.unisim.game.Events.gameEvents;

import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;

public class Event {

    public boolean active;
    public float timeActivated;
    public float duration;
    public int lectureHallBonus;
    public int accommodationBonus;
    public int foodHallBonus;
    public int gymBonus;
    public int clubBonus;

    public Event(float duration){
        active = false;
        this.duration = duration;
        setBonusScores();
    }

    public void activate(float timeActivated){
        if (!active){
            active = true;
            this.timeActivated = timeActivated;
        }
    }

    public void end(){
        if(active){
            active = false;
            timeActivated = -1;
            lectureHallBonus = 0;
            accommodationBonus = 0;
            foodHallBonus = 0;
            gymBonus = 0;
            clubBonus = 0;}
    }

    public boolean isActive(){
        return active;
    }

    public int getLectureHallBonus() {
        return lectureHallBonus;
    }

    public int getAccommodationBonus() {
        return accommodationBonus;
    }

    public int getFoodHallBonus() {
        return foodHallBonus;
    }

    public int getGymBonus() {
        return gymBonus;
    }

    public int getClubBonus() {
        return clubBonus;
    }

    public void setBonusScores(){
        lectureHallBonus = 0;
        accommodationBonus = 0;
        foodHallBonus = 0;
        gymBonus = 0;
        clubBonus = 0;
    }
}
