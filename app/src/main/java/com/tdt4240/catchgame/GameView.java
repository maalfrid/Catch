package com.tdt4240.catchgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.HashMap;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    protected CharacterSprite characterSprite;
    private FallingObject fallingObject;
    private FallingObjectsLogic fallingObjectsLogic;
    private Bitmap background;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;


    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
       // this.setBackgroundResource(R.drawable.bg_menu);
        background = getResizedBitmapBG(BitmapFactory.decodeResource(getResources(), R.drawable.bg_play), 1, 1);
        characterSprite = new CharacterSprite(getResizedBitmapObject(BitmapFactory.decodeResource(getResources(),R.drawable.sprites_monkey3),0.2));
        fallingObject = new FallingObject(getResizedBitmapObject(BitmapFactory.decodeResource(getResources(),R.drawable.obj_good_banana),0.2));
        fallingObjectsLogic = new FallingObjectsLogic(fallingObject,characterSprite);
        System.out.println("surface created");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
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

    public void update(){

        if (characterSprite != null && fallingObject !=null && fallingObjectsLogic != null) {

            characterSprite.update();

            fallingObject.update();
            fallingObjectsLogic.update();
            System.out.println("update");

        }
        else{
            System.out.println("character " + characterSprite == null);
            System.out.println("logic " + fallingObjectsLogic == null);
            System.out.println("fallingobject " + fallingObject == null);


        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawBitmap(background, 0,0, null);
            characterSprite.draw(canvas);
            fallingObject.draw(canvas);
        }
    }

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

    public Bitmap getResizedBitmapBG(Bitmap bmp, double scaleFactorWidth, double scaleFactorHeight) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        double newWidth = screenWidth * scaleFactorWidth;
        double newHeight = screenHeight * scaleFactorHeight;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap =
                Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false);
        bmp.recycle();
        return resizedBitmap;
    }




}
