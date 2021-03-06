package com.tdt4240.catchgame.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.tdt4240.catchgame.Controllers.CoreGame;


public abstract class FallingObject {

    private Bitmap objectImage;
    private int objectPositionX, objectPositionY;
    private int objectWidth, objectHeight;
    private float objectSpeed;
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
    }

    public void setObjectPositionX(int newPositionX) {
        this.objectPositionX = newPositionX;
    }

    public int getObjectWidth() {
        return objectWidth;
    }

    public String getSound() {
        return this.sound;
    }

    protected void setSound(String sound) {
        this.sound = sound;
    }

    public void setObjectSpeed(int objectSpeed) {
        this.objectSpeed = objectSpeed;
    }

    public int getScore() {
        return this.objectScore;
    }

    private void wasEaten() {
        System.out.println("Object eaten, get score " + this.getScore());
        this.isEaten = true;
    }

    private void touchedFloor() {
        this.touchedFloor = true;
        System.out.println("Object dropped, get score " + this.getScore());
    }

    public void detectCollision(CharacterSprite characterSprite) {
        int objectTopLeft = objectPositionX;
        int objectTopRight = objectPositionX + objectWidth;
        int objectBottom = objectPositionY + objectHeight;
        int characterTop = characterSprite.getCharacterPositionY();
        int characterBottom = characterSprite.getCharacterPositionY() + characterSprite.getCharacterHeight();
        int characterTopLeft = characterSprite.getCharacterPositionX();
        int characterTopRight = characterSprite.getCharacterPositionX() + characterSprite.getCharacterWidth();

        if (objectBottom >= characterTop) {
            if (objectBottom >= characterBottom) {
                this.touchedFloor();
            } else if ((objectTopLeft >= characterTopLeft && objectTopLeft <= characterTopRight)
                    || (objectTopRight >= characterTopLeft && objectTopRight <= characterTopRight)
                    || (objectTopLeft >= characterTopLeft && objectTopRight <= characterTopRight)
                    || (characterTopLeft >= objectTopLeft && characterTopRight <= objectTopRight)) {
                this.wasEaten();
            }
            applyObjectEffect(characterSprite);
        }
    }

    private void applyObjectEffect(CharacterSprite characterSprite) {
        if (this.isEaten) {
            this.applyObjectEatenEffect(characterSprite);
        } else if (this.touchedFloor) {
            applyObjectOnFloorEffect(characterSprite);
        }
    }

    public void applyGameChange(CoreGame coreGame, long updateTime){
    }

    public String gameChangeMessage(){ return"";}

    public void applyObjectEatenEffect(CharacterSprite characterSprite) {
    }

    public void applyObjectOnFloorEffect(CharacterSprite characterSprite) {
    }

    public boolean collisionDetected() {
        return (isEaten || touchedFloor);
    }

    public boolean isEaten(){
        return isEaten;
    }
}
