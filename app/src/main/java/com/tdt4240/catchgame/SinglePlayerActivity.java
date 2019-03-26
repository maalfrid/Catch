package com.tdt4240.catchgame;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class SinglePlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

    }


 

    @Override
    protected void onPause(){
        MainThread.setRunning(false);
        super.onPause();
    }

}
