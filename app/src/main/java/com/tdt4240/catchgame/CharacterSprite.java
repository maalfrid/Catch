package com.tdt4240.catchgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite {
    private Bitmap characterSpriteImage;
    private int characterPositionX, characterPositionY;
    private int characterWidth, characterHeight;

    private boolean isTouched;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int score;
    private int lives;


    public CharacterSprite(Bitmap bmp) {
        setImage(bmp);
        setCharacterWidth(this.characterSpriteImage.getWidth());
        setCharacterHeight(this.characterSpriteImage.getHeight());
        this.characterPositionX = (screenWidth - characterWidth) / 2;
        this.characterPositionY = screenHeight - characterHeight - 125;
        this.lives = 3;
        this.isTouched = false;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.characterSpriteImage, this.characterPositionX, this.characterPositionY, null);
    }

    public void update() {}

    /*
    * TOUCH METHODS
    * */

    public boolean isTouched() {
        return isTouched;
    }

    public void isBeingTouched(int eventX, int eventY) {
        if ((eventX >= (this.characterPositionX)) && (eventX <= (this.characterPositionX + this.characterWidth)) &&
                (eventY >= (this.characterPositionY)) &&
                (eventY <= this.characterPositionY + this.characterHeight)) {
            setTouched(true);
        } else {
            setTouched(false);
        }
    }

    /*
    * GETTERS AND SETTERS
    * */
    
    public void setImage(Bitmap bmp){this.characterSpriteImage = bmp; }

    public void setCharacterWidth(int width) {
        this.characterWidth = width;
    }
    
    public int getCharacterWidth() {
        return this.characterWidth;
    }


    public void setCharacterHeight(int height) { this.characterHeight = height;}
    
    public int getCharacterHeight() {
        return this.characterHeight;
    }

    public void setCharacterPositionX(int newPositionX) {
        if (newPositionX > screenWidth - this.characterSpriteImage.getWidth()) {
            this.characterPositionX = screenWidth - this.characterSpriteImage.getWidth();
        } else if (newPositionX < 0) {
            this.characterPositionX = this.characterSpriteImage.getWidth();
        } else {
            this.characterPositionX = newPositionX;
        }
    }

    public int getCharacterPositionX() { return this.characterPositionX;}

    public int getCharacterPositionY() {
        return this.characterPositionY;
    }


    public void setTouched(boolean isTouched) {
        this.isTouched = isTouched;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() { return this.lives; }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

}



