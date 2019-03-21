package com.tdt4240.catchgame;

public class ScoreSinglePlayer {

    public CharacterSprite sprite;
    public int score;

    public ScoreSinglePlayer(CharacterSprite sprite) {
        this.sprite = sprite;

    }

    // Method for incrementing players score
    public void incrementScore(int scoreInc) {
        score += scoreInc;

        //Check if "level up"
        // TODO: Find correct level-variables and methods
        if (score >= Level.getLevelScore(sprite.getCurrentLevel() + 1)) {
            sprite.levelUp();
        }

    }

    public void decrementScore(int scoreDec) {
        score += scoreDec;

        //Check if "level gameover"
        // TODO: Find correct game-over variables and methods
        if (score < 0) {
            sprite.gsm.set(new EndStateSingleplayer(sprite.gsm));
        }

        //Check if "level level down"
        if (score <= Level.getLevelScore(sprite.getCurrentLevel())) {
            CharacterSprite.sprite.levelDown();
        }

    }


    public void caughtObject(FallingObject object) {
        int objectScore = object.getScore();

        if(objectScore>0){
            incrementScore(objectScore);
        }
        else {
            decrementScore(objectScore);
        }

    }

}

