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
            coreGame.setLargeObjects(true);
            coreGame.getFallingObjectFactory().setObjectScale(0,0.25);
            coreGame.getFallingObjectFactory().setObjectScale(1,0.1);
            coreGame.setBeetleDuration(updateTime + 10000);
        } else if (objectType == ObjectType.LADYBUG) {
        } else if (objectType == ObjectType.STARBEETLE) {
            coreGame.setOnlyGood(true);
            coreGame.setStarBeetleDuration(updateTime + 10000);
        }
    }

    public String gameChangeMessage(ObjectType objectType){
        if(objectType == ObjectType.BEETLE) {
            return "You caught a beetle!";
        }
        else if(objectType == ObjectType.LADYBUG) {
            return "You caught a ladybug!";
        }
        else {
            return "You caught a starbeetle!";
        }
    }

}

  /*POWER-UP RULES
            Gets points for catching, in addition to logics for which is caught:

            Things we can do:
                Change scale of objects, i.e. make bad objects larger for the opponent
                Slow down or increase the speed or frequency of object spawns.
                Get points.
                Get life.

            With some additional logic:
                Scale size of sprites, larger, smaller.
                Make opponent unable to move for some time (not sure if good idea since it might just seem like lag.


                */

   /*      if(typeOfGame.equals("single")) {
                if(objectPoints == 1) {
                    incrementScore(10);
                    //this.coreGame.popup("Powerup! 10 extra points");
                }
                if(objectPoints == 2){
                    this.coreGame.setLevelDown();
                }
                if(objectPoints == 3){

                    // TODO: this does not work, fix that the lives increases
                    int currentLives = characterSprite.getLives();
                    int newLives = currentLives++;
                    characterSprite.setLives(newLives);
                    //this.coreGame.popup("Powerup! 1 extra life");
                }
            }*/

            /*if(typeOfGame.equals("multi")){
                if(objectPoints == 1) {
                    incrementScore(10);
                    // TODO: x2 points of the next caught items, don't know how yet
                }
                if(objectPoints == 2){
                    // TODO: increase size of own sprite and decrease opponent size
                        //Increase own size
                        //Broadcast this message

                }
                if(objectPoints == 3){
                    //TODO: increase speed of opponent's falling objects
                        //Broadcast this message
                }
            }*/