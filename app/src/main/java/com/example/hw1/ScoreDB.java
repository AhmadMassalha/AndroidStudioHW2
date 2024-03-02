package com.example.hw1;

import java.util.ArrayList;

public class ScoreDB {

    private ArrayList<Score> scores;
    public ScoreDB() { }
    public ArrayList<Score> getRecords() {
        if(scores == null){
            scores = new ArrayList<>();
        }
        return scores;
    }
}
