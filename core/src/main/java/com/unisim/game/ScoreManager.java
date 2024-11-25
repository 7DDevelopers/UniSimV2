package com.unisim.game;
import com.sun.tools.javac.Main;
import com.unisim.game.Stages.MainStage;
import java.util.ArrayList;

public class ScoreManager {
    /**Contains the {@link LandPlot} for building placement on the map.*/
    private int score;
    MainStage game;

    public ScoreManager(MainStage game){
        score = 0;
        this.game = game;
    }

    public int calculateScore(){
        for(LandPlot landPlot : game.getLandPlots()){
            if (landPlot.isOccupied()){
                Building buildingOnLandplot = landPlot.getBuildingPlaced();
                String buildingName = buildingOnLandplot.getName();
                switch (buildingName){
                    case "Accommodation":
                        score += 10;
                        break;
                    case "LectureHall":
                        score+=10;
                        break;
                    case "FoodHall":
                        score +=5;
                        break;
                    case "Gym":
                        score += 5;
                        break;
                    case "Club":
                        score +=5;
                        break;
                    default:
                        break;
                }
            }
            else{
            }
        }
        return score;
    }

}
