package com.tdt4240.catchgame;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import java.util.HashMap;
import java.util.Random;

public class FallingObjectFactory {

    HashMap<Integer, Integer> goodFoodCollection;
    HashMap<Integer, Bitmap> goodFoodImages;
    HashMap<Integer, Integer> badFoodCollection;
    HashMap<Integer, Bitmap> badFoodImages;
    HashMap<Integer, Integer> powerUpCollection;
    HashMap<Integer, Bitmap> powerUpImages;
    private CoreGame coreGame;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    public FallingObjectFactory(CoreGame coreGame){
        this.coreGame = coreGame;
        this.loadGoodObjects();
        this.loadBadObjects();
        this.loadPowerUpObjects();
    }

    //creates an object of the given foodtype
    public FallingObject getFallingObject(String foodType){
        if(foodType.equalsIgnoreCase(coreGame.getBad())){
            int food = getRandomKey(badFoodCollection);
            Bitmap bitmap = getBitmapForFallingObject(food);
            int value = getFoodValue(food, badFoodCollection);
            return new BadFood(bitmap, value, "bad", coreGame);
        }
        else if(foodType.equalsIgnoreCase(coreGame.getPowerup())){
            int food = getRandomKey(powerUpCollection);
            Bitmap bitmap = getBitmapForFallingObject(food);
            int value = getFoodValue(food, powerUpCollection);
            return new PowerUp(bitmap, value, "powerUp", coreGame);
        }
        else{
            int food = getRandomKey(goodFoodCollection);
            Bitmap bitmap = getBitmapForFallingObject(food);
            int value = getFoodValue(food, goodFoodCollection);
            return new GoodFood(bitmap, value, "good", coreGame);
        }
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

    public void loadGoodObjects(){
        goodFoodCollection = new HashMap<>();
        goodFoodCollection.put(R.drawable.obj_good_banana, 2);
        goodFoodCollection.put(R.drawable.obj_good_apple, 2);
        goodFoodCollection.put(R.drawable.obj_good_strawberry, 1);
        goodFoodCollection.put(R.drawable.obj_good_cherry, 1);
        goodFoodCollection.put(R.drawable.obj_good_grapes, 2);
        goodFoodCollection.put(R.drawable.obj_good_lemon, 2);
        goodFoodCollection.put(R.drawable.obj_good_orange, 1);
        goodFoodCollection.put(R.drawable.obj_good_pineapple, 1);

        goodFoodImages = new HashMap<>();
        goodFoodImages.put(R.drawable.obj_good_banana, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_banana), 0.15));
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
