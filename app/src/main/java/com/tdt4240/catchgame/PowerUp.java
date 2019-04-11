package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class PowerUp extends FallingObject {

    private int powerUpID;

    public PowerUp(Bitmap bitmap, int objectScore, int powerUpID){
        super(bitmap, objectScore);
        this.powerUpID = powerUpID;
    }


    @Override
    public void applyObjectEatenEffect(){
        if (powerUpID == 0){
            //DO something
        }
        else if (powerUpID == 1){
            //DO something else
        }

        setSound("powerup");
    }

    @Override
    public void applyObjectOnFloorEffect(){
        setSound("smack");
    }

}
