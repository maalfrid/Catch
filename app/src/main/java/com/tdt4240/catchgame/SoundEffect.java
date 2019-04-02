package com.tdt4240.catchgame;

import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundEffect {

    SoundPool soundPool;
    SoundPool.Builder soundPoolBuilder;

    AudioAttributes attributes;
    AudioAttributes.Builder attributesBuilder;

    int soundID_crunch;
    int soundID_smack;


    public SoundEffect(){
        attributesBuilder = new AudioAttributes.Builder();
        attributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
        attributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        attributes = attributesBuilder.build();

        soundPoolBuilder = new SoundPool.Builder();
        soundPoolBuilder.setAudioAttributes(attributes);
        soundPool = soundPoolBuilder.build();

        soundID_crunch = soundPool.load(CoreGame.getContext(), R.raw.crunch, 1);
        //soundPool.setVolume(1,50.0f, 50.0f); //denne er fortsatt veldig lav

        soundID_smack = soundPool.load(CoreGame.getContext(), R.raw.crunch, 1);
    }


    public void crunchSound(){
        soundPool.play(soundID_crunch, 3, 3, 0, 0, 1);
    }


    public void smackSound(){
        soundPool.play(soundID_smack, 1, 1, 0, 0, 1);
    }





}
