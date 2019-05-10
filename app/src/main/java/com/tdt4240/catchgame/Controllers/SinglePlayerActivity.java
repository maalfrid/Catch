package com.tdt4240.catchgame.Controllers;

import android.media.MediaPlayer;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tdt4240.catchgame.R;
import com.tdt4240.catchgame.View.GameView;


public class SinglePlayerActivity extends AppCompatActivity {

    private String difficulty;
    MediaPlayer backgroundMusic;
    private String avatar;
    private boolean backgroundsoundOn;
    private boolean soundEffectsOn;
    private String background;


    public SinglePlayerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.background = getIntent().getStringExtra("background");
        setContentView(new GameView(this, this));
        this.difficulty = getIntent().getStringExtra("difficulty");
        this.avatar = getIntent().getStringExtra("avatar");
        this.backgroundsoundOn = getIntent().getExtras().getBoolean("backgroundSound");
        this.soundEffectsOn = getIntent().getExtras().getBoolean("soundEffects");
        this.backgroundMusic = MediaPlayer.create(this, R.raw.test_song);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(1, 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(this.backgroundsoundOn){
            this.backgroundMusic.start();
        }
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public boolean getBackgroundsoundOn(){
        return this.backgroundsoundOn;
    }

    public boolean getSoundEffectsOn(){
        return this.soundEffectsOn;
    }

    public String getBackground(){
        return this.background;
    }

    public String getAvatar(){ return this.avatar;}

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*this.backgroundMusic = MediaPlayer.create(this, R.raw.test_song);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(1, 1);*/
    }

    //Called if game exit or game over
    @Override
    protected void onStop() {
        super.onStop();
        startActivity(new Intent(this, MenuActivity.class));
         this.backgroundMusic.release();

    }

    public void backgroundMusicOn() {
        this.backgroundMusic.start();
    }

    public void backgroundMusicOff() {
        this.backgroundMusic.pause();
    }
}
