package com.unisim.game.Events.gameEvents;

import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;

public class Event {

    public boolean active;
    public int timeActivated;
    public int duration;
    public int lectureHallBonus;
    public int accommodationBonus;
    public int foodHallBonus;
    public int gymBonus;
    public int clubBonus;

    public Event(int duration){
        active = false;
        this.duration = duration;
        setBonusScores();
    }

    public void activate(int timeActivated){
        if (!active){
            active = true;
            this.timeActivated = timeActivated;
        }
    }

    public void end(){
        if(active){
            active = false;
            timeActivated = -1;}
    }

    public boolean isActive(){
        return active;
    }

    public void setBonusScores(){
        lectureHallBonus = 0;
        accommodationBonus = 0;
        foodHallBonus = 0;
        gymBonus = 0;
        clubBonus = 0;
    }
}
