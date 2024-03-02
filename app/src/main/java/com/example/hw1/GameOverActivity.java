package com.example.hw1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;




import java.util.Comparator;


public class GameOverActivity extends AppCompatActivity {


    private TextView gameOver_score;
    private TextView gameOver_result;
    private EditText gameOver_name;
    private MaterialButton gameOver_saveRecord;
    private MaterialButton gameOver_back;

    //private GPSTracker gpsService;

    private ScoreDB scoreDB;

    private String playerName;
    private int score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Utilities.hideSystemUI(this);

        score = getIntent().getExtras().getInt("score");

        findView();
        gameOver_result.setText("Score: " + score);
        initView();

    }

    private void initView() {
        gameOver_back.setOnClickListener(view -> finish());
        gameOver_saveRecord.setOnClickListener(view -> {
            double latitude = 0.0;
            double longitude = 0.0;
            playerName = gameOver_name.getText().toString();
            /* gpsService = new GPSTracker(Activity_GameOver.this);
            if (gpsService.canGetLocation()) {
                latitude = gpsService.getLatitude();
                longitude = gpsService.getLongitude();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            } else {
                gpsService.showSettingsAlert();
            } */
            // * End of Location Service
            gameOver_name.setVisibility(View.INVISIBLE);
            gameOver_saveRecord.setVisibility(View.INVISIBLE);
            saveRecord(playerName, score, longitude, latitude);
        });
    }

    private void findView() {
        gameOver_result = findViewById(R.id.gameOver_result);
        gameOver_name = findViewById(R.id.gameOver_name);
        gameOver_score = findViewById(R.id.gameOver_score);
        gameOver_saveRecord = findViewById(R.id.gameOver_saveRecord);
        gameOver_back = findViewById(R.id.gameOver_back);

        gameOver_back.setOnClickListener(view -> finish());
        gameOver_saveRecord.setOnClickListener(v -> {
            double latitude = 0.0;
            double longitude = 0.0;
            playerName = gameOver_name.getText().toString();
            /*
            if (gpsService.canGetLocation()) {
                latitude = gpsService.getLatitude();
                longitude = gpsService.getLongitude();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            } else {
                gpsService.showSettingsAlert();
            }
            */
            gameOver_name.setVisibility(View.INVISIBLE);
            gameOver_saveRecord.setVisibility(View.INVISIBLE);
            saveRecord(playerName, score, longitude, latitude);
        });


    }

    private void saveRecord(String player_name, int score, double longitude, double latitude) {
        String js = MSPV3.getMe().getString("MY_DB", "");
        scoreDB = new Gson().fromJson(js, ScoreDB.class);

        scoreDB.getRecords().add(new Score()
                .setName(player_name)
                .setScore(score)
                .setLatitude(latitude)
                .setLongitude(longitude)
        );

        scoreDB.getRecords().sort(new SortByScore());
        String json = new Gson().toJson(scoreDB);
        MSPV3.getMe().putString("MY_DB", json);
    }
}

class SortByScore implements Comparator<Score> {
    @Override
    public int compare(Score rec1, Score rec2) {
        return rec2.getScore() - rec1.getScore();
    }
}
