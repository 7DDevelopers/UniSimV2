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

    /**Returns an integer representing the score of the current game*/
    public int getScore(){
        return score;
    }

    /**Calculates the overall satisfaction based on building positions and events*/
    public int getSatisfaction(){
        satisfaction =0;
        // Iterates through each landplot
        for(int i = 0; i<9; i++){
            LandPlot landPlot = game.getLandPlots()[i];
            // Continues only if the current landplot is occupied
            if (landPlot.isOccupied()){
                Building buildingOnLandplot = landPlot.getBuildingPlaced();
                String buildingName = buildingOnLandplot.getName();
                // Uses selection based on the building name
                switch (buildingName){
                    case "Accommodation":
                        satisfaction += 10;
                        satisfaction+=game.eventManager.accommodationBonus();
                        for(int nearbyBuildingIndex: nearbyLandPlot(i)){
                            LandPlot nearbyLandplot = game.getLandPlots()[nearbyBuildingIndex];
                            // Checks all nearby landplots to give bonus points if any of the nearby buildings
                            // are a food hall, gym, or club
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
                                // Checks all nearby buildings, only scoring points of there is accommodation nearby
                                Building nearbyBuilding = nearbyLandplot.getBuildingPlaced();
                                String nearbyBuildingName = nearbyBuilding.getName();
                                if (nearbyBuildingName == "Accommodation"){
                                    accommocationNearby = true;
                                }
                            }
                        }
                        if(accommocationNearby == true){satisfaction+=10;
                            satisfaction+=game.eventManager.lectureHallBonus();}
                        break;
                    case "FoodHall":
                        satisfaction +=5;
                        satisfaction+=game.eventManager.foodHallBonus();
                        break;
                    case "Gym":
                        satisfaction += 5;
                        satisfaction+=game.eventManager.gymBonus();
                        break;
                    case "Club":
                        satisfaction +=5;
                        satisfaction+=game.eventManager.clubBonus();
                        break;
                    default:
                        break;
                }
            }
        }
        return satisfaction;
    }
    public void updateScore(){
        // Score is saved as the total of the satisfaction over the 5 minutes
        score += satisfaction;
    }

    /**Uses a matrix to return the landplot numbers of nearby landplots of the given landplot*/
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
