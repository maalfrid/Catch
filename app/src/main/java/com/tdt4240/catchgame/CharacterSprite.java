package com.tdt4240.catchgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite {
    private Bitmap image;
    private int x, y;
    private int xVelocity = 10;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;


    public CharacterSprite(Bitmap bmp){
        image = bmp;
        x = (screenWidth - image.getWidth())/2;
        y = screenHeight - image.getHeight();


    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

    public void update(){
        x += xVelocity;
        if (((x > screenWidth - image.getWidth()) || (x < 0))) {
            xVelocity = xVelocity * -1;
        }
    }



}

