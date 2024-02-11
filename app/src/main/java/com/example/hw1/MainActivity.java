package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    final int DELAY = 50;
    private AppCompatImageView background_image;
    private ImageView[][] game_stones;
    private ImageView[] game_cars;
    private AppCompatImageButton game_BTN_left;
    private AppCompatImageButton game_BTN_right;
    private ShapeableImageView[] hearts;
    final Handler handler = new Handler();
    private GameManager gameManager;


    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        gameManager = new GameManager();
        initButtons();
        startTimer();
    }

    private void initButtons() {
        game_BTN_left.setOnClickListener(v -> {
            if (game_cars[1].isShown()) {
                game_cars[0].setVisibility(View.VISIBLE);
                game_cars[1].setVisibility(View.INVISIBLE);
                gameManager.setCarPosition(0);
            } else if (game_cars[2].isShown()) {
                game_cars[1].setVisibility(View.VISIBLE);
                game_cars[2].setVisibility(View.INVISIBLE);
                gameManager.setCarPosition(1);
            }
        });
        game_BTN_right.setOnClickListener(view -> {
            if (game_cars[0].isShown()) {
                game_cars[0].setVisibility(View.INVISIBLE);
                game_cars[1].setVisibility(View.VISIBLE);
                game_cars[2].setVisibility(View.INVISIBLE);
                gameManager.setCarPosition(1);
            } else if (game_cars[1].isShown()) {
                game_cars[0].setVisibility(View.INVISIBLE);
                game_cars[1].setVisibility(View.INVISIBLE);
                game_cars[2].setVisibility(View.VISIBLE);
                gameManager.setCarPosition(2);
            }
        });
    }

    private void assignViews(){
        game_BTN_left = findViewById(R.id.game_left);
        game_BTN_right = findViewById(R.id.game_right);

        initStones();
        initHearts();
        initCarArray();
    }
    private void initHearts(){
        hearts = new ShapeableImageView[]{
                findViewById(R.id.game_heart3),
                findViewById(R.id.game_heart2),
                findViewById(R.id.game_heart1),
        };
    }
    private void initCarArray(){
        game_cars = new ImageView[]{
                findViewById(R.id.game_carLeft),
                findViewById(R.id.game_carCenter),
                findViewById(R.id.game_carRight),
        };
    }
    private void initStones(){
        game_stones = new ImageView[][]{
                {       findViewById(R.id.game_stone1),
                        findViewById(R.id.game_stone2),
                        findViewById(R.id.game_stone3)},
                {       findViewById(R.id.game_stone4),
                        findViewById(R.id.game_stone5),
                        findViewById(R.id.game_stone6)},
                {       findViewById(R.id.game_stone7),
                        findViewById(R.id.game_stone8),
                        findViewById(R.id.game_stone9)},
                {       findViewById(R.id.game_stone10),
                        findViewById(R.id.game_stone11),
                        findViewById(R.id.game_stone12)},
                {       findViewById(R.id.game_stone13),
                        findViewById(R.id.game_stone14),
                        findViewById(R.id.game_stone15)}
        };
    }

    private void refreshHearts(){
        for(int i = gameManager.LIVES ; i > gameManager.num_lives; i--){
            hearts[i-1].setVisibility(View.INVISIBLE);
        }
    }

    private void refreshStones(){
        for (int i =0;i<gameManager.ROWS;i++){
            for (int j=0;j<gameManager.COLUMNS;j++){
                if(gameManager.stones[i][j]) {
                    game_stones[i][j].setVisibility(View.VISIBLE);
                }
                else{
                    game_stones[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void refreshGame(){
        gameManager.updateGame();
        if(gameManager.finished){
            refreshHearts();
            refreshStones();
            vibrate();
            Toast
                    .makeText(this, "Game Over", Toast.LENGTH_SHORT)
                    .show();
            stopTimer();
            finish();
        }
        if(gameManager.hit){
            refreshHearts();
            Toast
                    .makeText(this, ":(", Toast.LENGTH_SHORT)
                    .show();
            vibrate();
            gameManager.setHit(false);
        }
        refreshStones();
    }
    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }
    Runnable runnable = new Runnable() {
        public void run() {
            handler.postDelayed(this, DELAY);
            refreshGame();


        }
    };
    private void startTimer()
    {
        handler.postDelayed(runnable, DELAY);
        System.out.println("Started");

    }
    private void stopTimer() {
        handler.removeCallbacks(runnable);
    }
}