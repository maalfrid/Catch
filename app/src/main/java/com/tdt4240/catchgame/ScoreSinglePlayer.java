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
        String typeOfGame = coreGame.getGametype();

        if(object.getType().equals(coreGame.getGood())){
            incrementScore(objectPoints);
        }
        if (object.getType().equals(coreGame.getBad())){
            decrementScore(objectPoints);
        }
        if (object.getType().equals(coreGame.getPowerup())){
            // TODO: Implement power-up logic
            incrementScore(objectPoints);

            /*POWER-UP RULES
            Gets points for catching, in addition to logics for which is caught:

            #1 Starbeetle: get 10 points
            #2 Ladybug: decrease character size opponent and decrease yourself
            #3 Beetle (lightning): faster opponent

            */
            if(typeOfGame.equals("single")) {
                if(objectPoints == 1) {
                    incrementScore(10);


                }
                if(objectPoints == 2){
                    // TODO: increase size of own sprite
                    coreGame.characterSprite.maximizeCharacterSize();
                    //wait 5 seconds
                    coreGame.characterSprite.setCharacterSizeNormal();


                }
            }

            if(typeOfGame.equals("multi")){
                if(objectPoints == 1) {
                    incrementScore(10);
                    // TODO: x2 points of the next caught items, don't know how yet
                }
                if(objectPoints == 2){
                    // TODO: increase size of own sprite and decrease opponent size
                    coreGame.characterSprite.maximizeCharacterSize();
                    //coreGame.opponent.minimizeCharacterSize();
                    // wait 5 seconds

                    coreGame.characterSprite.setCharacterSizeNormal();


                }
                if(objectPoints == 3){
                    // TODO: increase speed of opponent's falling objects
                }
            }

        }

    }

}
