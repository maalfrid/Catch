package com.tdt4240.catchgame;

import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundEffect {

    SoundPool soundPool;
    SoundPool.Builder soundPoolBuilder;

    AudioAttributes attributes;
    AudioAttributes.Builder attributesBuilder;

    int soundID_bite;
    int soundID_smack;
    int soundID_cough;
    int soundID_powerup;


    public SoundEffect(){
        attributesBuilder = new AudioAttributes.Builder();
        attributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
        attributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        attributes = attributesBuilder.build();

        soundPoolBuilder = new SoundPool.Builder();
        soundPoolBuilder.setAudioAttributes(attributes);
        soundPool = soundPoolBuilder.build();

        soundID_bite = soundPool.load(CoreGame.getContext(), R.raw.bite, 1);
        //soundPool.setVolume(1,50.0f, 50.0f); //denne er fortsatt veldig lav

        soundID_smack = soundPool.load(CoreGame.getContext(), R.raw.smack, 1);

        soundID_cough = soundPool.load(CoreGame.getContext(), R.raw.cough, 1);

        soundID_powerup = soundPool.load(CoreGame.getContext(), R.raw.powerup, 1);
    }


    public void biteSound(){
        soundPool.play(soundID_bite, 1, 1, 0, 0, 1);
    }


    public void smackSound(){
        soundPool.play(soundID_smack, 1, 1, 0, 0, 1);
    }

    public void coughSound(){
        soundPool.play(soundID_cough, 1, 1, 0, 0, 1);
    }

    public void powerupSound(){
        soundPool.play(soundID_powerup, 1, 1, 0, 0, 1);
    }





}
