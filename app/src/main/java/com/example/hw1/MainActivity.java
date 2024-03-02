package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    final int DELAY = 50;
    private ImageView[][] game_stones;
    private ImageView[][] game_coins;
    private ImageView[] game_cars;
    private AppCompatImageButton game_BTN_left;
    private AppCompatImageButton game_BTN_right;
    private ShapeableImageView[] hearts;
    final Handler handler = new Handler();
    private GameManager gameManager;
    int score = 0;


    //SENSORS
    private SensorManager sensorManager;
    private Sensor sensor;
    private boolean sensorMode = false;
    public static final String SENSOR_MODE = "SENSOR_MODE";

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
        Utilities.hideSystemUI(this);
        assignViews();
        gameManager = new GameManager();

        sensorMode = getIntent().getExtras().getBoolean(SENSOR_MODE);
        if (sensorMode)
            moveCarBySensors();
        else{
            initButtons();
        }
        startTimer();
    }
    private void moveCarBySensors() {
        game_BTN_right.setVisibility(View.INVISIBLE);
        game_BTN_left.setVisibility(View.INVISIBLE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void initButtons() {
        game_BTN_left.setOnClickListener(v -> {
            if (gameManager.getCarIndex() != 0) {
                game_cars[gameManager.getCarIndex()].setVisibility(View.INVISIBLE);
                game_cars[gameManager.getCarIndex() - 1].setVisibility(View.VISIBLE);
                gameManager.setCarIndex(gameManager.getCarIndex() - 1);
            }
        });
        game_BTN_right.setOnClickListener(view -> {
            if(gameManager.getCarIndex() != gameManager.getCOLUMNS()-1) {
                game_cars[gameManager.getCarIndex()].setVisibility(View.INVISIBLE);
                game_cars[gameManager.getCarIndex() + 1].setVisibility(View.VISIBLE);
                gameManager.setCarIndex(gameManager.getCarIndex() + 1);
            }
        });
    }

    private void assignViews(){
        game_BTN_left = findViewById(R.id.game_left);
        game_BTN_right = findViewById(R.id.game_right);

        initCoinArr();
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
                findViewById(R.id.game_car1),
                findViewById(R.id.game_car2),
                findViewById(R.id.game_car3),
                findViewById(R.id.game_car4),
                findViewById(R.id.game_car5),
        };
    }
    private void initStones(){
        game_stones = new ImageView[][]{
                {findViewById(R.id.game_stone1),
                        findViewById(R.id.game_stone2),
                        findViewById(R.id.game_stone3),
                        findViewById(R.id.game_stone4),
                        findViewById(R.id.game_stone5)},
                {findViewById(R.id.game_stone6),
                        findViewById(R.id.game_stone7),
                        findViewById(R.id.game_stone8),
                        findViewById(R.id.game_stone9),
                        findViewById(R.id.game_stone10)},
                {findViewById(R.id.game_stone11),
                        findViewById(R.id.game_stone12),
                        findViewById(R.id.game_stone13),
                        findViewById(R.id.game_stone14),
                        findViewById(R.id.game_stone15)},
                {findViewById(R.id.game_stone16),
                        findViewById(R.id.game_stone17),
                        findViewById(R.id.game_stone18),
                        findViewById(R.id.game_stone19),
                        findViewById(R.id.game_stone20)},
                {findViewById(R.id.game_stone21),
                        findViewById(R.id.game_stone22),
                        findViewById(R.id.game_stone23),
                        findViewById(R.id.game_stone24),
                        findViewById(R.id.game_stone25)}
        };
    }

    private void initCoinArr(){
        game_coins = new ImageView[][]{
                {findViewById(R.id.game_coin1),
                        findViewById(R.id.game_coin2),
                        findViewById(R.id.game_coin3),
                        findViewById(R.id.game_coin4),
                        findViewById(R.id.game_coin5)},
                {findViewById(R.id.game_coin6),
                        findViewById(R.id.game_coin7),
                        findViewById(R.id.game_coin8),
                        findViewById(R.id.game_coin9),
                        findViewById(R.id.game_coin10)},
                {findViewById(R.id.game_coin11),
                        findViewById(R.id.game_coin12),
                        findViewById(R.id.game_coin13),
                        findViewById(R.id.game_coin14),
                        findViewById(R.id.game_coin15)},
                {findViewById(R.id.game_coin16),
                        findViewById(R.id.game_coin17),
                        findViewById(R.id.game_coin18),
                        findViewById(R.id.game_coin19),
                        findViewById(R.id.game_coin20)},
                {findViewById(R.id.game_coin21),
                        findViewById(R.id.game_coin22),
                        findViewById(R.id.game_coin23),
                        findViewById(R.id.game_coin24),
                        findViewById(R.id.game_coin25)}
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

    private void refreshCoinUI() {
        for (int i = 0; i < gameManager.ROWS; i++) {
            for (int j = 0; j < gameManager.COLUMNS; j++) {
                if (gameManager.coins[i][j]) {
                    game_coins[i][j].setVisibility(View.VISIBLE);
                } else {
                    game_coins[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void refreshGame(){
        gameManager.updateGame();
        if(gameManager.finished){
            refreshHearts();
            refreshStones();
            Utilities.vibrate(this);
            Utilities.toast(this, "Game Over");
            Intent gameOverIntent = new Intent(MainActivity.this, GameOverActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("score", score);
            gameOverIntent.putExtras(bundle);
            startActivity(gameOverIntent);
            stopTimer();
            finish();
        }
        if(gameManager.hit){
            refreshHearts();
            Toast
                    .makeText(this, ":(", Toast.LENGTH_SHORT)
                    .show();
            Utilities.vibrate(this);
            gameManager.setHit(false);
        }
        if (gameManager.isCoin()) {
            score+=1000;
            Utilities.toast(this,"Coins +1000");
            Utilities.vibrate(this);
            gameManager.setHitCoin(false);
        }
        refreshStones();
        refreshCoinUI();
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



    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            moveCarBySensors(sensorEvent.values[0]);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    private void moveCarBySensors(float x) {
        game_cars[gameManager.getCarIndex()].setVisibility(View.INVISIBLE);
        if (x < -4) {
            gameManager.setCarIndex(4);
        }  else if (-3.5 < x && x < -1.5) {
            gameManager.setCarIndex(3);
        }else if (-1 < x && x< 1) {
            gameManager.setCarIndex(2);
        } else if (1.5 < x && x < 3.5) {
            gameManager.setCarIndex(1);
        } else if (4 < x) {
            gameManager.setCarIndex(0);
        }
        game_cars[gameManager.getCarIndex()].setVisibility(View.VISIBLE);
    }

}