package com.tdt4240.catchgame;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SinglePlayerDifficulty extends AppCompatActivity implements goBackFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_difficulty);

        //Buttons and navigation
        Button easyBtn = findViewById(R.id.btn_easy);
        Button mediumBtn = findViewById(R.id.btn_medium);
        Button hardBtn = findViewById(R.id.btn_hard);

        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SinglePlayerActivity.class));
                //Tenker typ noe som dette skal skje her (og i de andre metodene under:


                //player.setDifficulty(easy);
                //singlePlayerGame.setSpeed(low);
                //gsm.setPlaystate(easy);
                //gsm.playstate = easy;
            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             startActivity(new Intent(view.getContext(), SinglePlayerActivity.class));
                                         }
                                     });

        hardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(view.getContext(), SinglePlayerActivity.class));
                }
            });

    }

    //method needed for the fragment, should be empty for now
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
