package com.tdt4240.catchgame.Model;

import com.tdt4240.catchgame.R;


public enum Sprites{

    //TODO: Fix crocodile image height.
    // NAME(DefaultImage, CatchImage, DeadImage)
    MONKEY(R.drawable.sprites_monkey1, R.drawable.sprites_monkey3, R.drawable.sprites_monkeydead),
    CROCODILE(R.drawable.sprites_crocodile1, R.drawable.sprites_crocodile2, R.drawable.sprites_crocodiledead),
    GNU(R.drawable.sprites_gnu1, R.drawable.sprites_gnu3,R.drawable.sprites_gnu4);

    public final int defaultImageID;
    public final int catchImageID;
    public final int deadImageID;

    Sprites(int defaultImageID, int catchImageID, int deadImageID){
        this.defaultImageID = defaultImageID;
        this.catchImageID = catchImageID;
        this.deadImageID = deadImageID;
    }
}
