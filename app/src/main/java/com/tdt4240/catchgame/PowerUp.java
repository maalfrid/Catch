package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class PowerUp extends FallingObject {

    private int powerUpID;

    public PowerUp(Bitmap bitmap, int objectScore, int powerUpID, CoreGame coreGame){
        super(bitmap, objectScore, coreGame);
        this.powerUpID = powerUpID;
    }

    public void applyPowerUpEffect(CharacterSprite characterSprite){
        if (powerUpID == 0){
            //DO something
        }
        else if (powerUpID == 1){
            //DO something else
        }
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
