package com.tdt4240.catchgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite {
    private Bitmap characterSpriteImage;
    private int characterPositionX, characterPositionY;
    private int characterWidth, characterHeight;

    private boolean isTouched = false;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;


    public CharacterSprite(Bitmap bmp){
        characterSpriteImage = bmp;

        characterWidth = characterSpriteImage.getWidth();
        characterHeight = characterSpriteImage.getHeight();

        characterPositionX = (screenWidth - characterWidth) / 2;
        characterPositionY = screenHeight - characterHeight - 125;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(characterSpriteImage, characterPositionX, characterPositionY, null);
    }

    public void update(){

    }

    public int getCharacterPositionX(){
        return characterPositionX;
    }

    public void setCharacterPositionX(int newPositionX){
        if (newPositionX > screenWidth - characterSpriteImage.getWidth()) {
            characterPositionX = screenWidth - characterSpriteImage.getWidth();
        }
        else if (newPositionX < 0){
            characterPositionX = characterSpriteImage.getWidth();
        }
        else {
            characterPositionX = newPositionX;
        }
    }

    public void setTouched(boolean isTouched){
        this.isTouched = isTouched;
    }

    public boolean isTouched(){
        return isTouched;
    }

    public void isBeingTouched(int eventX, int eventY) {
        if ((eventX >= (characterPositionX)) && (eventX <= (characterPositionX + characterWidth)) &&
                (eventY >= (characterPositionY)) &&
                (eventY <= characterPositionY + characterHeight)) {
            setTouched(true);
        }
        else{
            setTouched(false);
            }
    }

}
