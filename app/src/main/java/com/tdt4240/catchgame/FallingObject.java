package com.tdt4240.catchgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class FallingObject {

    private Bitmap objectImage;
    private int objectPositionX, objectPositionY;
    private int objectWidth, objectHeight;
    private int objectSpeed;
    private int score;
    private boolean isEaten = false;
    private boolean touchedFloor = false;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;


    public FallingObject(Bitmap bmp){
        objectImage = bmp;
        objectWidth = objectImage.getWidth();
        objectHeight = objectImage.getHeight();
        objectPositionY = 0;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(objectImage, objectPositionX, objectPositionY, null);
    }

    public void update(){
        objectPositionY += objectSpeed;

        // TODO: Need method in core game to remove object from list when it is eaten/touches floor.
        if (touchedFloor) {
            // TODO: Need method in player state for loosing life when object touches floor.
        }
        if(isEaten){
            // TODO: Check if good food, bad food or power up
            // TODO: method to score points or apply powerup.
        }
    }

    public int getObjectPositionY(){
        return objectPositionY;
    }
    public int getObjectPositionX(){
        return objectPositionX;
    }

    public void setObjectPositionY(int newPositionY) {
        this.objectPositionY = newPositionY;
    }

    public void setObjectPositionX(int newPositionX) {
        this.objectPositionX = newPositionX;
    }

    public int getObjectWidth(){
        return objectWidth;
    }

    public int getObjectHeight(){
        return objectHeight;
    }

    public int getObjectSpeed(){
        return objectSpeed;
    }

    public void setObjectSpeed(int objectSpeed) {
        this.objectSpeed = objectSpeed;
    }


    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

    public void wasEaten(){
        this.isEaten = true;
    }
    public void touchedFloor(){
        this.touchedFloor = true;
    }

    public void detectCollision(CharacterSprite characterSprite) {
        int objectTopLeft = this.getObjectPositionX();
        int objectTopRight = this.getObjectPositionX() + this.getObjectWidth();
        int objectBottom = this.getObjectPositionY() + this.getObjectHeight();
        int characterBottom = characterSprite.getCharacterPositionY() + characterSprite.getCharacterHeight();
        int characterTopLeft = characterSprite.getCharacterPositionX();
        int characterTopRight = characterSprite.getCharacterPositionX() + characterSprite.getCharacterWidth();

        if (objectBottom >= characterBottom) {
            this.touchedFloor();
        }
        if ((objectTopLeft >= characterTopLeft && objectTopLeft <= characterTopRight)
                || (objectTopRight >= characterTopLeft && objectTopRight <= characterTopRight)
                || (objectTopLeft >= characterTopLeft && objectTopRight >= characterTopRight)) {
            this.wasEaten();
        }
    }
}
