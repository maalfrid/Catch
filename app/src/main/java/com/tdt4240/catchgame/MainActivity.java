package com.tdt4240.catchgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons and navigation

        //Play button
        Button playBtn = findViewById(R.id.btn_play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(new Intent(v.getContext(), PlayMenuActivity.class));
            }
        });

        //Settings button
        Button settingsBtn = findViewById(R.id.btn_settings);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(new Intent(v.getContext(), SettingsActivity.class));
            }
        });

        //Score
        Button scoreBtn = findViewById(R.id.btn_score);

        //Rules
        Button ruleBtn = findViewById(R.id.btn_rules);





      
    }
}
