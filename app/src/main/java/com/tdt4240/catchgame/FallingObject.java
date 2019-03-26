package com.tdt4240.catchgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class FallingObject {

    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap characterSpriteImage;
    private int characterPositionX, characterPositionY;
    private int characterWidth, characterHeight;
    private int speed = 5;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;


    public FallingObject(Bitmap bmp){
        characterSpriteImage = bmp;

        characterWidth = characterSpriteImage.getWidth();
        characterHeight = characterSpriteImage.getHeight();


        characterPositionX = (screenWidth - characterWidth) / 2;
        characterPositionY = 0;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(characterSpriteImage, characterPositionX, characterPositionY, null);
    }

    public void update(){
        setCharacterPositionY(getCharacterPositionY() + speed);
    }

    public int getCharacterPositionY(){
        return characterPositionY;
    }

    public void setCharacterPositionY(int newPositionY){
        if (newPositionY > screenHeight) {
            //End element
            characterPositionY = screenHeight;
        }
        else if (newPositionY < 0){
            characterPositionY = characterSpriteImage.getHeight();
        }
        else {
            characterPositionY = newPositionY;
        }
    }

}
