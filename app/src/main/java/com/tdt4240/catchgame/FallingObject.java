package com.tdt4240.catchgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;



public abstract class FallingObject {

    private Bitmap objectImage;
    private int objectPositionX, objectPositionY;
    private int objectWidth, objectHeight;
    private int objectSpeed;
    protected int objectScore;
    protected ObjectType objectType;
    private boolean isEaten = false;
    private boolean touchedFloor = false;
    private String sound;

    public FallingObject(Bitmap bmp, ObjectType object) {
        this.objectType = object;
        this.objectScore = object.objectValue;
        this.objectImage = bmp;
        this.objectWidth = objectImage.getWidth();
        this.objectHeight = objectImage.getHeight();
        this.objectPositionY = 0;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(objectImage, objectPositionX, objectPositionY, null);
    }

    public void update() {
        objectPositionY += objectSpeed;

        // TODO: Move this logic to CoreGame to be able to call it on a sprite.
        if (touchedFloor) {
            // TODO: Need method in CharacterSprite for loosing life when object touches floor.
        }
        if (isEaten) {
            // TODO: Check if good food, bad food or power up
            // TODO: method to score points or apply powerup.
        }
    }

    public int getObjectPositionY() {
        return objectPositionY;
    }

    public int getObjectPositionX() {
        return objectPositionX;
    }

    public void setObjectPositionY(int newPositionY) {
        this.objectPositionY = newPositionY;
    }

    public void setObjectPositionX(int newPositionX) {
        this.objectPositionX = newPositionX;
    }

    public int getObjectWidth() {
        return objectWidth;
    }

    public int getObjectHeight() {
        return objectHeight;
    }

    public int getObjectSpeed() {
        return objectSpeed;
    }

    public String getSound(){
        return this.sound;
    }
    public void setSound(String sound) {
        this.sound = sound;

    }
    public void setObjectSpeed(int objectSpeed) {
        this.objectSpeed = objectSpeed;
    }

    public void setScore(int objectScore){
        this.objectScore = objectScore;
    }

    public int getScore(){
        return this.objectScore;
    }

    public void wasEaten() {
        System.out.println("Object eaten, get score " + this.getScore());
        this.isEaten = true;
    }

    public boolean isEaten(){
        return isEaten;
    }

    public void touchedFloor() {
        this.touchedFloor = true;
        System.out.println("Object dropped, get score " + this.getScore());
    }

    public void detectCollision(CharacterSprite characterSprite) {
        int objectTopLeft = objectPositionX;
        int objectTopRight = objectPositionX + objectWidth;
        int objectBottom = objectPositionY + objectHeight;
        int characterBottom = characterSprite.getCharacterPositionY() + characterSprite.getCharacterHeight();
        int characterTopLeft = characterSprite.getCharacterPositionX();
        int characterTopRight = characterSprite.getCharacterPositionX() + characterSprite.getCharacterWidth();

        if (objectBottom >= characterSprite.getCharacterPositionY()) {
            if (objectBottom >= characterBottom) {
                this.touchedFloor();
            } else if ((objectTopLeft >= characterTopLeft && objectTopLeft <= characterTopRight)
                    || (objectTopRight >= characterTopLeft && objectTopRight <= characterTopRight)
                    || (objectTopLeft >= characterTopLeft && objectTopRight <= characterTopRight)) {
                this.wasEaten();
            }
            applyObjectEffect(characterSprite);
        }
    }

    public void applyObjectEffect(CharacterSprite characterSprite){
        if (this.isEaten){
            this.applyObjectEatenEffect(characterSprite);
        }
        else if (this.touchedFloor){
            applyObjectOnFloorEffect(characterSprite);
        }
    }

    public void applyObjectEatenEffect(CharacterSprite characterSprite){
    }

    public void applyObjectOnFloorEffect(CharacterSprite characterSprite){
    }

    public boolean collisionDetected() {
        return (isEaten || touchedFloor);
    }

}
