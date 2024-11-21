package com.unisim.game.Events;

import com.unisim.game.Events.gameEvents.ExamSeason;
import com.unisim.game.Events.gameEvents.FreshersWeek;
import com.unisim.game.Events.gameEvents.Heatwave;
import com.unisim.game.Events.gameEvents.Storm;

public class EventManager {

    FreshersWeek freshersWeekEvent;
    Storm stormEvent;
    ExamSeason examSeasonEvent;
    Heatwave heatwaveEvent;

    public EventManager(){
        freshersWeekEvent = new FreshersWeek();
        stormEvent = new Storm();
        examSeasonEvent = new ExamSeason();
        heatwaveEvent = new Heatwave();
    }

    public void startExamSeason(int time){
        examSeasonEvent.activate(time);
    }
    public void endExamSeason(){
        examSeasonEvent.end();
    }
    public void startStorm(int time){
        stormEvent.activate(time);
    }
    public void endStorm(){
        stormEvent.end();
    }
    public void startFreshersWeek(int time){
        freshersWeekEvent.activate(time);
    }
    public void endFreshersWeek(){
        freshersWeekEvent.end();
    }
    public void startHeatwave(int time){
        heatwaveEvent.activate(time);
    }
    public void endHeatwave(){
        heatwaveEvent.end();
    }
}
