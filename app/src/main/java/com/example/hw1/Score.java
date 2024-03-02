package com.example.hw1;

public class Score {
    private String name;
    private int score = 0;
    private double lat = 0.0;
    private double lon = 0.0;
    public Score() { }
    public String getName() {
        return name;
    }
    public Score setName(String name) {
        this.name = name;
        return this;
    }
    public int getScore() {
        return score;
    }
    public Score setScore(int score) {
        this.score = score;
        return this;
    }
    public double getLatitude() {
        return lat;
    }
    public Score setLatitude(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLongitude() {
        return lon;
    }

    public Score setLongitude(double lon) {
        this.lon = lon;
        return this;
    }


}


