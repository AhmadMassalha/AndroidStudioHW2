package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

public class topTenActivity extends AppCompatActivity {

    private ScoresFragment fragmentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten);
        Utilities.hideSystemUI(this);
        fragmentScore = new ScoresFragment();
        fragmentScore.setActivity(this);
        fragmentScore.setCallBackList(callBack_list);
        getSupportFragmentManager().beginTransaction().add(R.id.frameScores, fragmentScore)
                .commit();

    }

    CallBack_List callBack_list = new CallBack_List() {
        @Override
        public void zoom(double lat, double lon) {
            // Didn't have time to finish     }
        };
    };
}