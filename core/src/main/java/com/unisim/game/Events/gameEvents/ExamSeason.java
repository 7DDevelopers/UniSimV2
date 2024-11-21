package com.unisim.game.Events.gameEvents;

public class ExamSeason extends Event {

    public ExamSeason(){
        super(15);
    }

    @Override
    public void setBonusScores(){
        lectureHallBonus = 10;
        accommodationBonus = 5;
        foodHallBonus = 0;
        gymBonus = 0;
        clubBonus = -10;
    }

}
