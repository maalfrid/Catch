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
        backgroundMusic.setVolume(1, 1);


    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundMusic.start();
    }

    public String getDifficulty(){
        return this.difficulty;
    }


    @Override
    protected void onPause(){
        super.onPause();
        backgroundMusic.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //Called if game exit or game over
    @Override
    protected void onStop() {
        super.onStop();
        startActivity(new Intent(this, MenuActivity.class));
        backgroundMusic.release();
    }

    public void backgroundMusicOn(){
        backgroundMusic.start();
    }

    public void backgroundMusicOff(){
        backgroundMusic.pause();
    }
}
