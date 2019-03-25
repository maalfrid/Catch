package com.tdt4240.catchgame;

import android.content.res.Resources;

public class FallingObjectsLogic {

    private int x, y;
    private int yVelocity = -10;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean hitFloor = false;
    private boolean eaten = false;
    private CharacterSprite player = SinglePlayerActivity.getPlayer();


    public FallingObjectsLogic(FallingObject object) {

        x = getRandomXPosition();
        y = screenHeight - object.getHeight();

    }



    public void update() {
        y += yVelocity;

        // Loose one life
        if (touchedFloor() ) {

        }

        // Method in Scorelogic for gaining or loosing points
        if(eaten){
            //Check if good food, bad food or power up
        }
    }

    public int getRandomXPosition(){
        int random = (int)(Math.random() * screenWidth - object.getWidth());
        return random;
    }

    public boolean touchedFloor(){
        while(y > 0){
            return false;

        }
        return true;
    }

    public boolean isEaten() {
        while (y > player.getHeight()){
            return false;
        }
        return eaten;

    }

    public void touchedLeft(){
        if((player.x < (x+object.getWidth()) < player.x + player.getWidth())){
            eaten = true;
        }
    }

    public void touchedRight(){
        if((player.x < x) && ((player.x + player.getWidth()) > x + object.getWidth())){
            eaten = true;
        }
    }

    public void touchedCenter(){
        if((player.x < x) && ((player.x + player.getWidth()) > x + object.getWidth())){
            eaten = true;
        }
    }

}




