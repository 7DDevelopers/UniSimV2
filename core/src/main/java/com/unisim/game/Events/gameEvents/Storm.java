package com.unisim.game.Events.gameEvents;

public class Storm extends Event {
    public Storm(){
        super(15);
    }
    @Override
    public void setBonusScores(){
        lectureHallBonus = -2;
        accommodationBonus = 10;
        foodHallBonus = -2;
        gymBonus = -2;
        clubBonus = -2;
    }
}
