package com.unisim.game.Leaderboard;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class LeaderboardManager {
    private String path;
    private List<Entry> leaderboard;


    public LeaderboardManager(String path){
        this.path = path;
        File file = new File(path);
        leaderboard = new ArrayList<>();
        if(file.exists() == false){
            try{file.createNewFile();}
            catch (IOException e){
                System.err.println("Error creating file");
            }
        }
        importLeaderboard();
    }
    public void addEntry(String name, int score){
        Entry scoreEntry = new Entry(name, score);
        leaderboard.add(scoreEntry);
        leaderboard.sort((entry1, entry2) -> {
            return Integer.compare(entry2.getScore(), entry1.getScore()); // Sort descending
        });
    }

    public void importLeaderboard(){
        // Reads csv and imports the leaderboard
        clearLocalLeaderboard();
        BufferedReader reader = null;
        String line = "";
        try{
            reader = new BufferedReader(new FileReader(path));
            while((line = reader.readLine()) != null){

                String[] row = line.split(",");
                Entry entry = new Entry(row[0], row[1]);
                leaderboard.add(entry);
            }
        }
        catch (Exception e){
            System.err.println("Error reading leaderboard");
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeLeaderBoard()  {
        File file = new File(path);
        try{
            PrintWriter out = new PrintWriter(file);

            for(Entry entry : leaderboard){
                out.printf("%s,%d\n", entry.getName(), entry.getScore());
            }
            out.close();
        }
        catch (IOException e){
            System.err.println("Error writing leaderboard");
        }

    }

    private void clearLocalLeaderboard(){
        leaderboard.clear();
    }


    public List<Entry> getLeaderboard() {
        return leaderboard;
    }
}
