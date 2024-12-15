package com.unisim.game.Leaderboard;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class LeaderboardManager {
    /**The path of the leaderboard file*/
    private String path;
    /**The list of Entry objects stored in the leaderboard*/
    private List<Entry> leaderboard;


    public LeaderboardManager(String path){
        this.path = path;
        File file = new File(path);
        leaderboard = new ArrayList<>();
        // Created a new file if one doesn't exist
        if(file.exists() == false){
            try{file.createNewFile();}
            catch (IOException e){
                System.err.println("Error creating file");
            }
        }
        importLeaderboard();
    }
    /**Adds a new Entry object to the leaderboard given a name and a score*/
    public void addEntry(String name, int score){
        Entry scoreEntry = new Entry(name, score);
        leaderboard.add(scoreEntry);
        leaderboard.sort((entry1, entry2) -> {
            // Sorts the list in numeric order
            return Integer.compare(entry2.getScore(), entry1.getScore()); // Sort descending
        });
    }
    /**Reads the leaderboard csv file and imports the entries locally*/
    public void importLeaderboard(){
        //Empties the current local list to avoid double entryes
        clearLocalLeaderboard();
        // Reads the file
        BufferedReader reader = null;
        String line = "";
        try{
            reader = new BufferedReader(new FileReader(path));
            while((line = reader.readLine()) != null){

                String[] row = line.split(",");
                // Formats the text and extracts the name and score to be saved in an Entry
                Entry entry = new Entry(row[0], row[1]);
                leaderboard.add(entry);
            }
        }
        // Catches any errors and closes the file afterward
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

    /**Uses the list of Entries to write the updated leaderboard back to the file*/
    public void writeLeaderBoard()  {
        File file = new File(path);
        try{
            PrintWriter out = new PrintWriter(file);
            // Iterates through the locally saved entries
            for(Entry entry : leaderboard){
                out.printf("%s,%d\n", entry.getName(), entry.getScore());
            }
            out.close();
        }
        catch (IOException e){
            System.err.println("Error writing leaderboard");
        }

    }
    /**Deletes any entries currently stored locally
     * Use this when the leaderboard has been saved to the file*/
    private void clearLocalLeaderboard(){
        leaderboard.clear();
    }

    /**Returns the entries currently stored locally*/
    public List<Entry> getLeaderboard() {
        return leaderboard;
    }
}
