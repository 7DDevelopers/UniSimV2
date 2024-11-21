package com.unisim.game.Events.gameEvents;

public class Heatwave extends Event {

    public Heatwave(){
        super(15);
    }

    @Override
    public void setBonusScores(){
        lectureHallBonus = 5;
        accommodationBonus = 5;
        foodHallBonus = 5;
        gymBonus = 5;
        clubBonus = 5;
    }
}
