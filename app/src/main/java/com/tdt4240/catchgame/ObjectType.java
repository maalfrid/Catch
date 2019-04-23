package com.tdt4240.catchgame;

import java.util.Random;

/*
All object types are stored here with their value and image reference, sorted into good, bad, and powerup.
 */

public enum ObjectType {

    APPLE(5, R.drawable.obj_good_apple),
    BANANA(2, R.drawable.obj_good_banana),
    STRAWBERRY(3, R.drawable.obj_good_strawberry),
    CHERRY(2, R.drawable.obj_good_cherry),
    GRAPES(1, R.drawable.obj_good_grapes),
    LEMON(2, R.drawable.obj_good_lemon),
    ORANGE(2, R.drawable.obj_good_orange),
    PINEAPPLE(1, R.drawable.obj_good_pineapple),

    SNAKE(3, R.drawable.obj_bad_snake),
    SPIDER(2, R.drawable.obj_bad_spider),

    BEETLE(3, R.drawable.obj_powerup_beetle),
    LADYBUG(1, R.drawable.obj_powerup_ladybug),
    STARBEETLE(2, R.drawable.obj_powerup_starbeetle);

    public final int objectValue;
    public final int objectResourceId;

    ObjectType(int value, int resourceId) {
        this.objectValue = value;
        this.objectResourceId = resourceId;
    }

    private static ObjectType[] GOOD = new ObjectType[]{
            APPLE, BANANA, STRAWBERRY, CHERRY, GRAPES, LEMON, ORANGE, PINEAPPLE
    };
    private static ObjectType[] BAD = new ObjectType[]{
            SNAKE, SPIDER
    };
    private static ObjectType[] POWERUP = new ObjectType[]{
            BEETLE, LADYBUG, STARBEETLE
    };

    public static ObjectType randomGood() {
        return GOOD[new Random().nextInt(GOOD.length)];
    }

    public static ObjectType randomBad() {
        return BAD[new Random().nextInt(BAD.length)];
    }

    public static ObjectType randomPowerup() {
        return POWERUP[new Random().nextInt(POWERUP.length)];
    }
}
