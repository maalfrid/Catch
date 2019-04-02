package com.tdt4240.catchgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class CoreGame{

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public CharacterSprite characterSprite;
    private ArrayList<FallingObject> objectsOnScreen;
    public ScoreSinglePlayer scoreSinglePlayer;
    private int gameTime;
    private int baseFrequency;
    private int baseSpeed;
    private int fractionGood;
    private String difficulty;
    protected static Context context;
    List<Integer> objectID;
    FallingObjectFactory fallingObjectFactory;

    public CoreGame(String difficulty, Context context, GameView gameview){
        this.context = context;
        this.objectsOnScreen = new ArrayList<>();
        this.objectID = new ArrayList<>();
        this.gameTime = 0;
        this.difficulty = difficulty;
        this.setDifficulty(difficulty);
        this.difficulty = difficulty;
        this.characterSprite = new CharacterSprite(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.sprites_monkey3),0.25));
        scoreSinglePlayer = new ScoreSinglePlayer(this);
        fallingObjectFactory = new FallingObjectFactory(this);
    }

    public void draw(Canvas canvas){
        characterSprite.draw(canvas);
        for(int i=0; i < objectsOnScreen.size(); i++){
            objectsOnScreen.get(i).draw(canvas);
        }
    }

    public void update(){
        gameTime++;
        characterSprite.update();
        int fallingObjectType = getFallingObjectType();
        for(int i=0; i < objectsOnScreen.size(); i++) {
            FallingObject currentObject = objectsOnScreen.get(i);
            currentObject.update();
            currentObject.detectCollision(characterSprite);
            if (currentObject.collisionDetected()) {
                removeObject(currentObject);
            }
        }
        //continously get new fallingObjectType of object
        if(gameTime % 2 == 1){
            fallingObjectType = getFallingObjectType();
        }
        // TODO: Find a way to spawn the objects based on the gameloop-time from MainThread? and baseFrequency.
        if ((gameTime == 10 ||gameTime % 50 == 0) && objectID.size() > 0){
            System.out.println(fallingObjectType);
            if(fallingObjectType == 0){
                spawnObject(createObject("good"));
                System.out.println("spawned good");
            }
            if(fallingObjectType == 1){
                spawnObject(createObject("bad"));
                System.out.println("spawned bad");
            }

            //spawnObject(createObject("good"));
        }
    }

    //creates a list of 0s (good object) and 1s (bad object) according to fraction.
    // 10 numbers in total
    public void setFallingObjectType() {
        //add as many 0s as the fraction of the goodfood
        for (int i = 0; i < this.fractionGood; i++) {
            objectID.add(0);
        }
        //add as many 1s as the fraction of the badfood
        for (int j = 0; j < 10 - this.fractionGood; j++) {
            objectID.add(1);
        }
        System.out.println(objectID);
    }

    //method for getting random falling object according to percentage from level

    public int getFallingObjectType(){
        int id = (int)((Math.random())* (objectID.size() -1));
        System.out.println("id of object: " + id);
        return objectID.get(id);

        }


//Fraction of good/bad: 70/30 - 50/50 - 30/70
// remember to say what you want: good/bad/powerup

    public FallingObject createObject(String foodType){
        return fallingObjectFactory.getFallingObject(foodType);
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

    public String getDifficulty() {
        return difficulty;
    }

    public int getRandomSpeed(){
        return (int)((Math.random() + 1) * baseSpeed);
    }

    public int getRandomFrequency(){
        return (int)(Math.random() * baseFrequency);
    }

    public void setLevelUp(){
        if(this.difficulty == "easy"){
            this.difficulty = "medium";
            setDifficulty("medium");
        }
        if(this.difficulty == "medium"){
            this.difficulty = "hard";
            setDifficulty("hard");
        }
    }
    //Temporary method to adjust game difficulty, should be in its own class?
    public void setDifficulty(String difficulty){
        if (difficulty.equals("easy")){
            this.baseFrequency = 1;
            this.baseSpeed = 5;
            this.fractionGood = 7;
            setFallingObjectType();
        }
        if (difficulty.equals("medium")){
            this.baseFrequency = 2;
            this.baseSpeed = 10;
            this.fractionGood = 5;
            setFallingObjectType();
        }
        if (difficulty.equals("hard")){
            this.baseFrequency = 3;
            this.baseSpeed = 15;
            this.fractionGood = 3;
            setFallingObjectType();

        }
    }

    public boolean onTouch(MotionEvent motionEvent) {
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

    public static Context getContext(){
        return context;

    }
}
