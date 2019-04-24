package com.tdt4240.catchgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;

import java.util.ArrayList;

public class CoreGame {

    protected static Context context;
    private GameView gameview;
    private boolean soundOn;
    private SoundEffects soundEffects;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private String difficulty;
    private String gametype;

    //TODO: change this
    private String easy = "easy";
    private String medium = "medium";
    private String hard = "hard";

    private long timeLastSpawn = 0;
    private int objectsSpawned = 0;

    private int baseFrequency;
    private int baseSpeed;
    private int fractionGood;


    private boolean onlyGood = false;
    private boolean onlyBad = false;
    private long starBeetleDuration;
    private long beetleDuration;

    private CharacterSprite characterSprite;
    private FallingObjectFactory fallingObjectFactory;
    private ArrayList<FallingObject> objectsOnScreen;


    //for multiplayer
    public static int pScore;
    public int multiGameOver;


    public CoreGame(String gameType, String difficulty, Context context, GameView gameview) {
        this.gameview = gameview;
        this.context = context;
        this.gametype = gameType;
        this.soundEffects = new SoundEffects();
        this.soundOn = true;

        this.setupGame(difficulty);

        pScore = characterSprite.getScore();
    }

    private void setupGame(String difficulty) {
        this.objectsOnScreen = new ArrayList<>();
        this.fallingObjectFactory = new FallingObjectFactory();
        this.setGameDifficulty(difficulty);
        this.characterSprite = new CharacterSprite(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.sprites_monkey3), 0.25));
        if(gameview.isMultiplayer){setMultiGameOver(0);}
    }

    /*
     * --------- DRAW, UPDATE, ONTOUCH ---------
     * */


    public void draw(Canvas canvas) {
        characterSprite.draw(canvas);

        for (int i = 0; i < objectsOnScreen.size(); i++) {
            objectsOnScreen.get(i).draw(canvas);
        }
    }

    public void update() {
        long updateTime = System.currentTimeMillis();
        characterSprite.update();
        if (characterSprite.getLives() == 0 && !this.gameview.isMultiplayer) { gameview.gameOver(); }
        if (characterSprite.getLives() == 0 && this.gameview.isMultiplayer) { gameview.gameLost(); }

        gameview.updateScoreSelf(characterSprite.getScore(), characterSprite.getLives());

        if (this.gameview.isMultiplayer) { broadcast(); }

        for (int i = 0; i < objectsOnScreen.size(); i++) {
            FallingObject currentObject = objectsOnScreen.get(i);
            currentObject.update();
            currentObject.detectCollision(characterSprite);
            if (currentObject.isEaten()){
                currentObject.applyGameChange(this, updateTime);
                if(!currentObject.gameChangeMessage().equals("")){this.gameview.popup(currentObject.gameChangeMessage());}
            }
            if (currentObject.collisionDetected()) {
                soundEffects.playSound(currentObject.getSound());
                removeObject(currentObject);
            }
            if (starBeetleDuration <= updateTime){
                fallingObjectFactory.setOnlyBad(false);
                fallingObjectFactory.setOnlyGood(false);
            }
            if (beetleDuration <= updateTime){
                this.fallingObjectFactory.setObjectScale(0, 0.15);
                this.fallingObjectFactory.setObjectScale(1, 0.15);
            }
        }

        int timeSinceSpawn = (int) (updateTime - timeLastSpawn);
        if (timeSinceSpawn >= baseFrequency) {
            spawnObject(createObject());
            timeLastSpawn = updateTime;
            objectsSpawned++;
            if (objectsSpawned % 5 == 0) {
                speedUp();
            }
        }
    }


    public boolean onTouch(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                characterSprite.isBeingTouched((int) motionEvent.getX(), (int) motionEvent.getY());

                // Update - Sound on /off
                if (gameview.btn_sound.isTouched(motionEvent.getX(), motionEvent.getY())) {
                    soundOn = !soundOn;
                    if (soundOn) {
                        gameview.btn_sound.setImage(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_sound_on), 0.15));
                        if(this.gameview.isMultiplayer){gameview.getMultiPlayerActivity().backgroundMusicOn();}
                        if(!this.gameview.isMultiplayer){gameview.getSinglePlayerActivity().backgroundMusicOn();}
                        soundEffects.volumeOn();
                    } else {
                        gameview.btn_sound.setImage(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_sound_off), 0.15));
                        if(this.gameview.isMultiplayer){gameview.getMultiPlayerActivity().backgroundMusicOff();}
                        if(!this.gameview.isMultiplayer){gameview.getSinglePlayerActivity().backgroundMusicOff();}
                        soundEffects.volumeOff();
                    }
                }

                // Update when exit button pressed
                if (gameview.btn_exit.isTouched(motionEvent.getX(), motionEvent.getY()) && !gameview.isMultiplayer) {
                    gameview.setGamePause(true);
                }
                if (gameview.btn_exit.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isMultiplayer) {
                    gameview.setGamePause(true);
                }

                // Update for response in game exit / game over
                if (gameview.btn_yes.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isGamePause()) {
                    if(gameview.isMultiplayer){
                        setMultiGameOver(1);
                        sendBroadcast();
                    }
                    gameview.gameExit();
                }
                if (gameview.btn_no.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isGamePause()) { gameview.setGamePause(false); }
                if (gameview.txt_gameOver.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isGameOver()) { finishGame(); }

                if(this.gameview.isMultiplayer){
                    if (gameview.txt_gameWin.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isGameWon()) { finishGame(); }
                    if (gameview.txt_gameLost.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isGameLost()) { finishGame(); }
                    if (gameview.txt_opponentExit.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isOpponentExit()) { finishGame(); }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (characterSprite.isTouched()) {
                    characterSprite.setCharacterPositionX((int) motionEvent.getX());
                    //TODO: Fix touch track of sprite
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

    /*
     * --------- OBJECT METHODS ---------
     * Calls the factory to generate an object, handles objects presence on the screen.
     * */

    public FallingObject createObject() {
        return fallingObjectFactory.getFallingObject();
    }

    public void spawnObject(FallingObject fallingObject) {
        fallingObject.setObjectPositionX(getRandomXPosition(fallingObject));
        fallingObject.setObjectSpeed(getRandomSpeed());
        objectsOnScreen.add(fallingObject);
    }

    public void removeObject(FallingObject fallingObject) {
        objectsOnScreen.remove(fallingObject);
    }

    public int getRandomXPosition(FallingObject fallingObject) {
        return (int) (Math.random() * (screenWidth - fallingObject.getObjectWidth()));
    }

    public void speedUp() {
        if (baseFrequency >= 50) {
            if (this.baseFrequency <= 75) {
                this.baseFrequency -= 5;
            } else if (this.baseFrequency <= 150) {
                this.baseFrequency -= 25;
            } else {
                this.baseFrequency -= 50;
            }
        }
        this.baseSpeed += 0.5;
    }

    /*
     * --------- MULTIPLAYER METHODS ---------
     * Broadcast score to other player
     * */

    public void broadcast(){
        sendBroadcast();
        receiveBroadcast();
    }

    public void receiveBroadcast(){
        // If opponent lost the game
        if(gameview.getMultiPlayerActivity().getIsGameOver() == 1 && gameview.getMultiPlayerActivity().getOpponentLife() <= 0) {
            gameview.gameWon();
        }

        // If opponent exits in the middle of the game
        if (gameview.getMultiPlayerActivity().getIsGameOver() == 1 && (gameview.getMultiPlayerActivity().getOpponentLife() > 0)) {
            gameview.opponentExit();
        }
    }

    public void sendBroadcast(){
        if(characterSprite.getLives() == 0){
            gameview.getMultiPlayerActivity().broadcast(characterSprite.getScore(), -1, getMultiGameOver());
        }
        gameview.getMultiPlayerActivity().broadcast(characterSprite.getScore(), characterSprite.getLives(), getMultiGameOver());
    }

    public void broadCastPowerUp(int powerup){
        // TODO: Send type of powerup to other player.
    }

    public void applyNegativeGameChange(ObjectType objectType, long updateTime){
        if (objectType == ObjectType.BEETLE) {
            fallingObjectFactory.setObjectScale(0,0.1);
            fallingObjectFactory.setObjectScale(1,0.25);
            setBeetleDuration(updateTime + 10000);
        } else if (objectType == ObjectType.STARBEETLE) {
            fallingObjectFactory.setOnlyBad(true);
            setStarBeetleDuration(updateTime + 10000);
        }
    }





    /*
     * --------- GETTERS AND SETTERS ---------
     * */

    public static Context getContext() {
        return context;
    }

    public FallingObjectFactory getFallingObjectFactory(){
        return this.fallingObjectFactory;
    }

    public int getRandomSpeed() {
        return (int) ((Math.random() + 1) * baseSpeed);
    }

    private void setGameDifficulty(String difficulty) {
        this.difficulty = difficulty;
        if (difficulty.equals(easy)) {
            this.baseFrequency = 2000;
            this.baseSpeed = 5;
            this.fractionGood = 7;
        }
        if (difficulty.equals(medium)) {
            this.baseFrequency = 1500;
            this.baseSpeed = 10;
            this.fractionGood = 6;
        }
        if (difficulty.equals(hard)) {
            this.baseFrequency = 1000;
            this.baseSpeed = 15;
            this.fractionGood = 5;
        }
        this.fallingObjectFactory.setFallingObjectFraction(this.fractionGood);
    }

    public void setStarBeetleDuration(long starBeetleDuration) {
        this.starBeetleDuration = starBeetleDuration;
    }

    public void setBeetleDuration(long beetleDuration) {
        this.beetleDuration = beetleDuration;
    }

    public void setMultiGameOver(int b){
        this.multiGameOver = b;
    }

    public int getMultiGameOver(){
        return this.multiGameOver;
    }


    /*
     * --------- HELP METHODS ---------
     * */

    public void finishGame(){
        if (!gameview.isMultiplayer) { gameview.getSinglePlayerActivity().finish();}
        if (gameview.isMultiplayer) { gameview.getMultiPlayerActivity().finish(); }
    }

    public Bitmap getResizedBitmapObject(Bitmap bmp, double scaleFactorWidth) {
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
