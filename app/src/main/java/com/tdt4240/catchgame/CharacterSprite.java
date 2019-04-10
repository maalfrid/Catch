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
        this.characterSpriteImage = bmp;
        this.characterWidth = characterSpriteImage.getWidth();
        this.characterHeight = characterSpriteImage.getHeight();
        this.characterPositionX = (screenWidth - characterWidth) / 2;
        this.characterPositionY = screenHeight - characterHeight - 125;
        this.lives = 3;
        this.isTouched = false;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.characterSpriteImage, this.characterPositionX, this.characterPositionY, null);
    }

    public void update() {}

    public int getCharacterWidth() {
        return this.characterWidth;
    }

    public int getCharacterHeight() {
        return this.characterHeight;
    }

    public int getCharacterPositionX() { return this.characterPositionX;}

    public int getLives() { return this.lives; }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getCharacterPositionY() {
        return this.characterPositionY;
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

    public void setTouched(boolean isTouched) {
        this.isTouched = isTouched;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

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

    public void resizeSprite(){

    }

}



