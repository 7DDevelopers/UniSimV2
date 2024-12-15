package com.unisim.game.Events.gameEvents;

public class Heatwave extends Event {

    public Heatwave(){
        super(15);
    }

    /** Overrides the default bonus scores of the Event class*/
    @Override
    public void setBonusScores(){
        lectureHallBonus = 5;
        accommodationBonus = 5;
        foodHallBonus = 5;
        gymBonus = 5;
        clubBonus = 5;
    }
}
