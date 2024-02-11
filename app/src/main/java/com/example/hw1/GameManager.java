package com.example.hw1;

import java.util.Random;

public class GameManager {
    boolean hit;
    boolean finished;
    public static final int LIVES = 3;
    public static final int COLUMNS = 3;
    public static final int ROWS = 5;
    public int num_lives = LIVES;
    public boolean[][] stones;
    public boolean[] lives;
    private int carPosition;

    public GameManager(){
        carPosition = 1;
        stones = new boolean[ROWS][COLUMNS];

    }
    public void reduceLife() {num_lives--;}
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(COLUMNS);
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setNum_lives(int num_lives) {
        this.num_lives = num_lives;
    }

    public void setCarPosition(int carPosition) {
        this.carPosition = carPosition;
    }
    private static int currentRow = ROWS-1;
    private static int currentCol = 0;
    private boolean firstStone = false;
    // in this function every time i update the location of one stone,
    // so this is like a delayed for loop.
    public void updateGame(){
        if(!firstStone){
            spawnNewStone();
            firstStone = true;
        }
        int i = currentRow;
        int j = currentCol;
        System.out.println("["+ i + "," + j +"}");
        if( stones[i][j] && i == ROWS-1){
            stones[i][j] = false;
            if(j == carPosition){
                carHit();
            }
        }else if(i != ROWS-1){
            stones[i+1][j]=stones[i][j];
            stones[i][j] = false;
        }
        currentCol++;
        if(j == COLUMNS-1){
            currentCol = 0;
            currentRow--;
        }
        if(i == 0 && j == COLUMNS-1){
            currentRow = ROWS-1;
            spawnNewStone();
        }
    }

    private void spawnNewStone(){
        // for making new stone
        int random = getRandom();
        for(int i = 0 ; i < COLUMNS; i++){
            if(random == i)
                stones[0][i] = true;
            else
                stones[0][i] = false;
        }
    }

    public void carHit(){
        hit = true;
        reduceLife();
        if(num_lives == 0)
            finished = true;
    }
}
