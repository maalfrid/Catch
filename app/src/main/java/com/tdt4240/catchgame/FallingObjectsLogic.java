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
    private CharacterSprite player;


    public FallingObjectsLogic(FallingObject object) {

        x = getRandomXPosition();
        y = screenHeight;
        this.player = player;

    }


    public void update() {
        y += yVelocity;

        // Loose one life
        if (touchedFloor() ) {
            // TODO: Need method in player state for loosing life
        }

        // Method in Scorelogic for gaining or loosing points
        if(isEaten()){
            //Check if good food, bad food or power up
            // TODO: Dispose object if eaten
            object.dispose();
        }
    }

    public int getRandomXPosition(){
        int random = (int)(Math.random() * screenWidth - object.getWidth());
        return random;
    }

    public boolean touchedFloor(){
        while(y > player.y){
            return false;
        }
        return !isEaten();
    }

    public boolean isEaten() {
        while (object.y - y.HEIGHT > player.y){
            return false;
        }
        if (object.y - y.HEIGHT == player.y) {
            touchedRight();
            touchedLeft();
            touchedCenter();
        }

        return eaten;

    }

    public void touchedLeft(){
        if((player.x < (object.x + object.getWidth()) < (player.x + player.getWidth()))){
            eaten = true;
        }
    }

    public void touchedRight(){
        if((player.x < object.x < (player.x+player.getWidth()))){
            eaten = true;
        }
    }

    public void touchedCenter(){
        if((player.x < object.x) && ((player.x + player.getWidth()) > object.x + object.getWidth())){
            eaten = true;
        }
    }

}




