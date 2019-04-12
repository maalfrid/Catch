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
    public MenuItem btn_exit;
    public MenuItem btn_sound;
    public MenuItem txt_score;
    public MenuItem txt_score2;
    private int gameTime;
    private String difficulty;
    private String gametype;
    private String easy = "easy";
    private String medium = "medium";
    private String hard = "hard";

    private int baseFrequency;
    private int baseSpeed;
    private int fractionGood;

    private CharacterSprite characterSprite;
    private FallingObjectFactory fallingObjectFactory;
    private ArrayList<FallingObject> objectsOnScreen;
    public ScoreSinglePlayer scoreSinglePlayer;


    //for multiplayer
    public static int pScore;
    public int multiGameOver;


    public CoreGame(String gameType, String difficulty, Context context, GameView gameview){
        this.gameview = gameview;
        this.context = context;
        this.gametype = gameType;
        this.soundEffects = new SoundEffects();
        this.soundOn = true;

        this.setupGame(difficulty);
        //menu items
        this.btn_exit = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.button_exit),0.15));
        this.btn_sound = new MenuItem(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.button_sound_on),0.15));
        this.txt_score = new MenuItem("ScoreSinglePlayer: "+characterSprite.getScore()+" Lives: "+characterSprite.getLives(), 16, 000000, context);
        this.txt_score2 = new MenuItem("ScoreSinglePlayer: ", 16, 000000, context);
        pScore = characterSprite.getScore();
    }

    private void setupGame(String difficulty){
        this.objectsOnScreen = new ArrayList<>();
        this.gameTime = 0;
        this.fallingObjectFactory = new FallingObjectFactory();
        this.setGameDifficulty(difficulty);
        this.characterSprite = new CharacterSprite(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.sprites_monkey3),0.25));
        this.scoreSinglePlayer = new ScoreSinglePlayer(characterSprite);
    }

    /*
     * --------- DRAW, UPDATE, ONTOUCH ---------
     * */


    public void draw(Canvas canvas){
        characterSprite.draw(canvas);
        btn_exit.draw(canvas, 0, 0);
        btn_sound.draw(canvas, screenWidth - btn_sound.getWidth(), 0);
        txt_score.draw(canvas, screenWidth/2 - txt_score.getWidth()/2, btn_exit.getHeight()/4);
        txt_score2.draw(canvas, screenWidth/2 - txt_score.getWidth()/2, btn_exit.getHeight()/2);

        for(int i=0; i < objectsOnScreen.size(); i++){
            objectsOnScreen.get(i).draw(canvas);
        }
    }

    public void update(){
        characterSprite.update();
        if(characterSprite.getLives()==0){
            gameview.gameOver();
        }
        txt_score.updateScoreLife(characterSprite.getScore(), characterSprite.getLives(), getContext());
        //Call broadcast
        if(this.gameview.isMultiplayer){
            /* SCORE LOGIC */
            //broadcastScore has 2 parameters -> ScoreSinglePlayer and lives.
            gameview.getMultiPlayerActivity().broadcastScore(characterSprite.getScore(), characterSprite.getLives(), this.multiGameOver);
            txt_score2.updateScoreLife(gameview.getMultiPlayerActivity().getOpponentScore(), gameview.getMultiPlayerActivity().getOpponentLife(), getContext());
            //TODO: If the other opponent looses or exit game --> Make game over view (and click to continue to get to main menu)
            if(gameview.getMultiPlayerActivity().getIsGameOver()==1){
                gameview.gameOver();
            }
            /*if(gameview.getMultiPlayerActivity().getOpponentLife()==0){
                gameview.gameOver();
            }*/

            /* POWERUPS LOGIC */

            // Check powerup buffer bit
            // TODO first: Add powerup bit to buffer in Multiplayeractivity
                // If == 2 -> Increase decrease this sprites size (use setImage)
                // If == 3 -> Increase speed of falling objects (e.g. level up?)

        }

        for(int i=0; i < objectsOnScreen.size(); i++) {
            FallingObject currentObject = objectsOnScreen.get(i);
            currentObject.update();
            currentObject.detectCollision(characterSprite);
            if (currentObject.collisionDetected()) {
                soundEffects.playSound(currentObject.getSound());
                removeObject(currentObject);
                txt_score.updateScoreLife(characterSprite.getScore(), characterSprite.getLives(), getContext());
            }
        }
        // TODO: Find a way to spawn the objects based on the gameloop-time from MainThread? and baseFrequency.
        if (gameTime == 10 || gameTime % 50 == 0){
            spawnObject(createObject());
        }
        gameTime++;
    }


    public boolean onTouch(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                characterSprite.isBeingTouched((int) motionEvent.getX(), (int) motionEvent.getY());
                if(btn_exit.isTouched(motionEvent.getX(), motionEvent.getY()) && !this.gameview.isMultiplayer){
                    gameview.gamePause();
                }
                if(btn_exit.isTouched(motionEvent.getX(), motionEvent.getY()) && this.gameview.isMultiplayer){
                    this.multiGameOver = 1;
                    gameview.gameOver();
                }
                if(btn_sound.isTouched(motionEvent.getX(), motionEvent.getY())){
                    soundOn = !soundOn;
                    if(soundOn){
                        this.btn_sound.setImage(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.button_sound_on),0.15));
                        gameview.getSinglePlayerActivity().backgroundMusicOn();
                        soundEffects.volumeOn();
                    }
                    else{
                        this.btn_sound.setImage(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.button_sound_off),0.15));
                        gameview.getSinglePlayerActivity().backgroundMusicOff();
                        soundEffects.volumeOff();
                    }
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

    public FallingObject createObject(){
        return fallingObjectFactory.getFallingObject();
    }

    public void spawnObject(FallingObject fallingObject){
        fallingObject.setObjectPositionX(getRandomXPosition(fallingObject));
        fallingObject.setObjectSpeed(getRandomSpeed());
        objectsOnScreen.add(fallingObject);
    }

    public void removeObject(FallingObject fallingObject){
        objectsOnScreen.remove(fallingObject);
    }

    public int getRandomXPosition(FallingObject fallingObject){
        return (int)(Math.random() * (screenWidth - fallingObject.getObjectWidth()));
    }

    /*
    * --------- GETTERS AND SETTERS ---------
    * */

    public static Context getContext(){
        return context;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getRandomSpeed(){
        return (int)((Math.random() + 1) * baseSpeed);
    }

    public int getRandomFrequency(){
        return (int)(Math.random() * baseFrequency);
    }


    // TODO: Turn these into gradual increase of frequency for an endless experience. Universal for all difficulties.
    public void setLevelUp(){
        if(this.difficulty.equals(medium)){
            this.difficulty = hard;
            soundEffects.levelUpSound();
            setGameDifficulty(hard);
            this.gameview.popup("Level up from medium to hard");
        }
        if(this.difficulty.equals(easy)){
            this.difficulty = medium;
            soundEffects.levelUpSound();
            setGameDifficulty(medium);
            this.gameview.popup("Level up from easy to medium");
        }
    }
    public void setLevelDown(){
        if(this.difficulty.equals(hard)){
            this.difficulty = medium;

            setGameDifficulty(medium);
        }
        if(this.difficulty.equals(medium)){
            this.difficulty = easy;
            setGameDifficulty(easy);

        }
    }

    private void setGameDifficulty(String difficulty){
        this.difficulty = difficulty;
        if (difficulty.equals(easy)){
            this.baseFrequency = 1;
            this.baseSpeed = 5;
            this.fractionGood = 7;
        }
        if (difficulty.equals(medium)){
            this.baseFrequency = 2;
            this.baseSpeed = 10;
            this.fractionGood = 5;
        }
        if (difficulty.equals(hard)){
            this.baseFrequency = 3;
            this.baseSpeed = 15;
            this.fractionGood = 3;
        }
        fallingObjectFactory.setFallingObjectFraction(this.fractionGood);
    }
  
    public String getGametype(){ return this.gametype; }

    public SoundEffects getSoundEffect(){
        return this.soundEffects;
    }


    /*
    * --------- HELP METHODS ---------
    * */

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

    //public void popup(String msg){
        //this.gameview.popup(msg);
    //}

}
