package com.tdt4240.catchgame;

public class ScoreSinglePlayer {

    public CoreGame coreGame;
    private int twolevelsup = 25;
    private int onelevelup = 15;
    private boolean levelUp = false;

    public ScoreSinglePlayer(CoreGame coreGame) {

        this.coreGame = coreGame;
    }

    // Method for incrementing players score
    public void incrementScore(int scoreInc) {
        coreGame.characterSprite.setScore(coreGame.characterSprite.getScore() + scoreInc);
        System.out.println("Player score: " + coreGame.characterSprite.getScore());

        //Check if "level up"
        if(!coreGame.getDifficulty().equals(coreGame.getHard())){
            //up two levels
            if (coreGame.getDifficulty().equals(coreGame.getMedium()) && coreGame.characterSprite.getScore() >= twolevelsup){
                coreGame.setLevelUp();
            }
            //up one level
            if (coreGame.characterSprite.getScore() >= onelevelup && levelUp == false){
                coreGame.setLevelUp();
                levelUp = true;
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

