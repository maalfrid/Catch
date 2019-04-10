package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class PowerUp extends FallingObject {

    public PowerUp(Bitmap bitmap, int objectScore, CoreGame coreGame){
        super(bitmap, objectScore, coreGame);
    }

    @Override
    public void applyObjectEatenEffect(){
        coreGame.getSoundEffect().powerupSound();
    }

    @Override
    public void applyObjectOnFloorEffect(){
        coreGame.getSoundEffect().smackSound();
    }

}
