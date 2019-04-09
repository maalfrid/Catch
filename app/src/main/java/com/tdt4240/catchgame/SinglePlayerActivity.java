package com.tdt4240.catchgame;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.SoundPool;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

public class SinglePlayerActivity extends AppCompatActivity {

    private String difficulty;
    private String gametype;
    MediaPlayer backgroundMusic;


    public SinglePlayerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this, this));
        this.difficulty = getIntent().getStringExtra("difficulty");
        this.gametype = getIntent().getStringExtra("gametype");
        this.backgroundMusic = MediaPlayer.create(this, R.raw.test_song);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(1, 1);


    }

    @Override
    protected void onStart() {
        super.onStart();
        this.backgroundMusic.start();
    }

    public String getDifficulty(){
        return this.difficulty;
    }

    public String getGametype(){ return this.gametype;}

    @Override
    protected void onPause(){
        super.onPause();
        this.backgroundMusic.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.backgroundMusic = MediaPlayer.create(this, R.raw.test_song);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(1, 1);
    }

    //Called if game exit or game over
    @Override
    protected void onStop() {
        super.onStop();
        startActivity(new Intent(this, MenuActivity.class));
        this.backgroundMusic.release();
    }

    public void backgroundMusicOn(){
        this.backgroundMusic.start();
    }

    public void backgroundMusicOff(){
        this.backgroundMusic.pause();
    }
}
