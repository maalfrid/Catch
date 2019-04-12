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

    private HashMap<ObjectType, Bitmap> objectImages;
    private ArrayList<Integer> fallingObjectFraction;

    public FallingObjectFactory(){
        this.loadObjectImages();
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

    public int getFallingObjectType(){
        int id = (int)((Math.random())* (fallingObjectFraction.size() -1));
        return fallingObjectFraction.get(id);
    }

    public FallingObject getFallingObject() {
        int fallingObjectType = getFallingObjectType();
        if (fallingObjectType == 1) {
            ObjectType objectID = ObjectType.randomBad();
            return new BadFood(objectImages.get(objectID), objectID);
        } else if (fallingObjectType == 2) {
            ObjectType objectID = ObjectType.randomPowerup();
            return new PowerUp(objectImages.get(objectID), objectID);
        }
        else {
            ObjectType objectID = ObjectType.randomGood();
            return new GoodFood(objectImages.get(objectID), objectID);
        }
    }


    public Bitmap getBitmapForFallingObject(ObjectType objectID){
        return objectImages.get(objectID);
    }

    //finds the score the given food gives
    public int getFoodValue(int objectID, HashMap foodCollection){
        return (int) foodCollection.get(objectID);
    }

    // --- Load object-library ---

    public void loadObjectImages() {
        objectImages = new HashMap<>();
        for (ObjectType object : ObjectType.values()) {
            objectImages.put(object, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), object.objectResourceId), 0.15));
        }

    }

    public void addRescaledObject(ScaledObject scaledObject){
            if (!objectImages.containsKey(scaledObject)){
             //   objectImages.put(scaledObject, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), scaledObject.getObject().objectResourceId), scaledObject.getScale()));
        }
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
