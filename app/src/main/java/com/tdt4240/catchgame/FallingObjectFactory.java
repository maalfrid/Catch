package com.tdt4240.catchgame;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FallingObjectFactory {

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    private HashMap<Integer, Integer> goodFoodCollection;
    private HashMap<Integer, Bitmap> goodFoodImages;
    private HashMap<Integer, Integer> badFoodCollection;
    private HashMap<Integer, Bitmap> badFoodImages;
    private HashMap<Integer, Integer> powerUpCollection;
    private HashMap<Integer, Bitmap> powerUpImages;
    private ArrayList<Integer> fallingObjectFraction;

    //TODO: Get rid of coregame being passed around.
    public FallingObjectFactory(){
        this.loadGoodObjects();
        this.loadBadObjects();
        this.loadPowerUpObjects();
        this.fallingObjectFraction = new ArrayList<>();
    }

    //Creates a list of 0s (good object), 1s (bad object), and 2s (power up) according to fraction.
    // fraction is given on game creation as difficulty is set. Based on fraction of 10 numbers.
    public void setFallingObjectFraction(int fractionGood) {
        int numberOfPowerUps = fractionGood/2;
        for (int i = 0; i < fractionGood; i++) {
            fallingObjectFraction.add(0);
        }
        for (int j = 0; j < 10 - fractionGood; j++) {
            fallingObjectFraction.add(1);
        }
        for (int k = 0; k < numberOfPowerUps; k++){
            fallingObjectFraction.add(2);
        }
    }

    //method for getting random falling object according to percentage from level
    public int getFallingObjectType(){
        int id = (int)((Math.random())* (fallingObjectFraction.size() -1));
        return fallingObjectFraction.get(id);
    }

    public FallingObject getFallingObject() {
        int fallingObjectType = getFallingObjectType();
        if (fallingObjectType == 1) {
            int objectID = getRandomKey(badFoodCollection);
            return new BadFood(getBitmapForFallingObject(objectID, badFoodImages), getFoodValue(objectID, badFoodCollection));
        } else if (fallingObjectType == 2) {
            int objectID = getRandomKey(powerUpCollection);
            return new PowerUp(getBitmapForFallingObject(objectID, powerUpImages), getFoodValue(objectID, powerUpCollection), objectID);
        }
        else {
            int objectID = getRandomKey(goodFoodCollection);
            return new GoodFood(getBitmapForFallingObject(objectID, goodFoodImages), getFoodValue(objectID, goodFoodCollection));
        }
    }

    //picks out random food from a given foodtype
    public int getRandomKey(HashMap foodCollection) {
        Object[] foodKeys = foodCollection.keySet().toArray();
        Object key = foodKeys[new Random().nextInt(foodKeys.length)];
        return (int) key;
    }

    //use imageID to create Bitmap
    public Bitmap getBitmapForFallingObject(int objectID, HashMap foodCollection){
        return (Bitmap) foodCollection.get(objectID);
    }

    //finds the score the given food gives
    public int getFoodValue(int objectID, HashMap foodCollection){
        return (int) foodCollection.get(objectID);
    }

    // --- Load object-library ---

    public void loadGoodObjects(){
        goodFoodCollection = new HashMap<>();
        goodFoodCollection.put(R.drawable.obj_good_banana, 5);
        goodFoodCollection.put(R.drawable.obj_good_apple, 2);
        goodFoodCollection.put(R.drawable.obj_good_strawberry, 1);
        goodFoodCollection.put(R.drawable.obj_good_cherry, 1);
        goodFoodCollection.put(R.drawable.obj_good_grapes, 2);
        goodFoodCollection.put(R.drawable.obj_good_lemon, 2);
        goodFoodCollection.put(R.drawable.obj_good_orange, 1);
        goodFoodCollection.put(R.drawable.obj_good_pineapple, 1);

        goodFoodImages = new HashMap<>();
        goodFoodImages.put(R.drawable.obj_good_banana, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_banana), 0.2));
        goodFoodImages.put(R.drawable.obj_good_apple, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_apple), 0.15));
        goodFoodImages.put(R.drawable.obj_good_strawberry, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_strawberry), 0.15));
        goodFoodImages.put(R.drawable.obj_good_cherry, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_cherry), 0.15));
        goodFoodImages.put(R.drawable.obj_good_grapes, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_grapes), 0.15));
        goodFoodImages.put(R.drawable.obj_good_lemon, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_lemon), 0.15));
        goodFoodImages.put(R.drawable.obj_good_orange, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_orange), 0.15));
        goodFoodImages.put(R.drawable.obj_good_pineapple, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_pineapple), 0.15));
    }

    public void loadBadObjects(){
        badFoodCollection = new HashMap<>();
        badFoodCollection.put(R.drawable.obj_bad_snake, -3);
        badFoodCollection.put(R.drawable.obj_bad_spider, -2);

        badFoodImages = new HashMap<>();
        badFoodImages.put(R.drawable.obj_bad_snake, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_bad_snake), 0.15));
        badFoodImages.put(R.drawable.obj_bad_spider, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_bad_spider), 0.15));
    }

    public void loadPowerUpObjects(){
        powerUpCollection = new HashMap<>();
        powerUpCollection.put(R.drawable.obj_powerup_beetle, 3);
        powerUpCollection.put(R.drawable.obj_powerup_ladybug, 1);
        powerUpCollection.put(R.drawable.obj_powerup_starbeetle, 2);

        powerUpImages = new HashMap<>();
        powerUpImages.put(R.drawable.obj_powerup_beetle, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_powerup_beetle), 0.15));
        powerUpImages.put(R.drawable.obj_powerup_ladybug, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_powerup_ladybug), 0.15));
        powerUpImages.put(R.drawable.obj_powerup_starbeetle, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_powerup_starbeetle), 0.15));
    }

    public Bitmap getResizedBitmapObject(Bitmap bmp, double scaleFactorWidth) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        double newWidth = screenWidth * scaleFactorWidth;
        float scale = ((float) newWidth) / width;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false);
        bmp.recycle();
        return resizedBitmap;
    }

}
