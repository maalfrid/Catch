package com.tdt4240.catchgame;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SinglePlayerActivity extends AppCompatActivity implements pauseFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

        //Buttons and navigation - do not work with fragments yet
       /* Fragment pauseBtn = findViewById(R.id.pauseFragment);

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SinglePlayerDifficulty.class));
            }
        });
*/

    }

    //method needed for the fragment, should be empty for now
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
