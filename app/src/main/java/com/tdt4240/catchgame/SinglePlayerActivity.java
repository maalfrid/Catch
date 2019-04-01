package com.tdt4240.catchgame;

import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SinglePlayerActivity extends AppCompatActivity {
    private String difficulty;

    MediaPlayer backgroundMusic;

    public SinglePlayerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this, this));
        this.difficulty = getIntent().getStringExtra("difficulty");

        backgroundMusic = MediaPlayer.create(this, R.raw.test_song);


        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(10.0f, 3.0f);
        

    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundMusic.start();
    }


    @Override
    protected void onPause(){
        MainThread.setRunning(false);
        super.onPause();
        backgroundMusic.release();
    }

    public String getDifficulty(){
        return this.difficulty;
    }
}
