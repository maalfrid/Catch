package com.tdt4240.catchgame.Model;

import android.graphics.Bitmap;

public class GoodFood extends FallingObject {

    protected GoodFood(Bitmap bitmap, ObjectType object) {
        super(bitmap, object);
    }

    @Override
    public void applyObjectEatenEffect(CharacterSprite characterSprite) {
        characterSprite.addScore(this.objectScore);
        setSound("bite");
    }

    @Override
    public void applyObjectOnFloorEffect(CharacterSprite characterSprite) {
        characterSprite.removeLife();
        setSound("smack");
    }
}
