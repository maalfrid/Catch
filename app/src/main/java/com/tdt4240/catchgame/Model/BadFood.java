package com.tdt4240.catchgame.Model;

import android.graphics.Bitmap;

public class BadFood extends FallingObject {


    protected BadFood(Bitmap bitmap, ObjectType object) {
        super(bitmap, object);
    }

    @Override
    public void applyObjectEatenEffect(CharacterSprite characterSprite) {
        characterSprite.setCharacterSpriteImage(characterSprite.getCharacterSprite().deadImageID);
        characterSprite.removeScore(this.objectScore);
        setSound("cough");
    }

    @Override
    public void applyObjectOnFloorEffect(CharacterSprite characterSprite) {
        setSound("smack");
    }
}
