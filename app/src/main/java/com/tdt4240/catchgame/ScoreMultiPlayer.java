package com.tdt4240.catchgame;

public class ScoreMultiPlayer {

    public CharacterSprite sprite;
    public int score;

    public ScoreMultiPlayer(CharacterSprite sprite) {
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

            //TODO: Find right input, need to know which player that get game-over
            sprite.gsm.set(new EndStateMultiplayer());

        }
    }


    //TODO: Implement logic
    public void caughtObject(FallingObject object) {
        int objectScore = object.getScore();

    }

}

