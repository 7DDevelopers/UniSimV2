package com.unisim.game.Events.gameEvents;

public class FreshersWeek extends Event {

    public FreshersWeek(){
        super(15);
    }

    @Override
    public void setBonusScores(){
        lectureHallBonus = 0;
        accommodationBonus = 15;
        foodHallBonus = 0;
        gymBonus = 0;
        clubBonus = 10;
    }
}
