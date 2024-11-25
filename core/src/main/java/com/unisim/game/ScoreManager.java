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
        System.out.println("-------");
        for(LandPlot landPlot : game.getLandPlots()){

            if (landPlot.isOccupied()){
                Building buildingOnLandplot = landPlot.getBuildingPlaced();
                String buildingName = buildingOnLandplot.getName();
                System.out.println(buildingName);
                switch (buildingName){
                    case "Accommodation":
                        score += 10;
                    case "LectureHall":
                        score+=10;
                    case "FoodHall":
                        score +=5;
                    case "Gym":
                        score += 5;
                    case "Club":
                        score +=5;
                }
            }
            else{
                System.out.println("Unoccupied");
            }

        }
        System.out.println("------");
        return score;
    }

}
