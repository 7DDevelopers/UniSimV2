package com.unisim.game.Events.gameEvents;

public class Winter extends Event{
    //https://opengameart.org/content/very-basic-32x32-topdown-snow-tileset
    public Winter(){
        super(15);
    }
    /** Overrides the default bonus scores of the Event class*/
    @Override
    public void setBonusScores(){
        lectureHallBonus = 2;
        accommodationBonus = 10;
        foodHallBonus = 2;
        gymBonus = -2;
        clubBonus = -2;
    }
}
