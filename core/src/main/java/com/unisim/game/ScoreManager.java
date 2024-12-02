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
    private int satisfaction;
    public ScoreManager(MainStage game){
        score = 0;
        satisfaction = 0;
        this.game = game;
    }

    public int getScore(){
        return score;
    }

    public int getSatisfaction(){
        satisfaction =0;
        for(int i = 0; i<9; i++){
            LandPlot landPlot = game.getLandPlots()[i];
            if (landPlot.isOccupied()){
                Building buildingOnLandplot = landPlot.getBuildingPlaced();
                String buildingName = buildingOnLandplot.getName();

                switch (buildingName){
                    case "Accommodation":
                        satisfaction += 10;
                        for(int nearbyBuildingIndex: nearbyLandPlot(i)){
                            LandPlot nearbyLandplot = game.getLandPlots()[nearbyBuildingIndex];
                            if(nearbyLandplot.isOccupied()){
                                Building nearbyBuilding = nearbyLandplot.getBuildingPlaced();
                                String nearbyBuildingName = nearbyBuilding.getName();
                                if (nearbyBuildingName == "FoodHall"){
                                    satisfaction+=5;
                                } else if (nearbyBuildingName == "Gym") {
                                    satisfaction +=5;
                                } else if (nearbyBuildingName == "Club") {
                                    satisfaction+=5;
                                }
                            }
                        }
                        break;
                    case "LectureHall":
                        boolean accommocationNearby = false;
                        for(int nearbyBuildingIndex: nearbyLandPlot(i)){
                            LandPlot nearbyLandplot = game.getLandPlots()[nearbyBuildingIndex];
                            if(nearbyLandplot.isOccupied()){
                                Building nearbyBuilding = nearbyLandplot.getBuildingPlaced();
                                String nearbyBuildingName = nearbyBuilding.getName();
                                if (nearbyBuildingName == "Accommodation"){
                                    accommocationNearby = true;
                                }
                            }
                        }
                        if(accommocationNearby == true){satisfaction+=10;}
                        break;
                    case "FoodHall":
                        satisfaction +=5;
                        break;
                    case "Gym":
                        satisfaction += 5;
                        break;
                    case "Club":
                        satisfaction +=5;
                        break;
                    default:
                        break;
                }
            }
        }
        score += satisfaction;
        return satisfaction;
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
