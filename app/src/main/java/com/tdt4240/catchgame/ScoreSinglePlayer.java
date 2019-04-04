package com.tdt4240.catchgame;

public class ScoreSinglePlayer {

    public CoreGame coreGame;
    private int twolevelsup = 35;
    private int onelevelup = 20;

    public ScoreSinglePlayer(CoreGame coreGame) {

        this.coreGame = coreGame;
    }

    // Method for incrementing players score
    public void incrementScore(int scoreInc) {
        coreGame.characterSprite.setScore(coreGame.characterSprite.getScore() + scoreInc);
        System.out.println("Player score: " + coreGame.characterSprite.getScore());

        //Check if "level up"
        if(coreGame.getDifficulty() != coreGame.getHard()){
            //up two levels
            if (coreGame.characterSprite.getScore() > twolevelsup){
                coreGame.setLevelUp();
            }
            //up one level
            else if (coreGame.characterSprite.getScore() > onelevelup){
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

        if(object.getType().equals(coreGame.getGood())){
            incrementScore(objectPoints);
        }
        if (object.getType().equals(coreGame.getBad())){
            decrementScore(objectPoints);
        }
        if (object.getType().equals(coreGame.getPowerup())){
            // TODO: Implement power-up logic
        }

    }

}

