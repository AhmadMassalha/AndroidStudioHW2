package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;


public class MenuGame extends AppCompatActivity {

    private MaterialButton menu_button;
    private MaterialButton menu_sensor;
    private MaterialButton menu_highScores;
    private AppCompatImageView menu_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_game);
        Utilities.hideSystemUI(this);
        initViews();
        clicked();
        startDB();
    }

    private void startDB(){
        MSPV3.initHelper(this);
        String js = MSPV3.getMe().getString("MY_DB", "");
        ScoreDB mdb = new Gson().fromJson(js, ScoreDB.class);
        if (mdb == null) {
            ScoreDB myDB = new ScoreDB();
            String json = new Gson().toJson(myDB);
            MSPV3.getMe().putString("MY_DB", json);
        }
    }
    private void initViews() {
        menu_highScores = findViewById(R.id.menu_highScores);
        menu_background = findViewById(R.id.menu_background);
        menu_sensor = findViewById(R.id.menu_sensor);
        menu_button = findViewById(R.id.menu_button);
    }
    private void clicked() {
        menu_button.setOnClickListener(view -> startGame(false));
        menu_sensor.setOnClickListener(view -> startGame(true));
        menu_highScores.setOnClickListener(view -> scoresScreen());
    }
    public void startGame(boolean useSensors) {
        Intent gameIntent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(MainActivity.SENSOR_MODE, useSensors);
        gameIntent.putExtras(bundle);
        startActivity(gameIntent);
    }
    public void scoresScreen() {
        Intent ScoreIntent = new Intent(this,topTenActivity.class);
        startActivity(ScoreIntent);
    }
}