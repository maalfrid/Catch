package com.tdt4240.catchgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;

import static com.tdt4240.catchgame.CoreGame.context;

public class MenuItem {

    private Bitmap bmp;
    private float posX, posY;
    private int width, height;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    //Constructor for images
    public MenuItem(Bitmap bmp) {
        setImage(bmp);
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    //Constructor for text
    public MenuItem(String text, float textSize, int textColor, Context context) {
        setText(text, textSize, textColor, context);
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    public void draw(Canvas canvas, float posX, float posY) {
        setPosX(posX);
        setPosY(posY);
        canvas.drawBitmap(this.bmp, getPosX(), getPosY(), null);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public float getPosX() {
        return this.posX;
    }

    public float getPosY() {
        return this.posY;
    }

    public float getCenterY() {
        return screenHeight / 2 - this.height / 2;
    }

    public float getCenterX() {
        return screenWidth / 2 - this.width / 2;
    }

    public void setPos(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setImage(Bitmap bmp) {
        this.bmp = bmp;
    }

    public void setText(String text, float textSize, int textColor, Context context) {
        Typeface regular = ResourcesCompat.getFont(context, R.font.frecklefaceregular);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(regular);
        paint.setTextSize(40.0f);
        paint.setColor(Color.WHITE);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        this.bmp = image;
    }

    public boolean isTouched(float eventX, float eventY) {
        if ((eventX >= (getPosX())) && (eventX <= (getPosX() + getWidth())) &&
                (eventY >= (getPosY())) &&
                (eventY <= getPosY() + getHeight())) {
            return true;
        }
        return false;
    }

    public void updateScoreLife(int score, int lives, Context context) {
        String s = "Score: " + score + " | Lives: " + lives;
        setText(s, 20.0f, Color.WHITE, context);
    }

}
