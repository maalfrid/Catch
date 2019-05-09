package com.tdt4240.catchgame.Model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.tdt4240.catchgame.Controllers.CoreGame;

import java.util.HashMap;
import java.util.Map;

public class CharacterSprite {
    private Sprites sprite;
    private Map<Integer, Bitmap> characterSpriteImages;
    private Bitmap characterSpriteImage;
    private int characterPositionX, characterPositionY;
    private int characterWidth, characterHeight;

    private boolean isTouched;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int score;
    private int lives;
    private long lastAnimationChange = System.currentTimeMillis();

    private boolean immune = false;
    private boolean vulnerable = false;


    public CharacterSprite(Sprites sprite) {
        this.sprite = sprite;
        loadImages(sprite);
        characterSpriteImage = characterSpriteImages.get(sprite.defaultImageID);
        characterWidth = characterSpriteImage.getWidth();
        characterHeight = characterSpriteImage.getHeight();
        characterPositionX = (screenWidth - characterWidth) / 2;
        characterPositionY = screenHeight - characterHeight - 125;
        this.lives = 3;
        this.score = 0;
        this.isTouched = false;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(characterSpriteImage, characterPositionX, characterPositionY, null);
    }

    public void update() {
        long updateTime = System.currentTimeMillis();
        if (updateTime >= lastAnimationChange + 500){
            if (characterSpriteImage == characterSpriteImages.get(sprite.defaultImageID)) {
                setCharacterSpriteImage(sprite.catchImageID);
            }
            else if (characterSpriteImage == characterSpriteImages.get(sprite.catchImageID)) {
                setCharacterSpriteImage(sprite.defaultImageID);
            }
            else if (characterSpriteImage == characterSpriteImages.get(sprite.deadImageID)){
                setCharacterSpriteImage(sprite.catchImageID);
            }
        }
    }

    public int getCharacterWidth() {
        return characterWidth;
    }

    public int getCharacterHeight() {
        return characterHeight;
    }

    public int getCharacterPositionX() {
        return characterPositionX;
    }

    public int getLives() {
        return this.lives;
    }

    public void removeLife() {
        if (!immune) {
            this.lives--;
        }
    }

    public void addLife() {
        this.lives++;
    }

    public int getCharacterPositionY() {
        return characterPositionY;
    }

    public void setCharacterPositionX(int newPositionX) {
        if (newPositionX > screenWidth - characterSpriteImage.getWidth()) {
            characterPositionX = screenWidth - characterSpriteImage.getWidth();
        } else if (newPositionX < 0) {
            characterPositionX = characterSpriteImage.getWidth();
        } else {
            characterPositionX = newPositionX;
        }
    }

    public void setTouched(boolean isTouched) {
        this.isTouched = isTouched;
    }

    public int getScore() {
        return this.score;
    }

    public void removeScore(int score) {
        if (!immune) {
            if (vulnerable) {
                this.score -= (score * 2);
            } else {
                this.score -= score;
            }
        }
    }

    public void addScore(int score) {
        this.score += score;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void isBeingTouched(int eventX, int eventY) {
        if ((eventX >= (characterPositionX)) && (eventX <= (characterPositionX + characterWidth)) &&
                (eventY >= (characterPositionY)) &&
                (eventY <= characterPositionY + characterHeight)) {
            setTouched(true);
        } else {
            setTouched(false);
        }
    }

    private void loadImages(Sprites sprite){
        characterSpriteImages = new HashMap<>();
        characterSpriteImages.put(sprite.defaultImageID, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.getContext().getResources(), sprite.defaultImageID), 0.18));
        characterSpriteImages.put(sprite.catchImageID, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.getContext().getResources(), sprite.catchImageID), 0.18));
        characterSpriteImages.put(sprite.deadImageID, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.getContext().getResources(), sprite.deadImageID), 0.18));
    }

    public Sprites getCharacterSprite(){
        return this.sprite;
    }

    public void setCharacterSpriteImage(int imageID){
        long updateTime = System.currentTimeMillis();
        this.characterSpriteImage = characterSpriteImages.get(imageID);
        lastAnimationChange = updateTime;
    }

    public void setImmune(boolean immune){
        this.immune = immune;
    }

    public void setVulnerable(boolean vulnerable){
        this.vulnerable = vulnerable;
    }

    public boolean isImmune() { return this.immune; }

    public boolean isVulnerable() { return this.vulnerable; }

    private Bitmap getResizedBitmapObject(Bitmap bmp, double scaleFactorWidth) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        double newWidth = screenWidth * scaleFactorWidth;
        float scale = ((float) newWidth) / width;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap resizedBitmap =
                Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false);
        bmp.recycle();
        return resizedBitmap;
    }


}