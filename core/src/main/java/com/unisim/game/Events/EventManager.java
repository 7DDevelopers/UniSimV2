package com.unisim.game.Events;

import com.unisim.game.Events.gameEvents.*;
import com.unisim.game.Stages.MainStage;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    /**Objects representing each type of possible event*/
    public FreshersWeek freshersWeekEvent;
    public Storm stormEvent;
    public ExamSeason examSeasonEvent;
    public Heatwave heatwaveEvent;
    public Winter winterEvent;

    /**Reference back to the main stage*/
    MainStage game;

    public EventManager(MainStage game){
        freshersWeekEvent = new FreshersWeek();
        stormEvent = new Storm();
        examSeasonEvent = new ExamSeason();
        heatwaveEvent = new Heatwave();
        winterEvent =  new Winter();
        this.game = game;
    }

    /**Function that start specific events*/
    public void startExamSeason(float time){
        examSeasonEvent.activate(time);
    }
    public void startStorm(float time){
        stormEvent.activate(time);
    }
    public void startFreshersWeek(float time){
        freshersWeekEvent.activate(time);
    }
    public void startHeatwave(float time){
        heatwaveEvent.activate(time);
    }
    public void startWinter(float time){
        winterEvent.activate(time);
        game.map.winterSeasonMap();
    }

    /**Methods that calculate the score bonus for each building based on current events*/
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

    /**Uses time and event durations to make sure events aren't overrunning*/
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
        if (examSeasonEvent.isActive()){
            if (examSeasonEvent.timeActivated-time >= examSeasonEvent.duration){
                System.out.println("Exam week has ended");
                examSeasonEvent.end();
            }
        }
        if (heatwaveEvent.isActive()){
            if (heatwaveEvent.timeActivated-time >= heatwaveEvent.duration){
                System.out.println("Heatwave has ended");
                heatwaveEvent.end();
            }
        }
        if (winterEvent.isActive()){
            if (winterEvent.timeActivated-time >= winterEvent.duration){
                System.out.println("winter has ended");
                winterEvent.end();
                game.map.defaultMap();
            }
        }
    }

    /**Returns a list of strings representing the currently active events*/
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
        if(winterEvent.isActive()){
            activeEvents.add("Christmas");
        }
        return  activeEvents;
    }

}
