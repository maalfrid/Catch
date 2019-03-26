package com.tdt4240.catchgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class FallingObject {

    private Bitmap objectImage;
    private int objectPositionX, objectPositionY;
    private int objectWidth, objectHeight;
    private int score;


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

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

}
