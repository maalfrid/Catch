package com.tdt4240.catchgame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final MainThread thread;
    private CoreGame coreGame;
    private Bitmap background;
    private Context context;
    private SinglePlayerActivity singlePlayerActivity;
    private MultiPlayerActivity multiPlayerActivity;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean gameExit;
    private boolean gameOver;
    private boolean gamePause;
    public boolean isMultiplayer;

    //game over/exit items
    public MenuItem txt_gameQuit;
    public MenuItem txt_gameOver;
    public MenuItem btn_yes;
    public MenuItem btn_no;

    public GameView(Context context, SinglePlayerActivity singlePlayerActivity) {
        super(context);
        this.singlePlayerActivity = singlePlayerActivity;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        this.context = context;
        this.gameExit = false;
        this.gameOver = false;
        this.gamePause = false;
        this.isMultiplayer = false;
    }

    public GameView(Context context, MultiPlayerActivity multiPlayerActivity) {
        super(context);
        this.multiPlayerActivity = multiPlayerActivity;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        this.context = context;
        this.gameExit = false;
        this.gameOver = false;
        this.gamePause = false;
        this.isMultiplayer = true;
    }

    /*
     * --------- @OVERRIDE METHODS - SURFACEVIEW ---------
     * */

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        background = getResizedBitmapBG(BitmapFactory.decodeResource(getResources(), R.drawable.bg_play), 1, 1);
        //game over/exit items
        this.txt_gameQuit= new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(getResources(),R.drawable.txt_quit),1.0));
        this.txt_gameQuit.setPos(screenWidth/2 - txt_gameQuit.getWidth()/2, screenHeight/2 - txt_gameQuit.getHeight()/2);
        this.btn_yes= new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(getResources(),R.drawable.button_yes),1.0));
        this.btn_yes.setPos(txt_gameQuit.getPosX(), txt_gameQuit.getPosY() + txt_gameQuit.getHeight());
        this.btn_no= new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(getResources(),R.drawable.button_no),1.0));
        this.btn_no.setPos(txt_gameQuit.getPosX(), txt_gameQuit.getPosY() + txt_gameQuit.getHeight() + btn_no.getHeight());
        //TODO: Replace with game over text
        //this.txt_gameOver= new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.txt_gameover),1.0));
        this.txt_gameOver = new MenuItem("GAME OVER (Click to continue)", 16, 000000);
        this.txt_gameOver.setPos(screenWidth/2 - txt_gameOver.getWidth()/2, screenHeight/2 - txt_gameOver.getHeight()/2);
        if(isMultiplayer){
            coreGame = new CoreGame(multiPlayerActivity.getGametype(), multiPlayerActivity.getDifficulty(), context, this);
        }
        if(!isMultiplayer){
            coreGame = new CoreGame(singlePlayerActivity.getGametype(), singlePlayerActivity.getDifficulty(), context, this);
        }

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

    /*
     * --------- DRAW, UPDATE, ONTOUCH ---------
     * */

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawBitmap(background, 0,0, null);

            if(!getGamePause() & !getGameOver()){coreGame.draw(canvas);}

            if(getGameOver()){
                txt_gameOver.draw(canvas, txt_gameOver.getPosX(), txt_gameOver.getPosY());
            }

            if(getGamePause()){
                txt_gameQuit.draw(canvas, txt_gameQuit.getPosX(), txt_gameQuit.getPosY());
                btn_yes.draw(canvas, btn_yes.getPosX(), btn_yes.getPosY());
                btn_no.draw(canvas, btn_no.getPosX(), btn_no.getPosY());
            }
        }
    }


    public void update(){
        if(!getGamePause() & !getGameOver()){coreGame.update();}
    }


    public boolean onTouchEvent(MotionEvent motionEvent) {
        coreGame.onTouch(motionEvent);

        //If game exit or game over
        //TODO: Move isTouched logic into game view (as the core game is paused)

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN & (getGameOver() | getGamePause())){
            if(btn_yes.isTouched(motionEvent.getX(), motionEvent.getY())){
                gameExit();
            }
            if(btn_no.isTouched(motionEvent.getX(), motionEvent.getY())){
                gameResume();
            }
            if(txt_gameOver.isTouched(motionEvent.getX(), motionEvent.getY())){
                if(!isMultiplayer){singlePlayerActivity.finish();}
                if(isMultiplayer){multiPlayerActivity.finish();}
            }
        }

        return true;
    }

    /*
     * --------- HANDLING GAME EXIT / GAME OVER---------
     * */


    // When the player says yes to quit the game
    public void gameExit(){
        setRunning(false);
        if(!isMultiplayer){singlePlayerActivity.finish();}
        if(isMultiplayer){multiPlayerActivity.finish();}
    }

    // When the player has lost 3 lives
    public void gameOver(){
        setRunning(false);
        setGameOver(true);
    }

    // Check if the user really wants to quit
    public void gamePause(){
        setGamePause(true);
    }

    public void gameResume() {
        setGamePause(false);
    }

    public void popup(final String msg){
        if(!isMultiplayer){
            getSinglePlayerActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(getSinglePlayerActivity(),msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
        /*if(isMultiplayer){
            getMultiPlayerActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(getSinglePlayerActivity(),msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }*/


    }

    /*
    * --------- GETTERS AND SETTERS ---------
    * */

    public void setRunning(Boolean b){
        thread.setRunning(b);
    }

    public void setGameOver(Boolean b){
        this.gameOver = b;
    }

    public boolean getGameOver(){
        return this.gameOver;
    }

    public void setGameExit(Boolean b){
        this.gameExit = b;
    }

    public boolean getGameExit(){
        return this.gameExit;
    }

    public void setGamePause(Boolean b){
        this.gamePause = b;
    }

    public boolean getGamePause(){
        return this.gamePause;
    }

    public SinglePlayerActivity getSinglePlayerActivity() {
        return this.singlePlayerActivity;
    }

    public MultiPlayerActivity getMultiPlayerActivity() {
        return this.multiPlayerActivity;
    }


    /*
     * --------- HELP METHODS ---------
     * */

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

}