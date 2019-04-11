package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class GoodFood extends FallingObject {

    public GoodFood(Bitmap bitmap, int objectScore){
        super(bitmap, objectScore);
    }

    @Override
    public void applyObjectEatenEffect(){
        setSound("bite");
    }

    @Override
    public void applyObjectOnFloorEffect(){
        setSound("smack");
    }
}
