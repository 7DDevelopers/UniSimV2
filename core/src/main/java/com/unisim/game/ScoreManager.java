package com.unisim.game;
import com.sun.tools.javac.Main;
import com.unisim.game.Stages.MainStage;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Arrays;

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
                System.out.println("Unoccupied");
            }

        }
        System.out.println("------");
        return score;
    }

    public ArrayList<Integer> nearbyLandPlot(int landplotIndex){
        return switch (landplotIndex) {
            case 0, 3 -> new ArrayList<>(Arrays.asList(1, 2));
            case 1 -> new ArrayList<>(Arrays.asList(0, 2, 3));
            case 2 -> new ArrayList<>(Arrays.asList(0, 1, 3));
            case 4 -> new ArrayList<>(Arrays.asList(5, 6));
            case 5 -> new ArrayList<>(Arrays.asList(4, 6, 7));
            case 6 -> new ArrayList<>(Arrays.asList(5, 7, 8));
            case 7 -> new ArrayList<>(Arrays.asList(5, 6, 8));
            case 8 -> new ArrayList<>(Arrays.asList(6, 7));
            default -> new ArrayList<>();
        };
    }

}
