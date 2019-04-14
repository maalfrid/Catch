package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class BadFood extends FallingObject {


    public BadFood(Bitmap bitmap, ObjectType object) {
        super(bitmap, object);
    }

    @Override
    public void applyObjectEatenEffect(CharacterSprite characterSprite) {
        characterSprite.removeScore(this.objectScore);
        setSound("cough");
    }

    @Override
    public void applyObjectOnFloorEffect(CharacterSprite characterSprite) {
        setSound("smack");
    }
}
