package com.tdt4240.catchgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static java.security.AccessController.getContext;

public class FallingObjectFactory {

    HashMap<String, Integer> goodFoodCollection;
    HashMap<String, Integer> badFoodCollection;
    HashMap<String, Integer> powerUpCollection;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    //List<String> foodTypeList;

    //lag en versjon som fungerer med noen av hver frukt


   /* final static int[] GOODFRUIT = {
            R.drawable.obj_bad_snake, R.id.view_highscore, R.id.view_play, R.id.view_play_multi,
            R.id.view_play_single, R.id.view_rules, R.id.view_settings_menu, R.id.btn_goBack
    };*/

    public FallingObjectFactory(){
        //foodTypeList = new ArrayList<>(Arrays.asList("good", "bad", "powerup", "good", "bad"));

        goodFoodCollection = new HashMap<>();

        goodFoodCollection.put("banana", 3);
        goodFoodCollection.put("apple", 2);
        goodFoodCollection.put("strawberry", 1);

        badFoodCollection = new HashMap<>();

        badFoodCollection.put("snake", -3); //ulik negativ score?
        badFoodCollection.put("spider", -2);

        powerUpCollection = new HashMap<>();

        powerUpCollection.put("starbeetle", 3);
        powerUpCollection.put("ladybug", 3);
        powerUpCollection.put("beetle", 3);
    }


    public FallingObject getFallingObject(String foodType){
        //String foodType = getRandomFoodType(foodTypeList);

        if(foodType.equalsIgnoreCase("good")){
            String food = getRandomKey(goodFoodCollection);
            Bitmap bitmap= getBitmapForFallingObject("good", food);
            int value = getFoodValue(food, goodFoodCollection);
            return new GoodFood(bitmap, value);

        }

        if(foodType.equalsIgnoreCase("bad")){
            String food = getRandomKey(badFoodCollection);
            Bitmap bitmap= getBitmapForFallingObject("bad", food);
            int value = getFoodValue(food, badFoodCollection);
            return new BadFood(bitmap, value);

        }

        if(foodType.equalsIgnoreCase("powerup")){
            String food = getRandomKey(powerUpCollection);
            Bitmap bitmap= getBitmapForFallingObject("powerup", food);
            int value = getFoodValue(food, powerUpCollection);
            return new PowerUp(bitmap, value);

        }
        return null;
    }


    //picks out random food from a given foodtype
    public String getRandomKey(HashMap foodCollection) {
        Object[] foodKeys = foodCollection.keySet().toArray();
        Object key = foodKeys[new Random().nextInt(foodKeys.length)];

        return (String) key;
    }

    //finds the score the given food gives
    public int getFoodValue(String food, HashMap foodCollection){
        return (int) foodCollection.get(food);
    }

    //FallingObject(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.obj_good_banana),0.15));
    public Bitmap getBitmapForFallingObject(String foodType, String food) {
        if (foodType.equals("good")) {
            if (food.equals("banana")) {
                return getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_banana), 0.15);
            }
            else if (food.equals("apple")) {
                return getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_apple), 0.15);
            }
            else {
                return getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_good_strawberry), 0.15);
            }
        } else if (foodType.equals("bad")) {
            if (food.equals("snake")) {
                return getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_bad_snake), 0.15);
            }
            else{
                return getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_bad_spider), 0.15);
            }
        } else {
            if (food.equals("beetle")) {
                return getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_powerup_beetle), 0.15);
            }
            else if (food.equals("ladybug")) {
                return getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_powerup_ladybug), 0.15);
            }
            else {
                return getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), R.drawable.obj_powerup_starbeetle), 0.15);
            }
        }
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


    //makes Bitmap for food
   /* public Bitmap getBitmapForFallingObject(String foodType, String food) {
        String imageName = "obj_" + foodType + "_" + food + ".png";
        Drawable drawableFromImageName = getAndroidDrawable(imageName);
        Context context = getContext();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.drawableFromImageName);
        return CoreGame.getResizedBitmapObject(bitmap ,0.15))
    }

    public static Drawable getAndroidDrawable(String imageName){
        int resourceId = Resources.getSystem().getIdentifier(imageName, "drawable", "andoid");
        if (resourceId == 0){
            return null;
        }
        else{
            return Resources.getSystem().getDrawable(resourceId);
        }*/
        //File file = new File(imageName);
        //Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        //resource??



        //return CoreGame.getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.obj_good_banana),0.15)
        //File file = new File(imageName);
        //Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        //return new FallingObject(getResizedBitmapObject(BitmapFactory.decodeResource(context.getResources(),R.drawable.obj_good_banana),0.15));
        //return bitmap;
    //}


    //picks out the foodType random
   /* public String getRandomFoodType(List foodTypeList){
        Random random = new Random();
        return (String) foodTypeList.get(random.nextInt(foodTypeList.size()));
    }*/



}
