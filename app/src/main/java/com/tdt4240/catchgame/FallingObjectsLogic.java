package com.tdt4240.catchgame;

import android.content.res.Resources;

public class FallingObjectsLogic {

    private int x, y;
    private int yVelocity = -10;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean hitFloor = false;
    private boolean eaten = false;
    //private CharacterSprite player = SinglePlayerActivity.getPlayer();
    private CharacterSprite character;
    private FallingObject object;
    private GameView gameView;


    public FallingObjectsLogic(FallingObject object, CharacterSprite character) {
        this.object = object;
        //character = gameView.characterSprite;
        this.character=character;
        object.setObjectPositionX(getRandomXPosition());
        x = object.getObjectPositionX();
        y = object.getObjectPositionY();

    }

    public void update() {
        object.setObjectPositionY(object.getObjectPositionY() + yVelocity);

        // Loose one life
        if (touchedFloor() ) {
            // TODO: Need method in player state for loosing life
        }

        // Method in Scorelogic for gaining or loosing points
        if(isEaten()){
            //Check if good food, bad food or power up
            // TODO: Dispose object if eaten
            //object.dispose();
        }
    }

    public int getRandomXPosition(){
        int random = (int)(Math.random() * screenWidth - object.getObjectWidth());
        return random;
    }

    public boolean touchedFloor(){
        while(object.getObjectPositionY() > character.getCharacterHeight()){
            return false;
        }
        return !isEaten();
    }

    public boolean isEaten() {
        while (object.getObjectPositionY() - object.getObjectHeight() > character.getCharacterWidth()){
            return false;
        }
        if (object.getObjectPositionY() - object.getObjectHeight() == character.getCharacterHeight()) {
            touchedRight();
            touchedLeft();
            touchedCenter();
        }
        return eaten;

    }

    public void touchedLeft(){
        if((character.getCharacterPositionX()) < (object.getObjectPositionX() + object.getObjectWidth())
                && (object.getObjectPositionX() + object.getObjectWidth()) < (character.getCharacterPositionX() + character.getCharacterWidth())){
            eaten = true;
        }
    }

    public void touchedRight(){
        if(((character.getCharacterPositionX() < object.getObjectPositionX()) &&
                object.getObjectPositionX() < (character.getCharacterPositionX() + character.getCharacterWidth()))){
            eaten = true;
        }
    }

    public void touchedCenter(){
        if((character.getCharacterPositionX() < object.getObjectPositionX()) && ((character.getCharacterPositionX() + character.getCharacterWidth()) > object.getObjectPositionX() + object.getObjectWidth())){
            eaten = true;
        }
    }

}




