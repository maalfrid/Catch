package com.tdt4240.catchgame.Controllers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;

import com.tdt4240.catchgame.Model.CharacterSprite;
import com.tdt4240.catchgame.Model.FallingObject;
import com.tdt4240.catchgame.Model.FallingObjectFactory;
import com.tdt4240.catchgame.Model.ObjectType;
import com.tdt4240.catchgame.Model.SoundEffects;
import com.tdt4240.catchgame.Model.Sprites;
import com.tdt4240.catchgame.R;
import com.tdt4240.catchgame.View.GameView;

import java.util.ArrayList;
import java.util.HashMap;

public class CoreGame {

    protected static Context context;
    private GameView gameview;
    private boolean soundOn;
    private SoundEffects soundEffects;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    //TODO: change this
    private String easy = "easy";
    private String medium = "medium";
    private String hard = "hard";

    private long timeLastSpawn = 0;
    private int objectsSpawned = 0;

    private int baseFrequency;
    private float baseSpeed;
    private int fractionGood;

    private HashMap<ObjectType, Long> powerupDurations;

    private CharacterSprite characterSprite;
    private ArrayList<FallingObject> objectsOnScreen;


    //for multiplayer
    private int multiGameOver;
    private int multiPowerupSent;
    private int multiPowerupReceived;

    /*
     * --------- CREATE AND SETUP THE GAME ---------
     * */

    public CoreGame(String difficulty, Context context, GameView gameview) {
        this.gameview = gameview;
        this.context = context;
        this.soundEffects = new SoundEffects();
        this.soundOn = true;
        this.setupGame(difficulty);
    }

    private void setupGame(String difficulty) {
        this.objectsOnScreen = new ArrayList<>();
        this.mapPowerUpDurations();
        this.setGameDifficulty(difficulty);
        this.characterSprite = new CharacterSprite(Sprites.GNU);
        if(gameview.isMultiplayer){setMultiGameOver(0);}
    }

    private void setGameDifficulty(String difficulty) {
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
        FallingObjectFactory.getInstance().setFallingObjectFraction(this.fractionGood);
    }

    private void mapPowerUpDurations(){
        long currentTime = System.currentTimeMillis();
        powerupDurations = new HashMap<>();
        powerupDurations.put(ObjectType.LIGHTNINGBEETLE, currentTime);
        powerupDurations.put(ObjectType.STARBEETLE, currentTime);
        powerupDurations.put(ObjectType.GREENBEETLE, currentTime);
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
        if (characterSprite.getLives() == 0 && !this.gameview.isMultiplayer) { gameOver(); }
        if (characterSprite.getLives() == 0 && this.gameview.isMultiplayer) { gameLost(); }

        gameview.updateScoreSelf(characterSprite.getScore(), characterSprite.getLives());

        if (this.gameview.isMultiplayer) { broadcast(); }
        if(this.multiPowerupReceived != 0){ applyNegativeGameChange(this.multiPowerupReceived, updateTime);}

        checkObjectsOnScreen(updateTime);
        spawnNewObject(updateTime);
    }


    public boolean onTouch(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                characterSprite.isBeingTouched((int) motionEvent.getX(), (int) motionEvent.getY());
                checkSound(motionEvent);
                checkExitGame(motionEvent);
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

    /*
     * --------- OBJECT METHODS ---------
     * Calls the factory to generate an object, handles objects presence on the screen.
     * */

    public FallingObject createObject() {
        return FallingObjectFactory.getInstance().getFallingObject();
    }

    public void spawnNewObject(long updateTime){
        int timeSinceSpawn = (int) (updateTime - timeLastSpawn);
        if (timeSinceSpawn >= baseFrequency) {
            placeObject(createObject());
            timeLastSpawn = updateTime;
            objectsSpawned++;
            if (objectsSpawned % 5 == 0) {
                speedUp();
            }
        }
    }

    public void placeObject(FallingObject fallingObject) {
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
        System.out.println("Frenquency is now " + baseFrequency);
        System.out.println("Speed is now " + baseSpeed);
        this.baseSpeed += 0.5;
        if (baseFrequency >= 205) {
            if (this.baseFrequency <= 250) {
                this.baseFrequency -= 5;
            } else if (this.baseFrequency <= 500) {
                this.baseFrequency -= 25;
            } else {
                this.baseFrequency -= 50;
            }
        }
    }

    private void checkObjectsOnScreen(long updateTime){
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
        }
        checkPowerUpEffect(updateTime);
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
            gameWon();
        }

        // If opponent exits in the middle of the game
        if (gameview.getMultiPlayerActivity().getIsGameOver() == 1 && (gameview.getMultiPlayerActivity().getOpponentLife() > 0)) {
            opponentExit();
        }

