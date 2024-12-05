package com.unisim.game.Events;

import com.unisim.game.Events.gameEvents.ExamSeason;
import com.unisim.game.Events.gameEvents.FreshersWeek;
import com.unisim.game.Events.gameEvents.Heatwave;
import com.unisim.game.Events.gameEvents.Storm;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    public FreshersWeek freshersWeekEvent;
    public Storm stormEvent;
    public ExamSeason examSeasonEvent;
    public Heatwave heatwaveEvent;

    public EventManager(){
        freshersWeekEvent = new FreshersWeek();
        stormEvent = new Storm();
        examSeasonEvent = new ExamSeason();
        heatwaveEvent = new Heatwave();
    }

    public void startExamSeason(float time){
        examSeasonEvent.activate(time);
    }
    public void endExamSeason(){
        examSeasonEvent.end();
    }
    public void startStorm(float time){
        stormEvent.activate(time);
    }
    public void endStorm(){
        stormEvent.end();
    }
    public void startFreshersWeek(float time){
        freshersWeekEvent.activate(time);
    }
    public void endFreshersWeek(){
        freshersWeekEvent.end();
    }
    public void startHeatwave(float time){
        heatwaveEvent.activate(time);
    }
    public void endHeatwave(){
        heatwaveEvent.end();
    }

    public int lectureHallBonus(){
        int bonusPoints = examSeasonEvent.getLectureHallBonus() + freshersWeekEvent.getLectureHallBonus() +
            heatwaveEvent.getLectureHallBonus() + stormEvent.getLectureHallBonus();
        return bonusPoints;
    }

    public int foodHallBonus(){
        int bonusPoints = examSeasonEvent.getFoodHallBonus() + freshersWeekEvent.getFoodHallBonus()  +
            heatwaveEvent.getFoodHallBonus()  + stormEvent.getFoodHallBonus() ;
        return bonusPoints;
    }
    public int gymBonus(){
        int bonusPoints = examSeasonEvent.getGymBonus() + freshersWeekEvent.getGymBonus()  +
            heatwaveEvent.getGymBonus()  + stormEvent.getGymBonus() ;
        return bonusPoints;
    }
    public int accommodationBonus(){
        int bonusPoints = examSeasonEvent.getAccommodationBonus() + freshersWeekEvent.getAccommodationBonus()  +
            heatwaveEvent.getAccommodationBonus()  + stormEvent.getAccommodationBonus() ;
        return bonusPoints;
    }
    public int clubBonus(){
        int bonusPoints = examSeasonEvent.getClubBonus() + freshersWeekEvent.getClubBonus()  +
            heatwaveEvent.getClubBonus()  + stormEvent.getClubBonus() ;
        return bonusPoints;
    }

    public void eventChecker(float time){
        if (freshersWeekEvent.isActive()){
            if (freshersWeekEvent.timeActivated-time >= freshersWeekEvent.duration){
                System.out.println("Freshers week has ended");
                freshersWeekEvent.end();

            }
        }
        if (stormEvent.isActive()){
            if (stormEvent.timeActivated-time >= stormEvent.duration){
                System.out.println("Storm has ended");
                stormEvent.end();

            }
        }
    }

    public java.util.List<String> getActiveEvents(){
        java.util.List<String> activeEvents = new ArrayList<>();
        if (freshersWeekEvent.isActive()){
            activeEvents.add("Freshers Week");
        }
        if (stormEvent.isActive()){
            activeEvents.add(("Rain storm"));
        }
        if(heatwaveEvent.isActive()){
            activeEvents.add("Heatwave");
        }
        if(examSeasonEvent.isActive()){
            activeEvents.add("Exam season");
        }
        return  activeEvents;
    }

}
