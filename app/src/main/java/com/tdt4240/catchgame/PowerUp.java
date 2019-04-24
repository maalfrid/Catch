package com.tdt4240.catchgame;

import android.graphics.Bitmap;

public class PowerUp extends FallingObject {

    public PowerUp(Bitmap bitmap, ObjectType object) {
        super(bitmap, object);
    }

    //TODO: Implement powerup-logic for single and multiplayer in this class, based on the restructure of the code and endless-mode.

    @Override
    public void applyObjectEatenEffect(CharacterSprite characterSprite) {
        if (objectType == ObjectType.BEETLE) {
            //DO something
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
            coreGame.getFallingObjectFactory().setObjectScale(1,0.1);
            coreGame.setBeetleDuration(updateTime + 10000);
            coreGame.broadCastPowerUp(1);
        } else if (objectType == ObjectType.LADYBUG) {
        } else if (objectType == ObjectType.STARBEETLE) {
            coreGame.getFallingObjectFactory().setOnlyGood(true);
            coreGame.setStarBeetleDuration(updateTime + 10000);
            coreGame.broadCastPowerUp(2);
        }
    }

}

  /*POWER-UP RULES
            Gets points for catching, in addition to logics for which is caught:

            Things we can do:
                Change scale of objects, i.e. make bad objects larger for the opponent
                Slow down or increase the speed or frequency of object spawns.
                Get life.

            With some additional logic:
                Scale size of sprites, larger, smaller.
                Make opponent unable to move for some time (not sure if good idea since it might just seem like lag.
                */