        // Get powerup value
        this.multiPowerupReceived = this.gameview.getMultiPlayerActivity().getPowerup();

    }

    public void sendBroadcast(){
        if(characterSprite.getLives() == 0){
            gameview.getMultiPlayerActivity().broadcast(characterSprite.getScore(), -1, getMultiGameOver(), getMultiPowerupSent());
        }
        gameview.getMultiPlayerActivity().broadcast(characterSprite.getScore(), characterSprite.getLives(), getMultiGameOver(), getMultiPowerupSent());

        // Reset powerup
        if(getMultiPowerupSent() != 0){ setMultiPowerupSent(0);}
    }

    /*
     * --------- MULTIPLAYER METHODS FOR POWERUPS ---------
     * Handle how powerups taken by the opponent affect the player.
     * */

    public void checkPowerUpEffect(long updateTime){
        if (powerupDurations.get(ObjectType.STARBEETLE) <= updateTime){
            FallingObjectFactory.getInstance().setOnlyBad(false);
            FallingObjectFactory.getInstance().setOnlyGood(false);
        }
        if (powerupDurations.get(ObjectType.LIGHTNINGBEETLE) <= updateTime){
            FallingObjectFactory.getInstance().setObjectScale(0, 0.15);
            FallingObjectFactory.getInstance().setObjectScale(1, 0.1);
            FallingObjectFactory.getInstance().setLargeBad(false);
            FallingObjectFactory.getInstance().setLargeGood(false);
        }
        if (powerupDurations.get(ObjectType.GREENBEETLE) <= updateTime){
            this.characterSprite.setVulnerable(false);
            this.characterSprite.setImmune(false);
        }
    }

    public void gameChangeMessage(ObjectType objectType){
        String msg = "";
        switch (objectType) {
            case LIGHTNINGBEETLE:
                msg = "Your opponent caught a beetle!\nSmall good objects, large bad objects for 10 seconds";
                break;
            case LADYBUG:
                msg = "Your opponent caught a ladybug\n and got one extra life";
                break;
            case STARBEETLE:
                msg = "Your opponent caught a starbeetle!\nOnly bad objects for 10 seconds";
                break;
            case GREENBEETLE:
                msg = "Your opponent caught a green beetle!\nYou are vulnerable for 10 seconds";
                break;
        }
        this.gameview.popup(msg);
    }

    public void applyNegativeGameChange(int objectType, long updateTime){
        // 1: Beetle
        // 2: Starbeetle
        // 3: Ladybug
        // 4: GreenBeetle
        switch (objectType) {
            case 1:
                FallingObjectFactory.getInstance().setObjectScale(0, 0.1);
                FallingObjectFactory.getInstance().setObjectScale(1, 0.25);
                FallingObjectFactory.getInstance().setLargeBad(true);
                setPowerupDuration(ObjectType.LIGHTNINGBEETLE, updateTime + 10000);
                gameChangeMessage(ObjectType.LIGHTNINGBEETLE);
                break;
            case 2:
                FallingObjectFactory.getInstance().setOnlyBad(true);
                setPowerupDuration(ObjectType.STARBEETLE, updateTime + 10000);
                gameChangeMessage(ObjectType.STARBEETLE);
                break;
            case 3:
                gameChangeMessage(ObjectType.LADYBUG);
                break;
            case 4:
                characterSprite.setVulnerable(true);
                setPowerupDuration(ObjectType.GREENBEETLE, updateTime + 10000);
                gameChangeMessage(ObjectType.GREENBEETLE);
                break;
        }
        this.multiPowerupReceived = 0;
    }

    public void gameExit() {
        this.gameview.setRunning(false);
        if (!this.gameview.isMultiplayer) {
            this.gameview.getSinglePlayerActivity().finish();
        }
        if (this.gameview.isMultiplayer) {
            this.gameview.getMultiPlayerActivity().finish();
        }
    }

    // When the player has lost 3 lives
    public void gameOver() {
        this.gameview.setRunning(false);
        this.gameview.setGameOver(true);
    }

    public void gameWon() {
        this.gameview.setRunning(false);
        this.gameview.setGameWon(true);
    }

    public void gameLost() {
        this.gameview.setRunning(false);
        this.gameview.setGameLost(true);
    }

    public void opponentExit() {
        this.gameview.setRunning(false);
        this.gameview.setOpponentExit(true);
    }

    /*
     * --------- CONTROLLER FOR BUTTON CLICKS IN GAME ---------
     * Handle how powerups taken by the opponent affect the player.
     * */


    private void checkSound(MotionEvent motionEvent){
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
    }

    private void checkExitGame(MotionEvent motionEvent){
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
            gameExit();
        }
        if (gameview.btn_no.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isGamePause()) { gameview.setGamePause(false); }
        if (gameview.txt_gameOver.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isGameOver()) { finishGame(); }

        if(this.gameview.isMultiplayer){
            if (gameview.txt_gameWin.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isGameWon()) { finishGame(); }
            if (gameview.txt_gameLost.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isGameLost()) { finishGame(); }
            if (gameview.txt_opponentExit.isTouched(motionEvent.getX(), motionEvent.getY()) && gameview.isOpponentExit()) { finishGame(); }
        }
    }

    /*
     * --------- GETTERS AND SETTERS ---------
     * */

    public static Context getContext() {
        return context;
    }

    public void setPowerupDuration(ObjectType powerupType, long powerupEndTime){
        powerupDurations.put(powerupType, powerupEndTime);
    }

    public void setMultiGameOver(int b){
        this.multiGameOver = b;
    }

    public int getMultiGameOver(){
        return this.multiGameOver;
    }

    public void setMultiPowerupSent(int b){
        this.multiPowerupSent = b;
    }

    public int getMultiPowerupSent(){
        return this.multiPowerupSent;
    }

    public CharacterSprite getCharacterSprite(){
        return this.characterSprite;
    }


    /*
     * --------- HELP METHODS ---------
     * */

    public int getRandomSpeed() {
        return (int) ((Math.random() + 1) * this.baseSpeed);
    }

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
