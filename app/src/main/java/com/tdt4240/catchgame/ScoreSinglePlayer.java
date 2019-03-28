package com.tdt4240.catchgame;

public class ScoreSinglePlayer {

    public CoreGame coreGame;
    public int score;

    public ScoreSinglePlayer(CoreGame coreGame) {
        this.coreGame = coreGame;
    }

    // Method for incrementing players score
    public void incrementScore(int scoreInc) {
        coreGame.characterSprite.setScore(coreGame.characterSprite.getScore() + scoreInc);
        System.out.println("Player score: " + coreGame.characterSprite.getScore());

        //Check if "level up"
        // TODO: Find correct level-variables and methods
       /* if (score >= Level.getLevelScore(player.getCurrentLevel() + 1)) {
            player.levelUp();
        }*/

    }

    public void decrementScore(int scoreDec) {
        coreGame.characterSprite.setScore(coreGame.characterSprite.getScore() + scoreDec);

        //Check if "level gameover"
        // TODO: Find correct game-over variables and methods, need view
        if (score < 0) {
        }

        //Check if "level level down"
       /* if (score <= Level.getLevelScore(player.getCurrentLevel())) {
            CharacterSprite.player.levelDown();
        } */

    }


    public void caughtObject(int objectPoints) {

        if(objectPoints>0){
            incrementScore(objectPoints);
        }
        else {
            decrementScore(objectPoints);
        }

    }

}

