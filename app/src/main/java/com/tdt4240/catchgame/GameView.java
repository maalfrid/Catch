package com.tdt4240.catchgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private CoreGame coreGame;
    private Bitmap background;
    private Context context;
    private SinglePlayerActivity singlePlayerActivity;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;


    public GameView(Context context, SinglePlayerActivity singlePlayerActivity) {
        super(context);
        this.singlePlayerActivity = singlePlayerActivity;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        this.context = context;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        background = getResizedBitmapBG(BitmapFactory.decodeResource(getResources(), R.drawable.bg_play), 1, 1);
        coreGame = new CoreGame(singlePlayerActivity.getDifficulty(), context, this);
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
        coreGame.onTouch(motionEvent);
        return true;
    }

    public void update(){
        coreGame.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawBitmap(background, 0,0, null);
            coreGame.draw(canvas);
        }
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