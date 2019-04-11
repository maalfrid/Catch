package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class BadFood extends FallingObject {


    public BadFood(Bitmap bitmap, int objectScore, CoreGame coreGame){
        super(bitmap, objectScore, coreGame);
    }

    @Override
    public void applyObjectEatenEffect(){
        coreGame.getSoundEffect().coughSound();
    }

    @Override
    public void applyObjectOnFloorEffect(){
        coreGame.getSoundEffect().smackSound();
    }
}
