package com.unisim.game.Events.gameEvents;

public class FreshersWeek extends Event {

    public FreshersWeek(){
        super(15);
    }

    /** Overrides the default bonus scores of the Event class*/
    @Override
    public void setBonusScores(){
        lectureHallBonus = 0;
        accommodationBonus = 15;
        foodHallBonus = 0;
        gymBonus = 0;
        clubBonus = 10;
    }
}
