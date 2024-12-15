package com.unisim.game.Leaderboard;

public class Entry {
    /**String representing the name of the player*/
    private String name;
    /**Integer representing the score of the player*/
    private int score;

    /**Constructor taking score as an integer*/
    public Entry(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /**Alternative constructor taking score as a String*/
    public Entry(String name, String score){
        this.name = name;
        this.score = Integer.parseInt(score);
    }

    /**Returns a string representing the name of the entry*/
    public String getName() {
        return name;
    }
    /**Returns an integer representing the score of the entry*/
    public int getScore() {
        return score;
    }


}
