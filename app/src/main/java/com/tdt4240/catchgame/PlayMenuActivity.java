package com.tdt4240.catchgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayMenuActivity extends AppCompatActivity {

    /*
    * TODO @Cathrine @Ingrid:
    * Menu w/ buttons for:
    * - Single player
    * - Multiplayer
    * - Game rules
    *
    * For the single player button:
    * The single player button should send the user
    * to the single player menu (issue #4).
    * I have not created a class for this yet.
    * You should find out whether it is appropriate
    * to make an Activity or have Fragments in the
    * activity.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_menu);

        //Buttons and navigation
        Button singleBtn = findViewById(R.id.btn_single);
        Button mulitBtn = findViewById(R.id.btn_multi);

        singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SinglePlayerActivity.class));
            }
        });

        mulitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MultiPlayerActivity.class));
            }
        });
    }
}
