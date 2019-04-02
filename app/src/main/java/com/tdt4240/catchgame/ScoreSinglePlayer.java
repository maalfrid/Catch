package com.tdt4240.catchgame;

public class ScoreSinglePlayer {

    public CoreGame coreGame;

    public ScoreSinglePlayer(CoreGame coreGame) {

        this.coreGame = coreGame;
    }

    // Method for incrementing players score
    public void incrementScore(int scoreInc) {
        coreGame.characterSprite.setScore(coreGame.characterSprite.getScore() + scoreInc);
        System.out.println("Player score: " + coreGame.characterSprite.getScore());

        //Check if "level up"
        if(coreGame.getDifficulty() != "hard"){
            //up two levels
            if (coreGame.characterSprite.getScore() > 35){
                coreGame.setLevelUp();
            }
            //up one level
            else if (coreGame.characterSprite.getScore() > 20){
                coreGame.setLevelUp();

            }

        }

    }

    public void decrementScore(int scoreDec) {
        coreGame.characterSprite.setScore(coreGame.characterSprite.getScore() + scoreDec);

        //Check if "level gameover"
        // TODO: Find correct game-over variables and methods, need view
        if (coreGame.characterSprite.getScore() < 0) {

        }

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

