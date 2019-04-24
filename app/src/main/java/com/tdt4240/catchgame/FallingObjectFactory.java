package com.tdt4240.catchgame;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FallingObjectFactory {

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    private HashMap<ScaledObject, Bitmap> objectImages;
    private ArrayList<Integer> fallingObjectFraction;
    private List<Double> objectScale;
    private boolean onlyBad = false;
    private boolean onlyGood = false;

    public FallingObjectFactory() {
        objectImages = new HashMap<>();
        this.fallingObjectFraction = new ArrayList<>();
        this.objectScale = Arrays.asList(0.15, 0.1, 0.15);
    }

    //Creates a list of 0s (good object), 1s (bad object), and 2s (power up) according to fraction.
    // fraction is given on game creation as difficulty is set. Based on fraction of 10 numbers.
    public void setFallingObjectFraction(int fractionGood) {
        int numberOfPowerUps = fractionGood / 2;
        for (int i = 0; i < fractionGood; i++) {
            fallingObjectFraction.add(0);
        }
        for (int j = 0; j < 10 - fractionGood; j++) {
            fallingObjectFraction.add(1);
        }
        for (int k = 0; k < numberOfPowerUps; k++) {
            fallingObjectFraction.add(2);
        }
    }

    public int getFallingObjectType() {
        int id = (int) ((Math.random()) * (fallingObjectFraction.size() - 1));
        return fallingObjectFraction.get(id);
    }

    public FallingObject getFallingObject() {
        if (this.onlyBad) {
            ObjectType objectID = ObjectType.randomBad();
            return new BadFood(getObjectImage(objectID, objectScale.get(1)), objectID);
        }
        else if (this.onlyGood) {
            ObjectType objectID = ObjectType.randomGood();
            return new GoodFood(getObjectImage(objectID, objectScale.get(0)), objectID);
        }
        else {
            int fallingObjectType = getFallingObjectType();
            if (fallingObjectType == 1) {
                ObjectType objectID = ObjectType.randomBad();
                return new BadFood(getObjectImage(objectID, objectScale.get(1)), objectID);
            } else if (fallingObjectType == 2) {
                ObjectType objectID = ObjectType.randomPowerup();
                return new PowerUp(getObjectImage(objectID, objectScale.get(2)), objectID);
            } else {
                ObjectType objectID = ObjectType.randomGood();
                return new GoodFood(getObjectImage(objectID, objectScale.get(0)), objectID);
            }
        }
    }

    public Bitmap getObjectImage(ObjectType object, double scale) {
        ScaledObject ImageKey = new ScaledObject(object, scale);
        if (!objectImages.containsKey(ImageKey)) {
            objectImages.put(ImageKey, getResizedBitmapObject(BitmapFactory.decodeResource(CoreGame.context.getResources(), object.objectResourceId), scale));
        }
        return objectImages.get(ImageKey);
    }

    public void setObjectScale(int objectType, double newScale) {
        this.objectScale.set(objectType, newScale);
    }

    public void setOnlyBad(boolean onlyBad){
        this.onlyBad = onlyBad;
    }

    public void setOnlyGood(boolean onlyGood){
        this.onlyGood = onlyGood;
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
