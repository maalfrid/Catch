package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class GoodFood extends FallingObject {

    public GoodFood(Bitmap bitmap, int objectScore, CoreGame coreGame){
        super(bitmap, objectScore, coreGame);
    }

    @Override
    public void applyObjectEatenEffect(){
        coreGame.getSoundEffect().biteSound();
    }

    @Override
    public void applyObjectOnFloorEffect(){
        coreGame.getSoundEffect().smackSound();
    }
}
