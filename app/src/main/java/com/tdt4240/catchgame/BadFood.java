package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class BadFood extends FallingObject {


    public BadFood(Bitmap bitmap, int objectScore){
        super(bitmap, objectScore);
    }

    @Override
    public void applyObjectEatenEffect(){
        setSound("cough");
    }

    @Override
    public void applyObjectOnFloorEffect(){
        setSound("smack");
    }
}
