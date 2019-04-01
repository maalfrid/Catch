package com.tdt4240.catchgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class FallingObject {

    private Bitmap objectImage;
    private int objectPositionX, objectPositionY;
    private int objectWidth, objectHeight;
    private int objectSpeed;
    private int objectScore;
    private boolean isEaten = false;
    private boolean touchedFloor = false;
    private ScoreSinglePlayer scoreSinglePlayer;
    private CoreGame coreGame;
    private String type = "good";


    public FallingObject(Bitmap bmp, CoreGame coreGame) {
        objectImage = bmp;
        objectWidth = objectImage.getWidth();
        objectHeight = objectImage.getHeight();
        objectPositionY = 0;
        this.coreGame = coreGame;
        scoreSinglePlayer = coreGame.scoreSinglePlayer;
        this.objectScore = objectScore;
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

    public void setObjectSpeed(int objectSpeed) {
        this.objectSpeed = objectSpeed;
    }

    public void setScore(int objectScore){
        this.objectScore = objectScore;
    }

    public int getScore(){
        return this.objectScore;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void wasEaten() {
        System.out.println("was eaten");
        System.out.println("get score " + this.getScore());
        coreGame.scoreSinglePlayer.caughtObject(this);
        this.isEaten = true;
    }

    public void touchedFloor() {
        this.touchedFloor = true;
        if (this.type.equals("good")) {
            if (coreGame.characterSprite.getLives() == 1) {
                // TODO: Create game-over state, send to game-over state here
                System.out.println("Game over looooser");
            }
            coreGame.characterSprite.setLives(coreGame.characterSprite.getLives() - 1);
            System.out.println("Player has " + coreGame.characterSprite.getLives() + " lives left");
        }
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
        }
    }

    public boolean collisionDetected() {
        return (isEaten || touchedFloor);
    }

}
