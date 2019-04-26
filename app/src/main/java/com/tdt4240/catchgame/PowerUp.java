package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class PowerUp extends FallingObject {

    public PowerUp(Bitmap bitmap, ObjectType object) {
        super(bitmap, object);
    }
    @Override
    public void applyObjectEatenEffect(CharacterSprite characterSprite) {
        if (objectType == ObjectType.LIGHTNINGBEETLE) {
        } else if (objectType == ObjectType.LADYBUG) {
            characterSprite.addLife();
        } else if (objectType == ObjectType.STARBEETLE) {
        } else if (objectType == ObjectType.GREENBEETLE){
            characterSprite.setImmune(true);
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
        if (objectType == ObjectType.LIGHTNINGBEETLE) {
            coreGame.getFallingObjectFactory().setObjectScale(0,0.25);
            coreGame.getFallingObjectFactory().setObjectScale(1,0.05);
            coreGame.getFallingObjectFactory().setLargeGood(true);
            coreGame.setBeetleDuration(updateTime + 10000);
            coreGame.setMultiPowerupSent(1);
        } else if (objectType == ObjectType.STARBEETLE) {
            coreGame.getFallingObjectFactory().setOnlyGood(true);
            coreGame.setStarBeetleDuration(updateTime + 10000);
            coreGame.setMultiPowerupSent(2);
        } else if (objectType == ObjectType.LADYBUG) {
            coreGame.setMultiPowerupSent(3);
        } else if (objectType == ObjectType.GREENBEETLE){
            coreGame.setGreenBeetleDuration(updateTime + 10000);
            coreGame.setMultiPowerupSent(4);
        }
    }

    @Override
    public String gameChangeMessage(){
        if(objectType == ObjectType.LIGHTNINGBEETLE) {
            return "Beetle!\nLarge good objects, small bad objects";
        }
        else if(objectType == ObjectType.LADYBUG) {
            return "Ladybug!\nYou get one extra life";
        }
        else if (objectType == ObjectType.STARBEETLE){
            return "Starbeetle!\nOnly good objects falling";
        } else if (objectType == ObjectType.GREENBEETLE){
            return "Green beetle! You are immune to bad objects!";
        }
        else {
            return "";
        }
    }

}