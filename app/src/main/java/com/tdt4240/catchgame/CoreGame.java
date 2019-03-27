package com.tdt4240.catchgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;

import java.util.ArrayList;

public class CoreGame{

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private CharacterSprite characterSprite;
    private ArrayList<FallingObject> objectsOnScreen;
    private int gameTime;
    private int baseFrequency;
    private int baseSpeed;
    private Context context;

    public CoreGame(String difficulty, Context context){
        this.context = context;
        this.objectsOnScreen = new ArrayList<>();
        this.gameTime = 0;
        this.setDifficulty(difficulty);

    }

    public void draw(Canvas canvas){
        for(int i=0; i < objectsOnScreen.size(); i++){
            objectsOnScreen.get(i).draw(canvas);
        }
    }

    public void update(){
        gameTime++;
        for(int i=0; i < objectsOnScreen.size(); i++){
            objectsOnScreen.get(i).update();
        }
        if (gameTime == 10 || gameTime == 100){
            spawnObject(createObject());
        }
    }

    public FallingObject createObject(){
        // TODO: Method that calls the factory to create object of given type and returns it.
        return new FallingObject(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.obj_good_banana),0.2));
    }

    public void spawnObject(FallingObject fallingObject){
        fallingObject.setObjectPositionX(getRandomXPosition(fallingObject));
        fallingObject.setObjectSpeed(getRandomSpeed());
        objectsOnScreen.add(fallingObject);
    }

    public int getRandomXPosition(FallingObject fallingObject){
        return (int)(Math.random() * screenWidth - fallingObject.getObjectWidth());
    }

    public int getRandomSpeed(){
        return (int)(Math.random() * baseSpeed);
    }

    public int getRandomFrequency(){
        return (int)(Math.random() * baseFrequency);
    }

    //Temporary method to adjust game difficulty, should be in its own class?
    public void setDifficulty(String difficulty){
        if (difficulty.equals("easy")){
            this.baseFrequency = 1;
            this.baseSpeed = 5;
        }
        if (difficulty.equals("medium")){
            this.baseFrequency = 2;
            this.baseSpeed = 10;
        }
        if (difficulty.equals("hard")){
            this.baseFrequency = 3;
            this.baseSpeed = 15;
        }
    }

    // Wanted to make this a static method in a separate class
    public Bitmap getResizedBitmapObject(Bitmap bmp, double scaleFactorWidth) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        double newWidth = screenWidth * scaleFactorWidth;
        float scale = ((float) newWidth) / width;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scale, scale);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap =
                Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false);
        bmp.recycle();
        return resizedBitmap;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                characterSprite.isBeingTouched((int) motionEvent.getX(), (int) motionEvent.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (characterSprite.isTouched()) {
                    characterSprite.setCharacterPositionX((int) motionEvent.getX());
                }
                break;

            case MotionEvent.ACTION_UP:
                if (characterSprite.isTouched()) {
                    characterSprite.setTouched(false);
                }
                break;
        }
        return true;
    }


    //Check if object has changed status to eaten or hit ground.
    //Remove object from list.
    //IF object has status as eaten, increase/decrease score, apply powerup.
    //IF object hits ground, remove life.

}
