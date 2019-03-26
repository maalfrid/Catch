package com.tdt4240.catchgame;

public class ScoreSinglePlayer {

    public CharacterSprite player;
    public int score;

    public ScoreSinglePlayer(CharacterSprite player) {
        this.player = player;

    }

    // Method for incrementing players score
    public void incrementScore(int scoreInc) {
        score += scoreInc;

        //Check if "level up"
        // TODO: Find correct level-variables and methods
        if (score >= Level.getLevelScore(player.getCurrentLevel() + 1)) {
            player.levelUp();
        }

    }

    public void decrementScore(int scoreDec) {
        score += scoreDec;

        //Check if "level gameover"
        // TODO: Find correct game-over variables and methods
        if (score < 0) {
            player.gsm.set(new EndStateSingleplayer(player.gsm));
        }

        //Check if "level level down"
        if (score <= Level.getLevelScore(player.getCurrentLevel())) {
            CharacterSprite.player.levelDown();
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

