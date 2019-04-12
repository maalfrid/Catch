package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class PowerUp extends FallingObject {

    private int powerUpID;

    public PowerUp(Bitmap bitmap, ObjectType object){
        super(bitmap, object);
    }


    @Override
    public void applyObjectEatenEffect(CharacterSprite characterSprite){
        if (objectType == ObjectType.BEETLE){
            //DO something
        }
        else if (objectType == ObjectType.LADYBUG){
            //DO something else
        }
        else if (objectType == ObjectType.STARBEETLE){

        }

        setSound("powerup");
    }

    @Override
    public void applyObjectOnFloorEffect(CharacterSprite characterSprite){
        setSound("smack");
    }

}
