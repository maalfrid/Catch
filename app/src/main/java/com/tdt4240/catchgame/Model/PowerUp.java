package com.tdt4240.catchgame.Model;

import android.graphics.Bitmap;

import com.tdt4240.catchgame.Controllers.CoreGame;

public class PowerUp extends FallingObject {

    public PowerUp(Bitmap bitmap, ObjectType object) {
        super(bitmap, object);
    }
    @Override
    public void applyObjectEatenEffect(CharacterSprite characterSprite) {
        switch (objectType) {
            case LIGHTNINGBEETLE:
                break;
            case LADYBUG:
                characterSprite.addLife();
                break;
            case STARBEETLE:
                break;
            case GREENBEETLE:
                characterSprite.setImmune(true);
                break;
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
        switch (objectType) {
            case LIGHTNINGBEETLE:
                FallingObjectFactory.getInstance().setObjectScale(0, 0.25);
                FallingObjectFactory.getInstance().setObjectScale(1, 0.05);
                FallingObjectFactory.getInstance().setLargeGood(true);
                coreGame.setPowerupDuration(objectType, updateTime + 10000);
                coreGame.setMultiPowerupSent(1);
                break;
            case STARBEETLE:
                FallingObjectFactory.getInstance().setOnlyGood(true);
                coreGame.setPowerupDuration(objectType, updateTime + 10000);
                coreGame.setMultiPowerupSent(2);
                break;
            case LADYBUG:
                coreGame.setMultiPowerupSent(3);
                break;
            case GREENBEETLE:
                coreGame.setPowerupDuration(objectType, updateTime + 10000);
                coreGame.setMultiPowerupSent(4);
                break;
        }
    }

    @Override
    public String gameChangeMessage(){
        switch (objectType) {
            case LIGHTNINGBEETLE:
                return "Beetle!\nLarge good objects, small bad objects";
            case LADYBUG:
                return "Ladybug!\nYou get one extra life";
            case STARBEETLE:
                return "Starbeetle!\nOnly good objects falling";
            case GREENBEETLE:
                return "Green beetle! You are immune to bad objects!";
            default:
                return "";
        }
    }

}