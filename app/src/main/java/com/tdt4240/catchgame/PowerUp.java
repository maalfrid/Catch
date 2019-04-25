package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class PowerUp extends FallingObject {

    private int multiPowerup;

    public PowerUp(Bitmap bitmap, ObjectType object) {
        super(bitmap, object);
    }

    //TODO: Implement powerup-logic for single and multiplayer in this class, based on the restructure of the code and endless-mode.

    @Override
    public void applyObjectEatenEffect(CharacterSprite characterSprite) {
        if (objectType == ObjectType.BEETLE) {
        } else if (objectType == ObjectType.LADYBUG) {
            characterSprite.addLife();
        } else if (objectType == ObjectType.STARBEETLE) {
        }
        characterSprite.addScore(this.objectScore);
        setSound("powerup");
    }

    @Override
    public void applyObjectOnFloorEffect(CharacterSprite characterSprite) {
        setSound("smack");
    }

    @Override
    public void applyGameChange(CoreGame coreGame, long updateTime){
        if (objectType == ObjectType.BEETLE) {
            coreGame.getFallingObjectFactory().setObjectScale(0,0.25);
            coreGame.getFallingObjectFactory().setObjectScale(1,0.05);
            coreGame.setBeetleDuration(updateTime + 10000);
            coreGame.setMultiPowerupSent(1);
        } else if (objectType == ObjectType.LADYBUG) {
            coreGame.setMultiPowerupSent(3);
        } else if (objectType == ObjectType.STARBEETLE) {
            coreGame.getFallingObjectFactory().setOnlyGood(true);
            coreGame.setStarBeetleDuration(updateTime + 10000);
            coreGame.setMultiPowerupSent(2);
        }
    }

    @Override
    public String gameChangeMessage(){
        if(objectType == ObjectType.BEETLE) {
            return "Beetle!\nLarge good objects, small bad objects";
        }
        else if(objectType == ObjectType.LADYBUG) {
            return "Ladybug!\nYou get one extra life";
        }
        else {
            return "Starbeetle!\nOnly good objects falling";
        }
    }

}