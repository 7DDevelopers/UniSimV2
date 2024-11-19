package com.unisim.game.Leaderboard;

public class Entry {
    private String name;
    private int score;

    public Entry(String name, int score) {
        this.name = name;
        this.score = score;
    }
    public Entry(String name, String score){
        this.name = name;
        this.score = Integer.parseInt(score);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }


}
