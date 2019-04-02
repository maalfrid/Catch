package com.tdt4240.catchgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SinglePlayerActivity extends AppCompatActivity {
    private String difficulty;
    private String gametype;


    public SinglePlayerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this, this));
        this.difficulty = getIntent().getStringExtra("difficulty");
        this.gametype = getIntent().getStringExtra("gametype");


    }

    @Override
    protected void onPause(){
        MainThread.setRunning(false);
        super.onPause();
    }

    public String getDifficulty(){
        return this.difficulty;
    }
    public String getGametype(){ return this.gametype;}
}
