package com.tdt4240.catchgame.Controllers;

import android.content.Context;
import android.media.MediaPlayer;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import com.tdt4240.catchgame.Model.Sprites;
import com.tdt4240.catchgame.R;
import com.tdt4240.catchgame.View.GameView;


public class SinglePlayerActivity extends AppCompatActivity {

    private String difficulty;
    MediaPlayer backgroundMusic;
    public String avatar;

    public SinglePlayerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("intentAvatarCrocodile"));

        /*if(getIntent().hasExtra("avatar")){
            this.avatar = getIntent().getStringExtra("avatar");
        }
        if(getIntent().getStringExtra("avatar") == null){
            this.avatar = Sprites.MONKEY.toString();
        }*/
        setContentView(new GameView(this, this));
        this.difficulty = getIntent().getStringExtra("difficulty");

        this.backgroundMusic = MediaPlayer.create(this, R.raw.test_song);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(1, 1);



    }
    //help method to receive intent without starting the activity
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            avatar = intent.getStringExtra("avatar");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        this.backgroundMusic.start();
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public String getAvatar(){ return this.avatar;}

    @Override
    protected void onPause() {
        super.onPause();
        this.backgroundMusic.release();
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
