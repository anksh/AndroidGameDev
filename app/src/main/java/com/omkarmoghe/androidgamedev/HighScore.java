package com.omkarmoghe.androidgamedev;

/**
 * Created by Nick on 2/20/2015.
 */
public class HighScore {
    private int circlesCounted;
    private String name;
    private int rank;

    HighScore() {
        circlesCounted = 0;
        name = "";
        rank = 10000;
    }

    HighScore(int c, String n, int r) {
        circlesCounted = c;
        name = n;
        rank = r;
    }

    public void setCirclesCounted(int c) {
        circlesCounted = c;
    }

    public void setName(String n) {
        name = n;
    }

    public void setRank(int r) {
        rank = r;
    }

    public int getCirclesCounted() {
        return circlesCounted;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }
}
