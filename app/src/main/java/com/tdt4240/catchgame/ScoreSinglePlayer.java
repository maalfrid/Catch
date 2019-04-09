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
        this.coreGame.characterSprite.setScore(this.coreGame.characterSprite.getScore() + scoreInc);
        System.out.println("Player score: " + this.coreGame.characterSprite.getScore());

        //Check if "level up"
        if(!this.coreGame.getDifficulty().equals(this.coreGame.getHard())){
            //up two levels
            if (this.coreGame.getDifficulty().equals(this.coreGame.getMedium()) && this.coreGame.characterSprite.getScore() >= twolevelsup){
                this.coreGame.setLevelUp();
            }
            //up one level
            if (this.coreGame.characterSprite.getScore() >= onelevelup && levelUp == false){
                this.coreGame.setLevelUp();
                levelUp = true;
            }

        }

    }

    public void decrementScore(int scoreDec) {
        this.coreGame.characterSprite.setScore(coreGame.characterSprite.getScore() + scoreDec);

        //Check if "level gameover"
        // TODO: Find correct game-over variables and methods, need view
        if (this.coreGame.characterSprite.getScore() < 0) {

        }

    }


    public void caughtObject(FallingObject object) {
        int objectPoints = object.getScore();
        String typeOfGame = this.coreGame.getGametype();

        if(object.getType().equals(this.coreGame.getGood())){
            incrementScore(objectPoints);
        }
        if (object.getType().equals(this.coreGame.getBad())){
            decrementScore(objectPoints);
        }
        if (object.getType().equals(this.coreGame.getPowerup())){
            // TODO: Implement power-up logic
            incrementScore(objectPoints);

            /*POWER-UP RULES
            Gets points for catching, in addition to logics for which is caught:

            SINGLE PLAYER:

            #1 Starbeetle: get 10 points
            #2 Ladybug: level down
            #3 Beetle (lightning): get one additional life

            Multi PLAYER:

            #1 Starbeetle: get 10 points
            #2 Ladybug: level down
            #3 Beetle (lightning): faster opponent

            */
            if(typeOfGame.equals("single")) {
                if(objectPoints == 1) {
                    incrementScore(10);
                    //this.coreGame.popup("Powerup! 10 extra points");
                }
                if(objectPoints == 2){
                    this.coreGame.setLevelDown();
                }
                if(objectPoints == 3){
                    int lives = this.coreGame.characterSprite.getLives() + 1;
                    this.coreGame.characterSprite.setLives(lives);
                    //this.coreGame.popup("Powerup! 1 extra life");
                }
            }

           /* if(typeOfGame.equals("multi")){
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
            }*/

        }

    }

}
