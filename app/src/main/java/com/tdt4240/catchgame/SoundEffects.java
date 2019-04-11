package com.tdt4240.catchgame;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundEffects {

    SoundPool soundPool;
    SoundPool.Builder soundPoolBuilder;

    AudioAttributes attributes;
    AudioAttributes.Builder attributesBuilder;
    AudioManager audioManager;


    int soundID_bite;
    int soundID_smack;
    int soundID_cough;
    int soundID_powerup;
    int soundID_levelup;
    int soundID_button;


    public SoundEffects(){
        attributesBuilder = new AudioAttributes.Builder();
        attributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
        attributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        attributes = attributesBuilder.build();

        soundPoolBuilder = new SoundPool.Builder();
        soundPoolBuilder.setAudioAttributes(attributes);
        soundPool = soundPoolBuilder.build();

        soundID_bite = soundPool.load(CoreGame.getContext(), R.raw.bite, 1);

        soundID_smack = soundPool.load(CoreGame.getContext(), R.raw.smack, 1);

        soundID_cough = soundPool.load(CoreGame.getContext(), R.raw.cough, 1);

        soundID_powerup = soundPool.load(CoreGame.getContext(), R.raw.powerup, 1);

        soundID_levelup = soundPool.load(CoreGame.getContext(), R.raw.music_box, 1);

        soundID_button = soundPool.load(CoreGame.getContext(), R.raw.buttonclick, 1);

        audioManager = (AudioManager) CoreGame.getContext().getSystemService(Context.AUDIO_SERVICE);

        soundPool.setVolume(soundID_bite,50.0f, 50.0f);
        soundPool.setVolume(soundID_cough,50.0f, 50.0f);
        soundPool.setVolume(soundID_powerup,50.0f, 50.0f);
        soundPool.setVolume(soundID_smack,50.0f, 50.0f);
        soundPool.setVolume(soundID_levelup,50.0f, 50.0f);
        soundPool.setVolume(soundID_button,50.0f, 50.0f);
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

    public void levelUpSound(){
        soundPool.play(soundID_levelup, 1, 1, 0, 0, 1);
    }

    public void buttonClickSound(){
        soundPool.play(soundID_button, 1, 1, 0, 0, 1);
    }

    public void volumeOff(){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);

    }

    public void volumeOn(){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
    }






}
