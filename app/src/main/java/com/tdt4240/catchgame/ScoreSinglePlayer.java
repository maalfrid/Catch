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
        if(coreGame.getDifficulty() != "hard"){
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
        String typeOfGame = coreGame.getGametype();

        if(object.getType().equals("good")){
            incrementScore(objectPoints);
        }
        if (object.getType().equals("bad")){
            decrementScore(objectPoints);
        }
        if (object.getType().equals("powerup")){
            // TODO: Implement power-up logic

            // POWER UP FEATURE
            // #1 Starbeetle (x2 points?)
            // #2 Ladybug decrease character size oponent and decrease yourself
            // #3 Beetle (lightning) faster opponent
            if(typeOfGame.equals("single")) {
                if(objectPoints == 1) {
                    incrementScore(objectPoints);


                }
                if(objectPoints == 2){

                }
            }

            if(typeOfGame.equals("multi")){
                if(objectPoints < 0){
                    // TODO: Implement negative score for opponent
                }
                if(objectPoints > 0){
                    incrementScore(objectPoints);
                }
            }

        }

    }

}

