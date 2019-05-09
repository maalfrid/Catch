package com.tdt4240.catchgame.View;

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

import com.tdt4240.catchgame.Controllers.CoreGame;
import com.tdt4240.catchgame.MainThread;
import com.tdt4240.catchgame.Model.Backgrounds;
import com.tdt4240.catchgame.Model.FallingObjectFactory;
import com.tdt4240.catchgame.Model.MenuItem;
import com.tdt4240.catchgame.Model.ObjectType;
import com.tdt4240.catchgame.Controllers.MultiPlayerActivity;
import com.tdt4240.catchgame.R;
import com.tdt4240.catchgame.Controllers.SinglePlayerActivity;

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
    public MenuItem txt_lives_self;
    public MenuItem txt_lives_opponent;
    public MenuItem txt_you;
    public MenuItem txt_opponent;
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
        this.background = scaleBackground(Backgrounds.valueOf(singlePlayerActivity.getBackground()));
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
        this.background = scaleBackground(Backgrounds.valueOf(multiPlayerActivity.getBackground()));
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
        generateScoreLine();
        setIngameMenuButtons();
        if (isMultiplayer) {
            coreGame = new CoreGame(multiPlayerActivity.getDifficulty(), multiPlayerActivity.getAvatar(), multiPlayerActivity.getBackgroundSoundOn(), multiPlayerActivity.getSoundEffectsOn(), this.context, this);
        } else {
            this.background = scaleBackground(Backgrounds.valueOf(singlePlayerActivity.getBackground()));
            coreGame = new CoreGame(singlePlayerActivity.getDifficulty(), singlePlayerActivity.getAvatar(), singlePlayerActivity.getBackgroundsoundOn(), singlePlayerActivity.getSoundEffectsOn(), this.context, this);
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
            drawMenuBar(canvas);
            drawState(canvas);
            drawActivePowerups(canvas);
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

    public boolean onTouchEvent(MotionEvent motionEvent) {
        coreGame.onTouch(motionEvent);
        return true;
    }

    /*
    * GAME MENU / SCORING VIEW
    * */


    private void generateScoreLine(){
        this.txt_you = new MenuItem("You", 45.0f, "#f1c131", "#0f4414", this.context);
        this.txt_score_self = new MenuItem("0", 45.0f, "#f1c131", "#0f4414", this.context);
        this.txt_lives_self = new MenuItem("0", 80.0f, "#f1c131", "#0f4414", this.context);
        if (this.isMultiplayer) {
            this.txt_opponent = new MenuItem("Opponent", 45.0f, "#f16131", "#0f4414", this.context);
            this.txt_score_opponent = new MenuItem("0: ", 45.0f, "#f16131", "#0f4414", this.context);
            this.txt_lives_opponent = new MenuItem("0", 80.0f, "#f16131", "#0f4414", this.context);
        }
    }

    private void setIngameMenuButtons(){
        this.btn_exit = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_exit), 0.15));
        this.btn_sound = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_sound_on), 0.15));
        //game over/exit items
        this.txt_gameQuit = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(getResources(), R.drawable.txt_quit), 1.0));
        this.txt_gameQuit.setPos(screenWidth/2 - txt_gameQuit.getWidth()/2, screenHeight / 2 - txt_gameQuit.getHeight() / 2);
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
        }
    }

    public void drawMenuBar(Canvas canvas){
        btn_exit.draw(canvas, 0, 0);
        btn_sound.draw(canvas, screenWidth - btn_sound.getWidth(), 0);

        this.txt_you.draw(canvas,(screenWidth/2.0f - btn_exit.getWidth())/2.0f + 1.0f*txt_you.getWidth(), 0);
        this.txt_score_self.draw(canvas, txt_you.getPosX() + txt_score_self.getWidth()/2.0f, txt_you.getHeight());
        this.txt_lives_self.draw(canvas, 0.5f*txt_lives_self.getWidth(), btn_exit.getHeight());
        if (this.isMultiplayer) {
            txt_opponent.draw(canvas,(1.5f*screenWidth - btn_sound.getWidth())/2.0f - 0.5f*this.txt_opponent.getWidth(), 0);
            this.txt_score_opponent.draw(canvas, (1.5f*screenWidth - btn_sound.getWidth())/2.0f - 0.5f*this.txt_score_opponent.getWidth(), txt_opponent.getHeight());
            this.txt_lives_opponent.draw(canvas, screenWidth - 1.5f*txt_lives_opponent.getWidth(), btn_sound.getHeight());
        }
    }

    // Draw power-ups
    public void drawActivePowerups(Canvas canvas){
        float heightSelf = this.txt_lives_self.getPosY();
        if(this.coreGame.getCharacterSprite().isImmune()){
            Bitmap bmp = FallingObjectFactory.getInstance().getObjectImage(ObjectType.GREENBEETLE, 0.1);
            canvas.drawBitmap(bmp, btn_exit.getWidth(), heightSelf, null);
        }
        if(FallingObjectFactory.getInstance().isLargeGood()){
            Bitmap bmp = FallingObjectFactory.getInstance().getObjectImage(ObjectType.LIGHTNINGBEETLE, 0.1);
            canvas.drawBitmap(bmp, btn_exit.getWidth() + bmp.getWidth(), heightSelf, null);
        }
        if(FallingObjectFactory.getInstance().isOnlyGood()){
            Bitmap bmp = FallingObjectFactory.getInstance().getObjectImage(ObjectType.STARBEETLE, 0.1);
            canvas.drawBitmap(bmp, btn_exit.getWidth() + 2*bmp.getWidth(), heightSelf, null);
        }
        if (isMultiplayer) {
            float heightOpponent = this.txt_lives_opponent.getPosY();
            if (this.coreGame.getCharacterSprite().isVulnerable()) {
                Bitmap bmp = FallingObjectFactory.getInstance().getObjectImage(ObjectType.GREENBEETLE, 0.1);
                canvas.drawBitmap(bmp, screenWidth - this.txt_lives_opponent.getWidth() - 2 * bmp.getWidth(), heightOpponent, null);
            }
            if (FallingObjectFactory.getInstance().isLargeBad()) {
                Bitmap bmp = FallingObjectFactory.getInstance().getObjectImage(ObjectType.LIGHTNINGBEETLE, 0.1);
                canvas.drawBitmap(bmp, screenWidth - this.txt_lives_opponent.getWidth() - 3 * bmp.getWidth(), heightOpponent, null);
            }
            if (FallingObjectFactory.getInstance().isOnlyBad()) {
                Bitmap bmp = FallingObjectFactory.getInstance().getObjectImage(ObjectType.STARBEETLE, 0.1);
                canvas.drawBitmap(bmp, screenWidth - this.txt_lives_opponent.getWidth() - 4 * bmp.getWidth(), heightOpponent, null);
            }
        }
    }

    // Draw scores, lives
    public void updateScoreOpponent() {
        int score = getMultiPlayerActivity().getOpponentScore();
        int lives = getMultiPlayerActivity().getOpponentLife();
        this.txt_lives_opponent.updateScoreLife(""+ lives, this.context);
        this.txt_score_opponent.updateScoreLife("" + score, this.context);
    }

    public void updateScoreSelf(int score, int lives) {
        this.txt_score_self.updateScoreLife("" + score, this.context);
        this.txt_lives_self.updateScoreLife("" + lives, this.context);
    }

    /*
     * --------- HANDLING GAME EXIT / GAME OVER---------
     * */

    public void drawState(Canvas canvas){
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


    // When the player says yes to quit the game


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
                    Toast toast = Toast.makeText(getMultiPlayerActivity(), msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
    }

    /*
     * --------- GETTERS AND SETTERS ---------
     * */

    public void setRunning(boolean b) {
        thread.setRunning(b);
    }

    public void setGameOver(boolean b) {
        this.gameOver = b;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public void setGamePause(boolean b) {
        this.gamePause = b;
    }

    public boolean isGamePause() {
        return this.gamePause;
    }

    public void setGameWon(boolean b) {
        this.gameWon = b;
    }

    public boolean isGameWon() {
        return this.gameWon;
    }

    public void setGameLost(boolean b) {
        this.gameLost = b;
    }

    public boolean isGameLost() {
        return this.gameLost;
    }

    public void setOpponentExit(boolean b) {
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
    public Bitmap scaleBackground(Backgrounds background){
        return getResizedBitmapBG(BitmapFactory.decodeResource(getResources(), background.defaultImageID), 1, 1);

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