package com.tdt4240.catchgame.Model;

import com.tdt4240.catchgame.R;

public enum Backgrounds {

    BLUE(R.drawable.bg_menu),
    GREEN(R.drawable.bg_play);

    public final int defaultImageID;

    Backgrounds(int defaultImageID){
        this.defaultImageID = defaultImageID;
    }
}
