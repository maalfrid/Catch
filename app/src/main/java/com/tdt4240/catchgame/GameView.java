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
    private boolean gameOver;
    private boolean gamePause;
    private boolean gameWon;
    private boolean gameLost;
    private boolean opponentExit;
    public boolean isMultiplayer;

    //menu items
    public MenuItem txt_score_self;
    public MenuItem txt_score_opponent;
    public MenuItem btn_exit;
    public MenuItem btn_sound;

    //game over/exit items
    public MenuItem txt_gameQuit;
    public MenuItem txt_gameOver;
    public MenuItem txt_gameWin;
    public MenuItem txt_gameLost;
    public MenuItem txt_opponentExit;
    public MenuItem btn_yes;
    public MenuItem btn_no;

    public GameView(Context context, SinglePlayerActivity singlePlayerActivity) {
        super(context);
        this.singlePlayerActivity = singlePlayerActivity;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        this.context = context;
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
        this.gameOver = false;
        this.gamePause = false;
        this.gameWon = false;
        this.gameLost = false;
        this.opponentExit = false;
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
        //menu items
        this.txt_score_self = new MenuItem("Score:  | Lives: ", 16, 000000, this.context);
        if (this.isMultiplayer) {
            this.txt_score_opponent = new MenuItem("(Opponent) Score: | Lives: ", 16, 000000, this.context);
        }
        this.btn_exit = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_exit), 0.15));
        this.btn_sound = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_sound_on), 0.15));
        //game over/exit items
        this.txt_gameQuit = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(getResources(), R.drawable.txt_quit), 1.0));
        this.txt_gameQuit.setPos(screenWidth / 2 - txt_gameQuit.getWidth() / 2, screenHeight / 2 - txt_gameQuit.getHeight() / 2);
        this.btn_yes = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(getResources(), R.drawable.button_yes), 1.0));
        this.btn_yes.setPos(txt_gameQuit.getPosX(), txt_gameQuit.getPosY() + txt_gameQuit.getHeight());
        this.btn_no = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(getResources(), R.drawable.button_no), 1.0));
        this.btn_no.setPos(txt_gameQuit.getPosX(), txt_gameQuit.getPosY() + txt_gameQuit.getHeight() + btn_no.getHeight());
        this.txt_gameOver = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.txt_gameover), 1.0));
        this.txt_gameOver.setPos(screenWidth / 2 - txt_gameOver.getWidth() / 2, screenHeight / 2 - txt_gameOver.getHeight() / 2);
        if (isMultiplayer) {
            this.txt_gameWin = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.txt_youwon), 1.0));
            this.txt_gameWin.setPos(screenWidth / 2 - txt_gameWin.getWidth() / 2, screenHeight / 2 - txt_gameWin.getHeight() / 2);
            this.txt_gameLost = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.txt_youlost), 1.0));
            this.txt_gameLost.setPos(screenWidth / 2 - txt_gameLost.getWidth() / 2, screenHeight / 2 - txt_gameLost.getHeight() / 2);
            this.txt_opponentExit = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.txt_opponentquit), 1.0));
            this.txt_opponentExit.setPos(screenWidth / 2 - txt_opponentExit.getWidth() / 2, screenHeight / 2 - txt_opponentExit.getHeight() / 2);
            coreGame = new CoreGame(multiPlayerActivity.getGametype(), multiPlayerActivity.getDifficulty(), this.context, this);
        }
        if (!isMultiplayer) {
            coreGame = new CoreGame(singlePlayerActivity.getGametype(), singlePlayerActivity.getDifficulty(), this.context, this);
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
            canvas.drawBitmap(background, 0, 0, null);

            if (!isGamePause() & !isGameOver()) {
                coreGame.draw(canvas);
            }

            this.txt_score_self.draw(canvas, screenWidth / 2 - txt_score_self.getWidth() / 2, 0);
            if (this.isMultiplayer) {
                this.txt_score_opponent.draw(canvas, screenWidth / 2 - txt_score_self.getWidth() / 2, txt_score_self.getHeight());
            }
            btn_exit.draw(canvas, 0, 0);
            btn_sound.draw(canvas, screenWidth - btn_sound.getWidth(), 0);

            if (isGameOver()) {
                txt_gameOver.draw(canvas, txt_gameOver.getPosX(), txt_gameOver.getPosY());
            }

            if (isGamePause()) {
                txt_gameQuit.draw(canvas, txt_gameQuit.getPosX(), txt_gameQuit.getPosY());
                btn_yes.draw(canvas, btn_yes.getPosX(), btn_yes.getPosY());
                btn_no.draw(canvas, btn_no.getPosX(), btn_no.getPosY());
            }

            if (isGameWon()) {
                txt_gameWin.draw(canvas, txt_gameWin.getPosX(), txt_gameWin.getPosY());
            }

            if (isGameLost()) {
                txt_gameLost.draw(canvas, txt_gameLost.getPosX(), txt_gameLost.getPosY());
            }

            if (isOpponentExit()) {
                txt_opponentExit.draw(canvas, txt_opponentExit.getPosX(), txt_opponentExit.getPosY());
            }
        }
    }


    public void update() {
        if (!isGamePause() && !isGameOver()) {
            coreGame.update();
            if (isMultiplayer) {
                updateScoreOpponent();
            }
        }
    }

    public void updateScoreOpponent() {
        int score = getMultiPlayerActivity().getOpponentScore();
        int lives = getMultiPlayerActivity().getOpponentLife();
        this.txt_score_opponent.updateScoreLife(score, lives, this.context);
    }

    public void updateScoreSelf(int score, int lives) {
        this.txt_score_self.updateScoreLife(score, lives, this.context);

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        coreGame.onTouch(motionEvent);
        return true;
    }

    /*
     * --------- HANDLING GAME EXIT / GAME OVER---------
     * */


    // When the player says yes to quit the game
    public void gameExit() {
        setRunning(false);
        if (!isMultiplayer) {
            singlePlayerActivity.finish();
        }
        if (isMultiplayer) {
            multiPlayerActivity.finish();
        }
    }

    // When the player has lost 3 lives
    public void gameOver() {
        setRunning(false);
        setGameOver(true);
    }

    public void gameWon() {
        setRunning(false);
        setGameWon(true);
    }

    public void gameLost() {
        setRunning(false);
        setGameLost(true);
    }

    public void opponentExit() {
        setRunning(false);
        setOpponentExit(true);
    }


    public void popup(final String msg) {
        if (!isMultiplayer) {
            getSinglePlayerActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(getSinglePlayerActivity(), msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
        if(isMultiplayer){
            getMultiPlayerActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(getSinglePlayerActivity(),msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
    }

    /*
     * --------- GETTERS AND SETTERS ---------
     * */

    public void setRunning(Boolean b) {
        thread.setRunning(b);
    }

    public void setGameOver(Boolean b) {
        this.gameOver = b;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public void setGamePause(Boolean b) {
        this.gamePause = b;
    }

    public boolean isGamePause() {
        return this.gamePause;
    }

    public void setGameWon(Boolean b) {
        this.gameWon = b;
    }

    public boolean isGameWon() {
        return this.gameWon;
    }

    public void setGameLost(Boolean b) {
        this.gameLost = b;
    }

    public boolean isGameLost() {
        return this.gameLost;
    }

    public void setOpponentExit(Boolean b) {
        this.opponentExit = b;
    }

    public boolean isOpponentExit() {
        return this.opponentExit;
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