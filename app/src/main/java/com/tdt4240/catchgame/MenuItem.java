package com.tdt4240.catchgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MenuItem {

    private Bitmap image;
    private int posX, posY;
    private int width, height;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public MenuItem(Bitmap bmp){
        this.image = bmp;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public void draw(Canvas canvas, float posX, float posY) {
        canvas.drawBitmap(image, posX, posY, null);
    }

    public void update() {}

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public boolean isTouched(float eventX, float eventY){
        if ((eventX >= (getPosX())) && (eventX <= (getPosX() + getWidth())) &&
                (eventY >= (getPosY())) &&
                (eventY <= getPosY() + getHeight())) {
            return true;
        }
        return false;
    }

}
