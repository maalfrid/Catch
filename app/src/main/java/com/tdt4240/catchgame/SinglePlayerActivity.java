package com.tdt4240.catchgame;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.SoundPool;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SinglePlayerActivity extends AppCompatActivity {
    private String difficulty;

    MediaPlayer backgroundMusic;
    /*SoundPool soundPool;
    SoundPool.Builder soundPoolBuilder;

    AudioAttributes attributes;
    AudioAttributes.Builder attributesBuilder;

    int soundID_crunch;*/

    public SinglePlayerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this, this));
        this.difficulty = getIntent().getStringExtra("difficulty");

        backgroundMusic = MediaPlayer.create(this, R.raw.test_song);

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(2.0f, 2.0f);

        /*attributesBuilder = new AudioAttributes.Builder();
        attributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
        attributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        attributes = attributesBuilder.build();

        soundPoolBuilder = new SoundPool.Builder();
        soundPoolBuilder.setAudioAttributes(attributes);
        soundPool = soundPoolBuilder.build();

        soundID_crunch = soundPool.load(this, R.raw.crunch, 1);*/


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
