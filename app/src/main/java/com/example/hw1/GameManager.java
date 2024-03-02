package com.example.hw1;

import java.util.Random;

public class GameManager {
    boolean hit;
    boolean hitCoin;
    boolean finished;
    public static final int LIVES = 3;
    public static final int COLUMNS = 5;
    public static final int ROWS = 5;
    public int num_lives = LIVES;
    public boolean[][] stones;
    public boolean[][] coins;
    public boolean[] lives;
    private int carPosition;

    public GameManager(){
        carPosition = 2;
        stones = new boolean[ROWS][COLUMNS];
        coins = new boolean[ROWS][COLUMNS];

    }
    public void reduceLife() {num_lives--;}
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(COLUMNS);
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
    public void setHitCoin(boolean hit) {
        this.hitCoin = hit;
    }
    private static int currentStoneRow = ROWS-1;
    private static int currentStoneCol = 0;
    private boolean firstStone = false;
    private boolean firstCoin = false;
    private static int currentCoinRow = ROWS-1;
    private static int currentCoinCol = 0;
    // in this function every time i update the location of one stone,
    // so this is like a delayed for loop.
    public void updateGame(){
        updateStones();
        updateCoins();
    }
    public void updateCoins(){
        if(!firstCoin){
            spawnNewCoin();
            firstCoin = true;
        }
        int i = currentStoneRow;
        int j = currentStoneCol;
        System.out.println("["+ i + "," + j +"}");
        if( coins[i][j] && i == ROWS-1){
            coins[i][j] = false;
            if(j == carPosition){
                hitCoin = true;
            }
        }else if(i != ROWS-1){
            coins[i+1][j]=coins[i][j];
            coins[i][j] = false;
        }
        currentStoneCol++;
        if(j == COLUMNS-1){
            currentStoneCol = 0;
            currentStoneRow--;
        }
        if(i == 0 && j == COLUMNS-1){
            currentStoneRow = ROWS-1;
            spawnNewCoin();
        }
    }
    public void updateStones(){
        if(!firstStone){
            spawnNewStone();
            firstStone = true;
        }
        int i = currentStoneRow;
        int j = currentStoneCol;
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
        currentStoneCol++;
        if(j == COLUMNS-1){
            currentStoneCol = 0;
            currentStoneRow--;
        }
        if(i == 0 && j == COLUMNS-1){
            currentStoneRow = ROWS-1;
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

    private void spawnNewCoin(){
        // for making new coin
        Boolean coinCreated = false;
        while(!coinCreated) {
            int random = getRandom();
            for (int i = 0; i < COLUMNS; i++) {
                if (random == i && !stones[0][i]){
                    coins[0][i] = true;
                    coinCreated = true;
                }
                else
                    coins[0][i] = false;
            }
        }
    }

    public void carHit(){
        hit = true;
        reduceLife();
        if(num_lives == 0)
            finished = true;
    }

    public int getCarIndex(){
        return carPosition;
    }

    public int getCOLUMNS(){ return COLUMNS;}

    public void setCarIndex(int carIndex) {
        this.carPosition = carIndex;
    }

    public boolean isCoin(){ return hitCoin; }
}
