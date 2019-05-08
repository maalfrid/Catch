package com.tdt4240.catchgame.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tdt4240.catchgame.R;

public class Loading extends AppCompatActivity {

    /** Duration of wait **/
    private final int LOADING_DISPLAY_LENGTH = 2500;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Loading.this, MenuActivity.class));

                finish();
            }
        }, LOADING_DISPLAY_LENGTH);

    }

}
