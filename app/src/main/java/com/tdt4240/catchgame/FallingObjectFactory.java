package com.tdt4240.catchgame;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import java.util.HashMap;
import java.util.Random;

public class FallingObjectFactory {

    HashMap<Integer, Integer> goodFoodCollection;
    HashMap<Integer, Integer> badFoodCollection;
    HashMap<Integer, Integer> powerUpCollection;
    private CoreGame coreGame;


    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    public FallingObjectFactory(CoreGame coreGame){
        goodFoodCollection = new HashMap<>();

        goodFoodCollection.put(R.drawable.obj_good_banana, 2);
        goodFoodCollection.put(R.drawable.obj_good_apple, 2);
        goodFoodCollection.put(R.drawable.obj_good_strawberry, 1);
        goodFoodCollection.put(R.drawable.obj_good_cherry, 1);
        goodFoodCollection.put(R.drawable.obj_good_grapes, 2);
        goodFoodCollection.put(R.drawable.obj_good_lemon, 2);
        goodFoodCollection.put(R.drawable.obj_good_orange, 1);
        goodFoodCollection.put(R.drawable.obj_good_pineapple, 1);

        badFoodCollection = new HashMap<>();

        badFoodCollection.put(R.drawable.obj_bad_snake, -3); //ulik negativ score?
        badFoodCollection.put(R.drawable.obj_bad_spider, -2);

        powerUpCollection = new HashMap<>();

        powerUpCollection.put(R.drawable.obj_powerup_beetle, 3);
        powerUpCollection.put(R.drawable.obj_powerup_ladybug, 3);
        powerUpCollection.put(R.drawable.obj_powerup_starbeetle, 3);

        this.coreGame = coreGame;
    }

    //creates an object of the given foodtype
    public FallingObject getFallingObject(String foodType){

        if(foodType.equalsIgnoreCase("good")){
            int food = getRandomKey(goodFoodCollection);
            Bitmap bitmap = getBitmapForFallingObject(food);
            int value = getFoodValue(food, goodFoodCollection);
            return new GoodFood(bitmap, value, "good", coreGame);
        }

        if(foodType.equalsIgnoreCase("bad")){
            int food = getRandomKey(badFoodCollection);
            Bitmap bitmap = getBitmapForFallingObject(food);
            int value = getFoodValue(food, badFoodCollection);
            return new BadFood(bitmap, value, "bad", coreGame);
        }

        if(foodType.equalsIgnoreCase("powerup")){
            int food = getRandomKey(powerUpCollection);
            Bitmap bitmap = getBitmapForFallingObject(food);
            int value = getFoodValue(food, powerUpCollection);
            return new PowerUp(bitmap, value, "powerUp", coreGame);
        }
        return null;
    }

    //picks out random food from a given foodtype
    public int getRandomKey(HashMap foodCollection) {
        Object[] foodKeys = foodCollection.keySet().toArray();
        Object key = foodKeys[new Random().nextInt(foodKeys.length)];

        return (int) key;
    }

    //use imageID to create Bitmap
    public Bitmap getBitmapForFallingObject(int food){
        return getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), food), 0.15);
    }

    //finds the score the given food gives
    public int getFoodValue(int food, HashMap foodCollection){
        return (int) foodCollection.get(food);
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
