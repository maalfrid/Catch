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


    public void caughtObject(FallingObject object) {
        int objectPoints = object.getScore();

        if(object.getType().equals("good")){
            incrementScore(objectPoints);
        }
        if (object.getType().equals("bad")){
            decrementScore(objectPoints);
        }
        if (object.getType().equals("power")){
            // TODO: Implement power-up logic
        }

    }

}

